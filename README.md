# 저장소 설명
패스트캠퍼스에서 `코루틴을 활용한 비동기식 간편결제 서비스` 프로젝트 실습 저장소

# 메모 - hello async
## kotlin logging
`io.github.microutils:kotlin-logging:3.0.5`를 자주 사용한다. 

# 메모 - Coroutine
## suspend 함수
특징
- coroutine dispatcher에 의해 실행 또는 재개 
- 중단 지점까지 비선점형으로 동작한다. 
- Context는 continuation이라는 parameter 형태로 전달
   - thread context switching이 발생하지 않는다. 
   - suspend 함수가 붙으면 내부적으로 `Continuation` 파라미터가 붙는다. 
   - 각 메서드 내부는 구간을 쪼개에서 label이 설정된다. 

## 테스트
`kotest` 를 이용한 테스트
- 배경: suspend 함수의 테스트를 지원하는 라이브러리. 
- 의존성
   ```kt
   testImplementation("io.kotest:kotest-runner-junit5:5.6.1")
   testImplementation("io.kotest:kotest-assertions-core:5.6.1")
   testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
   ```
- 샘플 코드
   ```kt
   @SpringBootTest
   @ActiveProfiles("test")
   class ArticleServiceTest(
      @Autowired private val service: ArticleService,
      @Autowired private val repository: ArticleRepository,
      @Autowired private val rxtx: TransactionalOperator,
   ) : StringSpec({

      "create and get" {
         rxtx.rollback {
               val created = service.create(ReqCreate("title1"))
               val get = service.get(created.id)
               get.id shouldBe created.id
               get.title shouldBe created.title
               get.body shouldBe created.body
               get.authorId shouldBe created.authorId
               get.createdAt shouldNotBe null
               get.updatedAt shouldNotBe null
         }
      }
   ```

Controller 테스트를 위한 API 실행 테스트
- `WebTestClient` 를 이용해서 기존 MVC에서 MockMVC를 사용한 것 처럼 사용할 수 있다. 
- 샘플 코드
   ```kt
   // 정의
   val client = WebTestClient.bindToApplicationContext(context).build()

   // 사용
   client.post().uri("/article").accept(MediaType.APPLICATION_JSON)..
   ```

Service Layer 롤백
- `TransactionalOperator`를 이용해서 롤백을 지정해줄 수 있다. 
- 샘플 코드
   ```kt
   // 의존성 주입
   @Autowired private val rxtx: TransactionalOperator,

   // 롤백 지정
   suspend fun <T> TransactionalOperator.rollback(f: suspend (ReactiveTransaction) -> T): T {
    return this.executeAndAwait { tx ->
        tx.setRollbackOnly()
        f.invoke(tx)
    }
   }
   ```