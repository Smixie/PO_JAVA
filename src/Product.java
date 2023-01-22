import javax.swing.*;
import java.util.ArrayList;

public class Product {
    private int quantity;
    private String name;
    private double price;
    private ImageIcon image;
    public static ArrayList<Product> products = FileReader.readProducts("Data/products.txt");
    public static ArrayList<Product> getProducts(){return products;}
    public Product(int quantity, String name, double price, ImageIcon image){
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.image = image;
    }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public String getName()
    {
        return name;
    }


    public double getPrice()
    {
        return price;
    }


    public ImageIcon getImage()
    {
        return image;
    }

}
