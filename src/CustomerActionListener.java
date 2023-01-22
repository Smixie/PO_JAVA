import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.awt.Color.white;

public class CustomerActionListener implements ActionListener {
    public CustomerActionListener(Customer customer){}
    private JFrame pay;
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Customer.logoutE)
        {
            Customer.close();
            new Login();
        }
        if(e.getSource() == Customer.checkoutButton)
        {
            pay = new JFrame();
            pay.setResizable(false);
            pay.setSize(300,400);
            //pay.setLayout(null);

            JPanel prod = new JPanel();
            prod.setLayout(new BoxLayout(prod, BoxLayout.Y_AXIS));
            prod.setBackground(white);

            if(Customer.getCart().isEmpty())
            {
                JOptionPane.showMessageDialog(Customer.frame,"Cart is empty, add products!");
            }
            else{
                double allProductCost = 0;
                for(Cart pd : Customer.getCart())
                {
                    int value = pd.getQuantity();
                    double price = pd.getPrice();
                    String name = pd.getName();
                    double totalCost = price*value;
                    allProductCost += totalCost;

                    JLabel wartosc = new JLabel(String.format("%s       %dx %.2f",name,value,totalCost));
                    wartosc.setPreferredSize(new Dimension(100,20));
                    prod.add(wartosc);
                }
                JLabel wartosc2 = new JLabel(String.format("TOTAL COST       %.2f",allProductCost));
                prod.add(wartosc2);

                JButton ok = new JButton("OK");
                ok.setBounds(350,140,100,40);
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    try {
                        FileWriter fw = new FileWriter("Data/products.txt", false);
                        BufferedWriter bw = new BufferedWriter(fw);

                        for(Product op : Product.getProducts())
                        {
                            bw.write(String.format("%s,",op.getName()) +
                                    String.format("%.2f",op.getPrice()).replace(",",".") +
                                    String.format(",%d,%s",op.getQuantity(),op.getImage()));
                            bw.newLine();
                        }
                        bw.close();
                        fw.close();
                    } catch (IOException ep) {
                        ep.printStackTrace();
                    }

                        JOptionPane.showMessageDialog(pay,"You bought products! Your cart is clear!");
                        Customer.getCart().clear();

                        Customer.panelTop.validate();
                        Customer.panelTop.repaint();

                        Customer.frame.repaint();

                        Customer.leftPanel.removeAll();
                        Customer.leftPanel.validate();
                        Customer.leftPanel.repaint();
                        pay.dispose();
                    }
                });

                prod.add(ok);
                pay.add(prod);
                pay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                pay.setVisible(true);
                pay.setLocationRelativeTo(null);
            }

        }
    }
}
