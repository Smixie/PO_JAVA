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

public class Manager extends Employee{
    public static JButton addEmployee,deleteEmployee,addProduct,deleteProduct,addEmp;
    public static JTextField eSurname,ePassword,eLogin,eSalary;
    public static JButton logout;
    public static JFrame frame;
    public static JPanel panelTop;
    private static JScrollPane scroll;
    private static ManagerActionListener actionListener;
    public Manager(int id, String name, String surname, double salary,String username) {
        super(id, name, surname, salary,username);
        actionListener = new ManagerActionListener(this);
    }
    private static ArrayList<Employee> managers;
    public static ArrayList<Employee> getManagers() { return managers;}
    public static void setManagers(ArrayList<Employee> managers) {
        Manager.managers = managers;
    }
    public static JPanel loadRightPanel(String searchName, int frameSize){
        managers = Manager.readFile("Data/managers.txt");
        JPanel rightPanel = new JPanel();
        for (Employee manager : managers) {
            if (manager.getUsername().equals(searchName)) {
                rightPanel.setPreferredSize(new Dimension(frameSize - 140, 100));

                JLabel a = new JLabel("JESTEŚ ZALOGOWANY JAKO ");
                JLabel b = new JLabel(String.format("IMIE: %s",manager.getName()));
                JLabel c = new JLabel(String.format("NAZWISKO: %s",manager.getSurname()));
                JLabel d= new JLabel("Masz uprawnienia: manager");

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
    public static void loadManager(String user) throws IOException {

        frame = new JFrame();
        panelTop = new JPanel();
        JPanel panelBot = new JPanel();
        //scroll = new JScrollPane(panelTop);

        frame.setTitle("Shop");
        frame.setResizable(false);
        frame.setSize(900,700);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.white);

        //panelTop.setPreferredSize(new Dimension(780,350));

//        scroll.setViewportView(panelTop);
//        //scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        scroll.getVerticalScrollBar().setUnitIncrement(16);

        loadShop();
        panelBot.setBackground(Color.white);
        frame.add(panelTop, BorderLayout.PAGE_START);
        frame.add(panelBot,BorderLayout.PAGE_END);

        panelTop.setBackground(Color.white);

        JPanel rightPanel = Manager.loadRightPanel(user,frame.getWidth()/2);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension((frame.getWidth()/2) + 100, 100));

        addEmployee = new JButton("HIRE EMPLOYEE");
        addEmployee.setPreferredSize(new Dimension(200,35));
        addEmployee.addActionListener(actionListener);

        deleteEmployee = new JButton("FIRE EMPLOYEE");
        deleteEmployee.setPreferredSize(new Dimension(200,35));
        deleteEmployee.addActionListener(actionListener);

        addProduct = new JButton("ADD PRODUCT");
        addProduct.setPreferredSize(new Dimension(200,35));
        addProduct.addActionListener(actionListener);

        deleteProduct = new JButton("DELETE PRODUCT");
        deleteProduct.setPreferredSize(new Dimension(200,35));
        deleteProduct.addActionListener(actionListener);

        logout = new JButton("LOGUT");
        logout.setPreferredSize(new Dimension(200,35));
        logout.addActionListener(actionListener);

        leftPanel.setLayout(new FlowLayout());
        leftPanel.add(addEmployee);
        leftPanel.add(deleteEmployee);
        leftPanel.add(addProduct);
        leftPanel.add(deleteProduct);
        rightPanel.add(logout);

        leftPanel.setBackground(Color.white);

        panelBot.add(leftPanel);
        panelBot.add(rightPanel);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void close(){
        frame.dispose();
    }
    public static void addEmployee()
    {
        addFrame = new JFrame();
        addFrame.setResizable(false);
        addFrame.setSize(500,400);
        addFrame.getContentPane().setBackground(Color.white);

        JLabel labelName = new JLabel("Name");
        labelName.setBounds(20,20,80,30);
        pName = new JTextField();
        pName.setBounds(140,20,300,30);
        pName.addActionListener(actionListener);

        JLabel labelPrice = new JLabel("Surname");
        labelPrice.setBounds(20,70,80,40);
        eSurname = new JTextField();
        eSurname.setBounds(140,70,300,40);
        eSurname.addActionListener(actionListener);

        JLabel labelQuantity = new JLabel("salary");
        labelQuantity.setBounds(20,130,80,40);
        eSalary = new JTextField();
        eSalary.setBounds(140,130,300,40);
        eSalary.addActionListener(actionListener);

        JLabel labelPassword= new JLabel("Password");
        labelPassword.setBounds(20,190,80,40);
        ePassword = new JTextField();
        ePassword.setBounds(140,190,300,40);
        ePassword.addActionListener(actionListener);

        addEmp = new JButton("Add employee");
        addEmp.setBounds(100,250,300,40);
        addEmp.addActionListener(actionListener);

        pClear = new JButton("Clear");
        pClear.setBounds(100,310,300,40);
        pClear.addActionListener(actionListener);

        addFrame.add(labelPassword);
        addFrame.add(labelName);
        addFrame.add(labelQuantity);
        addFrame.add(labelPrice);

        addFrame.add(pName);
        addFrame.add(eSurname);
        addFrame.add(eSalary);
        addFrame.add(ePassword);
        addFrame.add(addEmp);
        addFrame.add(pClear);

        addFrame.setLayout(null);
        addFrame.setLocationRelativeTo(null);
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setVisible(true);
    }

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
                mng.add(new Manager(id,name,surname,salary,username));
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mng;
    }
}
