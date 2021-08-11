import config

testcases = []
for i in range(1, config.number_of_testcases + 1):
    file = open('testcase_' + str(i), mode='r')
    contents = file.read()
    file.close()
    testcases.append(contents)

print(config.number_of_testcases)

for testcase in testcases:
    print(testcase)

