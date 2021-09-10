import random
import config


testcases_files = []


def get_random_numbers(n, include_repetitive_numbers):
    numbers = [i for i in range(n)]
    if include_repetitive_numbers:
        first_part = numbers[0:(n // 2) + 1]
        second_part = numbers[0:n - len(first_part)]
        numbers = first_part + second_part
    random.shuffle(numbers)
    return numbers


for i in range(config.testcase_numbers):
    for j in config.include_repetitive_numbers:
        numbers = get_random_numbers(config.number_counts, j)
        testcase = str(len(numbers)) + "\n"
        testcase += " ".join([str(number) for number in numbers])
        testcase_filename = "testcase_" + str(len(testcases_files) + 1)
        file = open(testcase_filename, "w")
        file.write(testcase)
        file.close()
        testcases_files.append(testcase_filename)

testcase_filename = "testcase_" + str(len(testcases_files) + 1)
file = open(testcase_filename, "w")
file.write("0\n\n")
file.close()
testcases_files.append(testcase_filename)

for testcase in testcases_files:
    print(testcase)
