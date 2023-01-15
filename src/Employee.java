import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.Color.white;

public class Employee {
    private static EmplyeeActionListner actionListner;
    public static EmplyeeActionListner getAction(){return actionListner;}
    private String name;
    private String surname;
    private int id;
    private double salary;
    private String username;
    public static JButton logoutE;
    public static JFrame frame,addFrame;

    public Employee(int id,String name,String surname,double salary,String username)
    {
        this.id = id;
        this.salary = salary;
        this.name = name;
        this.surname = surname;
        this.username = username;
        actionListner = new EmplyeeActionListner(this);
    }

    private static ArrayList<Employee> employee = FileReader.readEmployee("Data/employes.txt");;
    public static ArrayList<Employee> getEmployee(){ return employee;}
    public static void setEmployee(ArrayList<Employee> employee){ Employee.employee = employee; }
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
    public static JButton addEmployee,deleteEmployee,addProduct,deleteProduct;
    public static JButton addProductButton,pClear;
    public static JTextField pName,pPrice,pQuantity,pImage;
    public static JPanel panelTop;
    public static void loadEmployeeShop(String user) throws IOException{


        panelTop = new JPanel();
        JPanel panelBot = new JPanel();
        frame = new JFrame();

        frame.setTitle("Employee-Shop");
        frame.setResizable(false);
        frame.setSize(800,600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.white);


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
        panelTop.setBackground(white);
        panelBot.setBackground(white);

        panelTop.setBorder(new EmptyBorder(10,10,10,10));

        frame.add(panelTop, BorderLayout.PAGE_START);
        frame.add(panelBot,BorderLayout.PAGE_END);

        panelTop.setBackground(white);

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = Employee.loadRightPanel(user,frame.getWidth()/2);


        leftPanel.setPreferredSize(new Dimension((frame.getWidth()/2) + 100, 100));
        leftPanel.setBackground(Color.white);

        addProduct = new JButton("ADD PRODUCT");
        addProduct.setPreferredSize(new Dimension(200,35));
        addProduct.addActionListener(actionListner);

        deleteProduct = new JButton("DELETE PRODUCT");
        deleteProduct.setPreferredSize(new Dimension(200,35));
        deleteProduct.addActionListener(actionListner);

        logoutE = new JButton("LOGOUT");
        logoutE.setPreferredSize(new Dimension(200,35));
        logoutE.addActionListener(actionListner);
        rightPanel.add(logoutE);

        leftPanel.setLayout(new FlowLayout());
        leftPanel.add(addProduct);
        leftPanel.add(deleteProduct);

        rightPanel.setPreferredSize(new Dimension((frame.getWidth()/2) - 140, 100));

        panelBot.add(leftPanel);
        panelBot.add(rightPanel);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static JPanel loadRightPanel(String searchName, int frameSize){
        JPanel rightPanel = new JPanel();
        for (Employee em : employee) {
            if (em.getUsername().equals(searchName)) {
                rightPanel.setPreferredSize(new Dimension(frameSize - 140, 100));

                JLabel a = new JLabel("JESTEŚ ZALOGOWANY JAKO ");
                JLabel b = new JLabel(String.format("IMIE: %s",em.getName()));
                JLabel c = new JLabel(String.format("NAZWISKO: %s",em.getSurname()));
                JLabel d= new JLabel("Masz uprawnienia: employee");

                a.setFont(new Font("Arial",Font.BOLD,15));
                b.setFont(new Font("Arial",Font.BOLD,15));
                c.setFont(new Font("Arial",Font.BOLD,15));
                d.setFont(new Font("Arial",Font.BOLD,15));

                rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
                rightPanel.setBackground(Color.white);

                rightPanel.add(a);
                rightPanel.add(b);
                rightPanel.add(c);
                rightPanel.add(d);
                break;
            }
        }
        return rightPanel;
    }

    public static void close(){
        frame.dispose();
    }
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
}
