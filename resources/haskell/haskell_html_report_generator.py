import sys


values = [{'name': 'td.bar', 'replaced': 'td.bar2'}, {'name': 'table.bar', 'replaced': 'table.bar2'}, {'name': 'class="bar"', 'replaced': 'class="bar2"'}]


filenames = sys.argv[1:]
for filename in filenames:
	file = open(filename, mode='r')
	file_content = file.read()
	file.close()
	for value in values:
		file_content = file_content.replace(value['name'], value['replaced'])
	file = open(filename, mode='w')
	file.write(file_content)
	file.close()
