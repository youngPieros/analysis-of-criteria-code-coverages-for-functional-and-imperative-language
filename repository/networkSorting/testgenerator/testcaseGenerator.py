import config
import random
import string

legal_network_chars = string.ascii_lowercase + string.ascii_uppercase

number_of_networks = config.number_of_valid_networks + config.number_of_invalid_networks
for i in range(number_of_networks):
    network = ['' for _ in range(config.network_size)]
    numbers = [i for i in range(config.network_size)]
    random.shuffle(numbers)
    for j in range(config.number_of_stages):
        network_pairs = [i for i in range(config.network_size)]
        random.shuffle(network_pairs)
        if (i < config.number_of_valid_networks):
            for k in range(config.network_size):
                if config.network_size % 2 == 1 and network_pairs[k] == config.network_size - 1:
                    network[k] += '-'
                else:
                    network[k] += legal_network_chars[network_pairs[k] // 2]
        else:
            if (config.network_size % 2 == 1):
                for k in range(config.network_size):
                    network[network_pairs[k]] += legal_network_chars[k // 2]
            else:
                for k in range(config.network_size):
                    network[network_pairs[k]] += legal_network_chars[k // 3]
    print(str(config.network_size) + " " + str(config.number_of_stages))
    for net in network:
        print(net, end=" ")
    print()
    for number in numbers:
        print(number, end=" ")
    print()
print ("0 0")

