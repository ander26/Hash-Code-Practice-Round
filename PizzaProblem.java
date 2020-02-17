import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

public class PizzaProblem {

    private static final String READING_FILE = "dataset/d_quite_big.in";
    private static final String WRITING_FILE = "results/resultD.txt";

    public static void main(String[] args) {

        readFile(READING_FILE);

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
            populationMatrix = mutate(evaluation, populationMatrix);
            evaluation = evaluate(populationMatrix, pizzas);
        }

        //System.out.println("Max Inds: ");
        //printListOfLists(maxInds);

        System.out.println("");

        System.out.println("Max Values: ");
        printList(maxValues);

        System.out.println("Best Value: " + Collections.max(evaluation));

        writeFile(WRITING_FILE, maxInds.get(maxValues.indexOf(Collections.max(maxValues))));

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

    public static List<List<Integer>> mutate (List<Integer> evaluation, List<List<Integer>> populationMatrix) {
        DataStructure dataStructure = DataStructure.getMyDataStructure();
        for (int i = 0; i < 10; i++) {
            int maxValue = Collections.max(evaluation);
            List<Integer> individual = new ArrayList<Integer>(populationMatrix.get(evaluation.indexOf(Collections.max(evaluation))));
            Random random = new Random();
            int positionChange = random.nextInt (individual.size());
            boolean incorrect = true;
            while(incorrect) {
                int randomType = random.nextInt(dataStructure.getTypes());
                if (!individual.contains(randomType)) {
                    individual.set(positionChange, randomType);
                    incorrect = false;
                }
            }
            if (fitness(individual, dataStructure.getPizzas()) > maxValue && fitness(individual, dataStructure.getPizzas()) <=
                    dataStructure.getSlices()) {
                populationMatrix.set(evaluation.indexOf(Collections.max(evaluation)), individual);
            }
            evaluation.remove(evaluation.indexOf(Collections.max(evaluation)));
        }
        return populationMatrix;
    }

    public static void writeFile(String filepath, List<Integer> result) {
        try {
            File myObj = new File(filepath);
            if (!myObj.createNewFile()) {
                myObj.delete();
                myObj.createNewFile();
            }
            FileWriter myWriter = new FileWriter(filepath);
            myWriter.write(result.size() + "\n");
            for (int i = 0; i < result.size(); i++) {
                myWriter.write(result.get(i) + "");
                if (i != result.size() -1) {
                    myWriter.write(" ");
                }
            }
            myWriter.close();
        } catch (Exception e) {
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
        List<Integer> sample;
        for (int i = 0 ; i < dataStructure.getPopulationMatrix().size() ; i++) {
            sample = createSample();
            dataStructure.getPopulationMatrix().set(i, sample);
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
