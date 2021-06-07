import sys
import random


n = int (sys.argv[1])

numbers = [i for i in range(n)]
random.shuffle(numbers)
output = ""
for number in numbers:
	output += str (number) + " "


print(output.strip())
