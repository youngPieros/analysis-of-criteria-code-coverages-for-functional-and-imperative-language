import random
import config

numbers = [i for i in range(config.number_counts)]
random.shuffle(numbers)
output = ""
for number in numbers:
    output += str(number) + " "


print(config.number_counts)
print(output.strip())