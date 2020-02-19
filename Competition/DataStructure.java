package Competition;

import java.util.ArrayList;
import java.util.List;

public class DataStructure {
    private List<Integer> pizzas;
    private int generations;
    private int slices;
    private int types;
    private List<List<Integer>> populationMatrix;
    private int populationSize;
    private static DataStructure myDataStructure;

    private DataStructure() {
        this.generations = 500;
        this.populationSize = 500;
        this.populationMatrix = createEmptyPopulationMatrix();
    }

    private List<List<Integer>> createEmptyPopulationMatrix() {
        List<List<Integer>> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        list2.add(0);
        for (int i = 0 ; i < populationSize ; i++) {
            list1.add(list2);
        }
        return list1;
    }

    public static DataStructure getMyDataStructure() {
        if (myDataStructure == null) {
            myDataStructure = new DataStructure();
        }
        return myDataStructure;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }


    public List<Integer> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Integer> pizzas) {
        this.pizzas = pizzas;
    }

    public int getSlices() {
        return slices;
    }

    public void setSlices(int slices) {
        this.slices = slices;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public List<List<Integer>> getPopulationMatrix() {
        return populationMatrix;
    }

    public void setPopulationMatrix(List<List<Integer>> populationMatrix) {
        this.populationMatrix = populationMatrix;
    }
}
