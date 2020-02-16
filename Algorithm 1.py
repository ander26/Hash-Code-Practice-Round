from itertools import combinations 
import os

WRITING_FILE = "./Results/resulte.txt"
READING_FILE = "./Dataset/e_also_big.in"

def read_file(filepath):
    data = open(filepath, "r")
    lines = data.readlines()
    counter = 0
    for line in lines:
        line_data = line.strip().split(" ")
        if counter == 0:
            slices = int(line_data[0])
            types = int(line_data[1])
        else:
            pizzas = line_data
        counter += 1
    return slices, types, pizzas

def resolve_problem (slices, types, pizzas): 
    best_solution = []
    maximum_solution = 0
    for i in range(types,0,-1):
        comb = combinations(range(0, types), i) 
        for i in list(comb): 
            punctuation = fitness_function(pizzas, i)
            if punctuation <=slices:
                if punctuation == slices:
                    write_result(WRITING_FILE, i)
                    return
                else: 
                    if maximum_solution < punctuation:
                        print ("Solution improved from " + str(maximum_solution) + " to " + str(punctuation))
                        maximum_solution = punctuation
                        best_solution = i
                        write_result(WRITING_FILE, best_solution)

def fitness_function (pizzas, order_index):
    slices = 0
    for index in order_index:
        slices += int(pizzas[index])
    return slices

def write_result (name_file, ordered_pizzas):
    try:
        file = open(name_file, "x")
    except:
        os.remove(name_file)
        file = open(name_file, "x")
    file.write(str(len(ordered_pizzas)) + "\n")
    counter = 0
    for pizza in ordered_pizzas:
        file.write(str(pizza))
        if counter != len(ordered_pizzas) -1: 
            file.write(" ")
        counter +=1 

print ("Reading file: " + READING_FILE)
slices, types, pizzas = read_file (READING_FILE)

print ("Maximum number of slices: " + str (slices))
print ("Different types of pizza: " + str(types))
print ("Slices for each pizza: " +  str(pizzas))

resolve_problem(slices, types, pizzas)