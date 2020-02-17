import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PizzaProblem {
    public static void main(String[] args) {
        String filepath = "/home/unai/IdeaProjects/HashCode_Practice_Round/datasets/e_also_big.in";
        readFile(filepath);

        DataStructure dataStructure = DataStructure.getMyDataStructure();

        initializePopulationMatrix();
        List<List<Integer>> populationMatrix = dataStructure.getPopulationMatrix();
        List<Integer> pizzas = dataStructure.getPizzas();
        List<Integer> evaluation = evaluate(populationMatrix, pizzas);
        List<List<Integer>> maxInds = new ArrayList<>();
        List<Integer> maxValues = new ArrayList<>();
        for (int g = 0 ; g < dataStructure.getGenerations() ; g++) {
            maxInds.add(populationMatrix.get(evaluation.indexOf(Collections.max(evaluation))));
            maxValues.add(Collections.max(evaluation));
            evaluation = evaluate(populationMatrix, pizzas);
        }

        System.out.println("Max Inds: ");
        printListOfLists(maxInds);

        System.out.println("");

        System.out.println("Max Values: ");
        printList(maxValues);

    }

    public static void readFile(String filepath) {
        try {
            DataStructure dataStructure = DataStructure.getMyDataStructure();
            int counter = 0;
            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] line = myReader.nextLine().split(" ");
                if (counter == 0) {
                    dataStructure.setSlices(Integer.parseInt(line[0]));
                    dataStructure.setTypes(Integer.parseInt(line[1]));
                } else {
                    dataStructure.setPizzas(convertPizzasToInt(line));
                }
                counter++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static List<Integer> convertPizzasToInt(String[] line) {
        List<Integer> pizzas = new ArrayList<>();
        for (int i = 0 ; i < line.length ; i++) {
            pizzas.add(Integer.parseInt(line[i]));
        }
        return pizzas;
    }

    public static void initializePopulationMatrix() {
        DataStructure dataStructure = DataStructure.getMyDataStructure();
        int sumSlices = 0;
        List<Integer> sample;
        List<Integer> pizzas = dataStructure.getPizzas();
        for (int i = 0 ; i < dataStructure.getPopulationMatrix().size() ; i++) {
            sample = createSample();
            for (int j = 0 ; j < dataStructure.getPopulationMatrix().get(i).size() ; j++) {
                dataStructure.getPopulationMatrix().set(i, sample);
            }
        }
    }

    public static List<Integer> createSample() {
        DataStructure dataStructure = DataStructure.getMyDataStructure();
        Random random = new Random();
        List<Integer> sample = new ArrayList<>();
        int sumSlices = 0;
        boolean incorrect = true;
        while(incorrect) {
            int randomType = random.nextInt(dataStructure.getTypes());
            if (!sample.contains(randomType)) {
                if (typeIsAllowed(dataStructure, sumSlices, randomType)) {
                    sumSlices += dataStructure.getPizzas().get(randomType);
                    sample.add(randomType);
                } else {
                    incorrect = false;
                }
            }
        }
        return sample;
    }

    public static boolean typeIsAllowed(DataStructure dataStructure, int sumSlices, int randomType) {
        sumSlices += dataStructure.getPizzas().get(randomType);
        if(sumSlices <= dataStructure.getSlices()) {
            return true;
        } else {
            return false;
        }
    }

    public static int fitness(List<Integer> ind, List<Integer> pizzas) {
        int fitness = 0;
        for (int i = 0 ; i < ind.size() ; i++) {
            fitness += pizzas.get(ind.get(i));
        }
        return fitness;
    }

    public static List<Integer> evaluate(List<List<Integer>> populationMatrix, List<Integer> pizzas) {
        List<Integer> evaluation = new ArrayList<>();
        for (int i = 0 ; i < populationMatrix.size() ; i++) {
            evaluation.add(fitness(populationMatrix.get(i), pizzas));
        }
        return evaluation;
    }

    public static void printList(List<Integer> list) {
        for (int i = 0 ; i < list.size() ; i++) {
            System.out.print(list.get(i) + ", ");
        }
        System.out.println("");
    }

    public static void printListOfLists(List<List<Integer>> listOfLists) {
        for (int i = 0 ; i < listOfLists.size() ; i++) {
            for (int j = 0 ; j < listOfLists.get(i).size() ; j++) {
                System.out.print(listOfLists.get(i).get(j) + " ");
            }
            System.out.println("");
            System.out.println("");
        }
    }
}
