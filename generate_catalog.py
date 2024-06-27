import random

def generate_unique_numbers_to_file(n, filename):
    generated_numbers = set()
    with open(filename, 'w') as f:
        while len(generated_numbers) < n:
            num1 = random.randint(100, 999)
            num2 = random.randint(100, 999)
            num3 = random.randint(100, 999)
            triplet = (num1, num2, num3)
            if triplet not in generated_numbers:
                generated_numbers.add(triplet)
                f.write(f"{num1} {num2} {num3}\n")


lines = 50000
output_name = 'casos/caso' + str(lines) + '.txt'
generate_unique_numbers_to_file(lines, output_name);
