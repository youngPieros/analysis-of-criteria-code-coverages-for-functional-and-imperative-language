import config
import random

testcases_files = []

def random_pos(n):
    return random.randint(1, n), random.randint(1, n)

def create_testcase(n, k, queen_pos, obstacles):
    testcase = "{} {}\n".format(n, k)
    testcase += "{} {}\n".format(queen_pos[0], queen_pos[1])
    for obstacle in obstacles:
        testcase += "{} {}\n".format(obstacle[0], obstacle[1])
    return testcase


testcases_files = []

for n in config.n_range:
    for k in config.k_range:
        if k >= int(n * n / 2):
            continue
        queen_position = random_pos(n)
        obstacles = []
        while len(obstacles) < k:
            obstacle = random_pos(n)
            if obstacle != queen_position and obstacle not in obstacles:
                obstacles.append(obstacle)
        testcase = create_testcase(n, k, queen_position, obstacles)
        testcase_filename = "testcase_" + str(len(testcases_files) + 1)
        file = open(testcase_filename, "w")
        file.write(testcase)
        file.close()
        testcases_files.append(testcase_filename)

for testcase in testcases_files:
    print(testcase)
