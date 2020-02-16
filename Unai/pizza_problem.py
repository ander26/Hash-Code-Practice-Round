import random

def read_file(filepath):
	pizzas_aux = []
	data = open(filepath, "r")
	lines = data.readlines()
	counter = 0
	for line in lines:
		line_data = line.strip().split(" ")
		if counter == 0:
			slices = int(line_data[0])
			types = int(line_data[1])
		else:
			pizzas = pizzas_aux + line_data
		counter += 1
	return slices, types, pizzas

def initialize_population_matrix(population_size, types, pizzas, slices):
	sum_slices = 0
	sample = []
	incorrect = True
	population_matrix = [[ 0 for i in range(population_size)] for j in range(types)]
	for i in range(len(population_matrix)):
		sample = create_sample(types, slices, pizzas)
		for j in range(len(population_matrix[i])):
			population_matrix[i] = sample
	return population_matrix

def create_sample(types, slices, pizzas):
	sample = []
	sum_slices = 0
	incorrect = True
	while(incorrect):
		random_type = random.randrange(types)
		if random_type not in sample:
			if type_is_allowed(sum_slices, random_type, slices, pizzas):
				sum_slices += int(pizzas[random_type])
				sample.append(random_type)
			else:
				incorrect = False
	return sample

def type_is_allowed(sum_slices, random_type, slices, pizzas):
	sum_slices += int(pizzas[random_type])
	if sum_slices <= slices:
		return True
	else:
		return False

def fitness(ind, pizzas):
	fitness = 0
	for i in range(len(ind)):
		fitness += int(pizzas[ind[i]])
	return fitness

def evaluate(population_matrix, pizzas):
	evaluation = []
	for i in range(len(population_matrix)):
		evaluation.append(fitness(population_matrix[i], pizzas))
	return evaluation
		

def crossover(population_matrix, prob, index_parents):
	offsprings = population_matrix
	for i in range(len(index_parents)):
		print(index_parents)
		probability_cross = random.random()
		if prob < probability_cross:
			p1 = population_matrix[index_parents[i]]
			p2 = population_matrix[index_parents[i+1]]
			offsprings[i]   = (1/3)*p1+(2/3)*p2
			offsprings[i+1] = (2/3)*p1+(1/3)*p2
	return offsprings

def generate_random_integers(range_max, number):
	random_integers = []	
	for i in range(number):
		random_integers.append(random.randrange(0, range_max))
	return random_integers

def tournament_selection(population_matrix, population_size, evaluation, number, types):
	indexes = [[ 0 for i in range(population_size)] for j in range(types)]
	for i in range(len(evaluation)):
		random_positions = generate_random_integers(len(evaluation), number)
		ind1_evaluation = population_matrix[random_positions[0]]
		ind2_evaluation = population_matrix[random_positions[1]]
		if ind1_evaluation > ind2_evaluation:		
			indexes[i] = ind1_evaluation
		else:
			indexes[i] = ind2_evaluation
	return indexes
	
def mutation(population_matrix, pmutation, types):
	incorrect = True
	offsprings = population_matrix
	for i in range(len(offsprings)):
		probability_mutation = random.random()
		if probability_mutation < pmutation:
			random_mutation_position = random.randrange(0, len(offsprings[i]))
			while(incorrect):
				random_new_pizza_type = random.randrange(0, types)
				if random_new_pizza_type not in offsprings[i]:
					offsprings[random_mutation_position] = random_new_pizza_type
					incorrect = False

	return offsprings

def main():
	max_values = []
	max_inds = []
	slices, types, pizzas = read_file("c_medium.in")

	generations = 10
	tournament_size = 2
	prob_crossover = 0.9
	prob_mutation = 0.05
	population_size = 100
	
	population_matrix = initialize_population_matrix(population_size, types, pizzas, slices)
	evaluation = evaluate(population_matrix, pizzas)
	for g in range(generations):
		index_parents = tournament_selection(population_matrix, population_size, evaluation, tournament_size, types)
		population_matrix = initialize_population_matrix(population_size, types, pizzas, slices)	
		#offspring1 = crossover(population_matrix, prob_crossover, index_parents)
		#offspring2 = mutation(population_matrix, prob_mutation, types)
		#population_matrix = offspring2
		evaluation = evaluate(population_matrix, pizzas)
		max_inds.append(population_matrix[evaluation.index(max(evaluation))])
		max_values.append(max(evaluation))
		
	print(max_values)
	print(max_inds)

if __name__ == "__main__":
	main()

	
					
