from locust import HttpUser, task, between, TaskSet
from locust import events
from locust.runners import MasterRunner, WorkerRunner

class UserBehavior(TaskSet):
    @task
    def getPost(self):
        self.client.get(f'/article/65')
    # @task
    # def getAllPost(self):
    #     self.client.get(f'/article/all?title=matched')
    # @task
    # def delay(self):
    #     self.client.get(f'/stress/delay')


class LocustUser(HttpUser):
    host = "http://localhost:8080"
    tasks = [ UserBehavior ]
    min_wait = 5000
    max_wait = 15000
