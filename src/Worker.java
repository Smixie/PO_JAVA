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

import static java.awt.Color.white;

public class Worker extends Employee{
    private static EmplyeeActionListner actionListner;
    public static EmplyeeActionListner getAction(){return actionListner;}
    public Worker(int id, String name, String surname, double salary, String username) {
        super(id, name, surname, salary, username);
        actionListner = new EmplyeeActionListner(this);
    }
    private static ArrayList<Employee> worker = Worker.readFile("Data/employes.txt");
    public static ArrayList<Employee> getWorker(){ return worker;}
    public static void setWorker(ArrayList<Employee> worker){ Worker.worker = worker; }

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
        panelTop.setBackground(white);
    }
    public static JButton deleteProduct;
    public static void loadEmployeeShop(String user) throws IOException {
        panelTop = new JPanel();
        JPanel panelBot = new JPanel();
        frame = new JFrame();

        frame.setTitle("Employee-Shop");
        frame.setResizable(false);
        frame.setSize(800,600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.white);

        loadShop();

        panelBot.setBackground(white);

        panelTop.setBorder(new EmptyBorder(10,10,10,10));

        frame.add(panelTop, BorderLayout.PAGE_START);
        frame.add(panelBot,BorderLayout.PAGE_END);

        panelTop.setBackground(white);

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = Worker.loadRightPanel(user,frame.getWidth()/2);


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
        for (Employee em : worker) {
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
    public static ArrayList<Employee> readFile(String filename) {
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
                emp.add(new Worker(id,name,surname,salary,username));
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return emp;
    }
}
