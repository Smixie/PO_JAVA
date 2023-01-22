import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ManagerActionListener implements ActionListener {
    public ManagerActionListener(Manager manager) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Manager.addEmployee)
        {
            Manager.addEmployee();
        }
        if(e.getSource() == Manager.addProduct)
        {
            Manager.addProduct();
        }
        if(e.getSource() == Manager.deleteEmployee)
        {
            System.out.print("Usuwanie pracownika");
        }
        if(e.getSource() == Manager.deleteProduct)
        {
            Manager.deleteProducts();
        }
        if(e.getSource() == Manager.logout)
        {
            Manager.close();
            new Login();
        }
        if(e.getSource() == Manager.addEmp)
        {
            if(Manager.eSalary.getText().equals("") || Manager.ePassword.getText().equals("")
                    || Manager.eSurname.getText().equals("")
                    || Manager.pName.getText().equals(""))
            {
                JOptionPane.showMessageDialog(Employee.addFrame,"Some fields are empty");
            }
            else
            {
                String eName = Manager.pName.getText();
                String surname = Manager.eSurname.getText();
                double eSalary = Double.parseDouble(Manager.eSalary.getText());
                String ePass = Manager.ePassword.getText();

                int epEX = 0;
                for(Employee emp: Worker.getWorker())
                {
                    if(emp.getName().equals(eName) && emp.getSurname().equals(surname))
                    {
                        JOptionPane.showMessageDialog(Employee.addFrame,"Emloyee exists!");
                        epEX = 1;
                    }
                }
                if(epEX == 0)
                {
                    int ID = Worker.getWorker().get(Worker.getWorker().size() - 1).getId() + 1;
                    Worker.getWorker().add(new Worker(ID+1,eName,surname,eSalary,
                            String.format("employee%d",ID)));

                    try {
                        FileWriter fw = new FileWriter("Data/employes.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(String.format("%d,%s,%s,",ID,eName,surname) +
                                String.format("%.2f",eSalary).replace(",",".") + String.format(",employee%d",ID));
                        bw.newLine();

                        bw.close();
                        fw.close();
                    } catch (IOException ep) {
                        ep.printStackTrace();
                    }

                    try {
                        FileWriter fw = new FileWriter("Data/loginCredentials.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(String.format("%s,%s,%d",String.format("employee%d",ID),ePass,2));
                        bw.newLine();

                        bw.close();
                        fw.close();
                    } catch (IOException ep) {
                        ep.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(Manager.addFrame,"New employee added");
                }
            }
        }
        if(e.getSource() == Manager.pClear)
        {
            Manager.eSalary.setText("");
            Manager.ePassword.setText("");
            Manager.eLogin.setText("");
            Manager.eSurname.setText("");
            Manager.pName.setText("");
        }
    }
}
