import config
import random

testcases_files = []

for n in config.n_range:
    for k in config.k_possible_values:
        for i in range(config.number_of_testcases):
            testcase = "{} {}\n".format(n, k)
            for j in range(n):
                testcase += str(random.randint(0, config.s_range)) + " "
            testcase += "\n"
            testcase_filename = "testcase_" + str(len(testcases_files) + 1)
            file = open(testcase_filename, "w")
            file.write(testcase)
            file.close()
            testcases_files.append(testcase_filename)

for testcase in testcases_files:
    print(testcase)
