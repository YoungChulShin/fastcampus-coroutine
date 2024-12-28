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