package at.fhv.itm2018;

import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixGenerator {
    private static final double MIN = 0.0;
    private static final double MAX = 100.0;

    public static void main(String[] args) {
//        if (args.length != 2 || args[0].isEmpty() || args[1].isEmpty()) {
//            System.out.println("Wrong argument count. Usage: MatrixGenerator.jat <size> <filepath>");
//            System.exit(0);
//        }

        try {
            int size = 3;
            File file = new File("C:\\Users\\ctsch\\Documents\\Projects\\BigData\\aufgabe6openmpi\\aufgabe6openmpi\\src\\at\\fhv\\itm2018\\matrix\\Matrix4.txt");
            if(!file.createNewFile()) {
                System.out.println("File already exists.");
                System.exit(0);
            }
            if(!file.isFile()) {
                System.out.println("File was not created or is a directory.");
                System.exit(0);
            }
            try (FileWriter fileWriter = new FileWriter(file)) {
                for(int i = 0; i < size; i++) {
                    for(int j = 0; j < size; j++) {
                        double rand = ThreadLocalRandom.current().nextDouble(MIN, MAX);
                        fileWriter.write(rand + ";");
                    }
                    fileWriter.write("\n");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid size argument. First argument has to be an integer.");
        } catch (IOException e) {
            System.out.println("IOException occured.");
            e.printStackTrace();
        }
    }
}
