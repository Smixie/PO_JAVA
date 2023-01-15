import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EmplyeeActionListner implements ActionListener {
    public EmplyeeActionListner(Employee employee) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Employee.addProduct) {
            Employee.addProduct();
        } else if (e.getSource() == Employee.deleteProduct) {
            System.out.print("DP");
        } else if (e.getSource() == Employee.logoutE) {
            Employee.close();
            new Login();
        }
        if(e.getSource() == Employee.addProductButton) {
            if(Employee.pImage.getText().equals("") || Employee.pPrice.getText().equals("")
            || Employee.pQuantity.getText().equals("") || Employee.pName.getText().equals(""))
            {
                JOptionPane.showMessageDialog(Employee.addFrame,"Some fields are empty");
            }
            else if(Integer.parseInt(Employee.pQuantity.getText()) >= 0
                    || Double.parseDouble(Employee.pPrice.getText()) >= 0)
            {

                String name = Employee.pName.getText();
                ImageIcon img = new ImageIcon("Images/"+Employee.pImage.getText());
                int quantity = Integer.parseInt(Employee.pQuantity.getText());
                double price = Double.parseDouble(Employee.pPrice.getText());
                int exist = 0;

                for(Product p : Product.getProducts())
                {
                    if(p.getName().equals(name))
                    {
                        p.setQuantity(p.getQuantity() + quantity);
                        exist = 1;
                        JOptionPane.showMessageDialog(Employee.addFrame,"Product exists, quantity has been changed!");
                    }
                }
                if(exist == 0)
                {
                    JLabel label;
                    Product.products.add(new Product(quantity, name, price, img));
                    label = new JLabel(String.format("%s %.2fzł", name, price), img, JLabel.CENTER);
                    label.setVerticalTextPosition(JLabel.BOTTOM);
                    label.setHorizontalTextPosition(JLabel.CENTER);
                    label.setBorder(new EmptyBorder(10, 10, 10, 10));

                    if(Employee.panelTop != null)
                    {
                        Employee.panelTop.add(label);
                        Employee.frame.validate();
                        Employee.frame.repaint();
                    }
                    if(Manager.panelTop != null)
                    {
                        Manager.panelTop.add(label);
                        Manager.frame.validate();
                        Manager.frame.repaint();
                    }
                }

                try {
                    FileWriter fw = new FileWriter("Data/products.txt", false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    for(Product p : Product.getProducts())
                    {
                        bw.write(String.format("%s,",p.getName()) +
                                String.format("%.2f",p.getPrice()).replace(',', '.') +
                                String.format(",%d,%s",p.getQuantity(),p.getImage()));
                        bw.newLine();
                    }

                    bw.close();
                    fw.close();
                } catch (IOException ep) {
                    ep.printStackTrace();
                }
            }else {
                JOptionPane.showMessageDialog(Employee.addFrame,"Price or quantity are equal or less than zero");
            }
        }
        if(e.getSource() == Employee.pClear)
        {
            Employee.pImage.setText("");
            Employee.pName.setText("");
            Employee.pPrice.setText("");
            Employee.pQuantity.setText("");
        }
    }
}
