package Competition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public  class Utilities {
    private static final String READING_FILE = "dataset/d_quite_big.in";
    private static final String WRITING_FILE = "results/resultD.txt";

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
                    dataStructure.setPizzas(convertObjectToInt(line));
                }
                counter++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static List<Integer> convertObjectToInt (String[] line) {
        List<Integer> objects = new ArrayList<>();
        for (int i = 0 ; i < line.length ; i++) {
            objects.add(Integer.parseInt(line[i]));
        }
        return objects;
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



}
