import config
import random
import string

legal_network_chars = string.ascii_lowercase + string.ascii_uppercase


def generate_valid_network(network_size, number_of_stages):
    network = ['' for _ in range(network_size)]
    for i in range(number_of_stages):
        network_pairs = [x for x in range(network_size)]
        random.shuffle(network_pairs)
        for j in range(network_size):
            if network_size % 2 == 1 and network_pairs[j] == network_size - 1:
                network[j] += '-'
            else:
                network[j] += legal_network_chars[network_pairs[j] // 2]
    return network


def generate_invalid_network(network_size, number_of_stages):
    network = ['' for _ in range(network_size)]
    for i in range(number_of_stages):
        network_pairs = [x for x in range(network_size)]
        random.shuffle(network_pairs)
        for j in range(network_size):
            if (network_size % 2 == 1):
                # for k in range(network_size):
                network[network_pairs[j]] += legal_network_chars[j // 2]
            else:
                # for k in range(network_size):
                network[network_pairs[j]] += legal_network_chars[j // 3]
    return network


def print_in_line(arr):
    for m in arr:
        print(m, end=" ")
    print()


number_of_networks = config.number_of_valid_networks + config.number_of_invalid_networks
for network_size in config.network_sizes:
    for i in range(number_of_networks):
        network = ['' for _ in range(network_size)]
        numbers = [n for n in range(network_size)]
        random.shuffle(numbers)
        if (i < config.number_of_valid_networks):
            network = generate_valid_network(network_size, config.number_of_stages)
        else:
            network = generate_invalid_network(network_size, config.number_of_stages)
        print_in_line([network_size, config.number_of_stages])
        print_in_line(network)
        print_in_line(numbers)
    print_in_line([network_size, config.number_of_stages])
    print_in_line(generate_valid_network(network_size, config.number_of_stages))
    print_in_line([n for n in range(network_size)])

print ("0 0")

