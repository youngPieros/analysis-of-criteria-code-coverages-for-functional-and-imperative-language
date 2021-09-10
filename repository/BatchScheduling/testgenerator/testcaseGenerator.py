import random
import config

print(config.number_of_tasks)
for i in range(config.number_of_tasks):
    deadline = random.randint(1, 50)
    minute = random.randint(1, 10)
    print(deadline, minute)
