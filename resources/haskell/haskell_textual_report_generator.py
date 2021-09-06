import re
import sys

values = [{'name': 'expressions_used', 'total': '', 'percentage': ''},{'name': 'boolean_coverage', 'total': '', 'percentage': ''},{'name': 'guards', 'total': '', 'percentage': ''},{'name': 'if_conditions', 'total': '', 'percentage': ''},{'name': 'qualifiers', 'total': '', 'percentage': ''},{'name': 'alternatives_used', 'total': '', 'percentage': ''},{'name': 'local_declarations_used', 'total': '', 'percentage': ''},{'name': 'top-level_declarations', 'total': '', 'percentage': ''}]

file = open('haskell_report.html',mode='r')
file_content = file.read()
file.close()

total_regex = re.compile(r'(\d+/\d+)')
percentage_regex = re.compile(r'(\d+)%')

for i in range(8):
    coverage = input()
    values[i]['total'] = total_regex.search(coverage).group(1)
    values[i]['percentage'] = percentage_regex.search(coverage).group(1)

for value in values:
    file_content = file_content.replace('$' + value['name'] + '_total', value['total'])
    file_content = file_content.replace('$' + value['name'] + '_percentage', value['percentage'] + '%')

print(file_content)
