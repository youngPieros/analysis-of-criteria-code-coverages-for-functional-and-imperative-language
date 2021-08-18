import random
import config

print(config.number_of_tasks)
for i in range(config.number_of_tasks):
    deadline = random.randint(0, 50)
    minute = random.randint(0, 10)
    print(deadline, minute)
