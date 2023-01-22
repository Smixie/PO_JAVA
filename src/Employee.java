import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

abstract class Employee extends JFrame {
    private static EmplyeeActionListner actionListner;
    public static EmplyeeActionListner getAction(){return actionListner;}
    private String name,surname,username;
    private int id;
    private double salary;
    public static JButton logoutE;
    public static JFrame frame,addFrame,deleteFrame;
    public Employee(int id,String name,String surname,double salary,String username)
    {
        this.id = id;
        this.salary = salary;
        this.name = name;
        this.surname = surname;
        this.username = username;
        actionListner = new EmplyeeActionListner(this);
    }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setUsername(String username) {this.username = username;}
    public String getUsername(){return username; }
    public static JButton addProduct, deleteproductB;
    public static JButton addProductButton,pClear;
    public static JTextField pName,pPrice,pQuantity,pImage,dName;
    public static JPanel panelTop;
    public static void addProduct()
    {
        addFrame = new JFrame();

        addFrame.setTitle("Add product");
        addFrame.setResizable(false);
        addFrame.setSize(500,400);
        //addFrame.setLayout(new FlowLayout());
        addFrame.getContentPane().setBackground(Color.white);

        JLabel labelName = new JLabel("Name");
        labelName.setBounds(20,20,80,40);
        pName = new JTextField();
        pName.setBounds(140,20,300,40);
        pName.addActionListener(actionListner);

        JLabel labelPrice = new JLabel("Price");
        labelPrice.setBounds(20,80,80,40);
        pPrice = new JTextField();
        pPrice.setBounds(140,80,300,40);
        pPrice.addActionListener(actionListner);

        JLabel labelQuantity = new JLabel("Quantity");
        labelQuantity.setBounds(20,140,80,40);
        pQuantity = new JTextField();
        pQuantity.setBounds(140,140,300,40);
        pQuantity.addActionListener(actionListner);

        JLabel labelImage= new JLabel("Image path");
        labelImage.setBounds(20,200,80,40);
        pImage = new JTextField();
        pImage.setBounds(140,200,300,40);
        pImage.addActionListener(actionListner);

        addProductButton = new JButton("Add product");
        addProductButton.setBounds(100,260,300,40);
        addProductButton.addActionListener(actionListner);

        pClear = new JButton("Clear");
        pClear.setBounds(100,310,300,40);
        pClear.addActionListener(actionListner);

        addFrame.add(labelName);
        addFrame.add(labelQuantity);
        addFrame.add(labelPrice);
        addFrame.add(labelImage);

        addFrame.add(pName);
        addFrame.add(pPrice);
        addFrame.add(pQuantity);
        addFrame.add(pImage);
        addFrame.add(addProductButton);
        addFrame.add(pClear);

        addFrame.setLayout(null);
        addFrame.setLocationRelativeTo(null);
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setVisible(true);
    }

    public static void deleteProducts()
    {
        deleteFrame = new JFrame();

        deleteFrame.setTitle("Delete product");
        deleteFrame.setResizable(false);
        deleteFrame.setSize(300,200);
        //addFrame.setLayout(new FlowLayout());
        deleteFrame.getContentPane().setBackground(Color.white);

        JLabel labelName = new JLabel("Product name");
        labelName.setBounds(100,10,160,40);
        dName = new JTextField();
        dName.setBounds(40,60,200,40);
        dName.addActionListener(actionListner);

        deleteproductB = new JButton("Delete");
        deleteproductB.setBounds(100,110,80,30);
        deleteproductB.addActionListener(actionListner);

        deleteFrame.add(labelName);
        deleteFrame.add(dName);
        deleteFrame.add(deleteproductB);

        deleteFrame.setLayout(null);
        deleteFrame.setLocationRelativeTo(null);
        deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteFrame.setVisible(true);
    }
    public static void close(){};
    public static ArrayList<Employee> readFile(String filename) {
        ArrayList<Employee> mng = new ArrayList<>();

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
                mng.add(new Employee(id, name, surname, salary, username) {
                });
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mng;
    }

    public static void loadShop() throws IOException {

        panelTop.setLayout(new GridLayout(0, 6));
        ((GridLayout) panelTop.getLayout()).setHgap(5);
        ((GridLayout) panelTop.getLayout()).setVgap(25);


        for (Product product : Product.getProducts()) {
            ImageIcon icon;
            if (product.getQuantity() == 0) {
                BufferedImage image = ImageIO.read(new File(String.valueOf(product.getImage())));
                Graphics2D g2d = image.createGraphics();
                g2d.setColor(Color.RED);
                int x = image.getWidth() - 100;
                int y = image.getHeight() - 100;
                g2d.drawLine(x, y, x + 100, y + 100);
                g2d.drawLine(x + 100, y, x, y + 100);
                g2d.dispose();
                icon = new ImageIcon(image);
            }
            else {
                icon = new ImageIcon(String.valueOf(product.getImage()));
            }

            JLabel label = new JLabel(String.format("%s %.2fzł",product.getName(),product.getPrice()),icon,JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.BOTTOM);
            label.setHorizontalTextPosition(JLabel.CENTER);
            panelTop.add(label);
        }
        panelTop.setBackground(Color.white);
        panelTop.setBorder(new EmptyBorder(10,10,10,10));
    }
}
