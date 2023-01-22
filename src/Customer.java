import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.Color.white;

public class Customer {
    private static CustomerActionListener action;
    public static CustomerActionListener getAction(){return action;}
    public static JButton logoutE,koszyk,checkoutButton;
    public static JFrame frame;
    private int id;
    public static JPanel leftPanel,panelTop;
    private String surname,login,name;
    private static ArrayList<Customer> customers = FileReader.readCustomers("Data/customers.txt");
    public Customer(int id,String name,String surname,String login){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        action = new CustomerActionListener(this);
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public static ArrayList<Customer> getCustomers() {
        return customers;
    }
    public void setCustomers(ArrayList<Customer> customers) {
        Customer.customers = customers;
    }
    private static ArrayList<Cart> cart = new ArrayList<Cart>();
    public static ArrayList<Cart> getCart(){return cart;}
    public static void loadCustomeShop(String user) throws IOException {
        panelTop = new JPanel();
        JPanel panelBot = new JPanel();

        frame = new JFrame();

        frame.setTitle("Customer-Shop");
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
            JPanel total = new JPanel();
            total.setLayout(new BoxLayout(total, BoxLayout.Y_AXIS));
            total.setBackground(white);

            JLabel label = new JLabel(String.format("%s %.2fzł",product.getName(),product.getPrice()),icon,JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.BOTTOM);
            label.setHorizontalTextPosition(JLabel.CENTER);
            koszyk = new JButton("Add to cart");
            koszyk.setActionCommand(product.getName());
            koszyk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Customer.addToCart(product);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            koszyk.setPreferredSize(new Dimension(100,20));
            koszyk.setVerticalTextPosition(JButton.BOTTOM);
            koszyk.setHorizontalTextPosition(JLabel.CENTER);

            total.add(label);
            total.add(koszyk);

            panelTop.add(total);
        }
        panelTop.setBackground(white);
        panelBot.setBackground(white);

        panelTop.setBorder(new EmptyBorder(10,10,10,10));

        frame.add(panelTop, BorderLayout.PAGE_START);
        frame.add(panelBot,BorderLayout.PAGE_END);

        panelTop.setBackground(white);

        leftPanel = new JPanel();
        JPanel rightPanel = Customer.loadRightPanel(user,frame.getWidth()/2);

        leftPanel.setPreferredSize(new Dimension((frame.getWidth()/2) + 100, 120));
        leftPanel.setBackground(Color.white);

        JPanel bottom = new JPanel();
        bottom.setBackground(white);
        bottom.setPreferredSize(new Dimension((frame.getWidth()/2) - 200, 20));


        logoutE = new JButton("LOGOUT");
        logoutE.setPreferredSize(new Dimension(100,20));
        logoutE.addActionListener(action);


        // Checkout
        checkoutButton = new JButton("CHECKOUT");
        checkoutButton.setPreferredSize(new Dimension(100,20));
        checkoutButton.addActionListener(action);

        bottom.add(checkoutButton);
        bottom.add(logoutE);

        rightPanel.add(bottom);
        rightPanel.setLayout(new GridLayout(5,1));

        leftPanel.setLayout(new FlowLayout());

        panelBot.add(leftPanel);
        panelBot.add(rightPanel);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void close(){
        frame.dispose();
    }
    public static JPanel loadRightPanel(String searchName, int frameSize){
        JPanel newPanel = new JPanel();
        for (Customer cust : Customer.getCustomers()) {
            if (cust.getLogin().equals(searchName)) {
                newPanel.setPreferredSize(new Dimension(frameSize - 140, 120));

                JLabel a = new JLabel("JESTEŚ ZALOGOWANY JAKO ");
                JLabel b = new JLabel(String.format("IMIE: %s",cust.getName()));
                JLabel c = new JLabel(String.format("NAZWISKO: %s",cust.getSurname()));
                JLabel d= new JLabel("Masz uprawnienia: Customer");

                a.setFont(new Font("Arial",Font.BOLD,15));
                b.setFont(new Font("Arial",Font.BOLD,15));
                c.setFont(new Font("Arial",Font.BOLD,15));
                d.setFont(new Font("Arial",Font.BOLD,15));

                //newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
                newPanel.setBackground(Color.white);

                newPanel.add(a);
                newPanel.add(b);
                newPanel.add(c);
                newPanel.add(d);
                break;
            }
        }
        return newPanel;
    }
    public static void addToCart(Product product) throws IOException {
        String name = product.getName();
        int quant = product.getQuantity();
        double productPrice = product.getPrice();
        int ex = 0;

        if(quant > 0)
        {
            for(Cart item : Customer.getCart())
            {
                if(item.getName().equals(name))
                {
                    int value = item.getQuantity();
                    item.setQuantity(value + 1);
                    product.setQuantity(quant - 1);
                    ex = 1;
                }
            }
            if(ex == 0){
                Customer.cart.add(new Cart(name,1,productPrice));
                product.setQuantity(quant - 1);
            }

            JLabel productInCart;
            leftPanel.removeAll();
            for(Cart item : Customer.getCart())
            {
                productInCart = new JLabel(String.format("%dx %s",item.getQuantity(),item.getName()));
                productInCart.setPreferredSize(new Dimension(100,20));
                leftPanel.add(productInCart);
            }

            leftPanel.revalidate();
            leftPanel.repaint();
        }
    }
}