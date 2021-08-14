import config

testcases_files = []
for i in range(1, config.number_of_testcases + 1):
    testcases_files.append('testcase_' + str(i))

for testcase in testcases_files:
    print(testcase)

