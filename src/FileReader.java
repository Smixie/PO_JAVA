import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    public static ArrayList<Product> readProducts(String filename) {
        ArrayList<Product> products = new ArrayList<>();

        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[2]);
                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                ImageIcon image = new ImageIcon(parts[3]);
                products.add(new Product(id, name, price, image));
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static ArrayList<Employee> readEmployee(String filename) {
        ArrayList<Employee> emp = new ArrayList<>();

        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String surname = parts[2];
                double salary = Double.parseDouble(parts[3]);
                String username = parts[4];
                emp.add(new Employee(id,name,surname,salary,username));
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return emp;
    }
    public static ArrayList<Manager> readManager(String filename) {
        ArrayList<Manager> mng = new ArrayList<>();

        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String surname = parts[2];
                double salary = Double.parseDouble(parts[3]);
                String username = parts[4];
                mng.add(new Manager(id,name,surname,salary,username));
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mng;
    }
}
