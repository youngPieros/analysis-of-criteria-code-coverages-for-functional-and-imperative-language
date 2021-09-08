import config
from random import randint

number_of_testcases = config.number_of_testcases_with_available_searched_member + \
                      config.number_of_testcases_without_available_searched_member
print(number_of_testcases + config.contain_empty_array_test)

for i in range(number_of_testcases):
    collection = [2 * j + 1 for j in range(config.size_of_searched_collection)]
    searched_member = 0
    if i < config.number_of_testcases_with_available_searched_member:
        index = randint(0, config.size_of_searched_collection - 1)
        searched_member = collection[index]
    else:
        searched_member = 2 * randint(0, config.size_of_searched_collection)
    print(len(collection), end=" ")
    print(searched_member, end=" ")
    for member in collection:
        print(member, end=" ")
    print()

if config.contain_empty_array_test:
    print("0 10")

