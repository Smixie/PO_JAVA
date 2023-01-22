import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

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
            Manager.deleteEmployee();
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
        if(e.getSource() == Manager.okDelete)
        {
            try {
                int idNumber = Integer.parseInt(Manager.delete.getText());
                if (Manager.delete.getText().equals("")) {
                    JOptionPane.showMessageDialog(Manager.deleteEmployeeFrame, "Please provide ID(number)");
                }
                boolean isEmp = false;
                String hisLogin = null;
                for(Employee work : Worker.getWorker())
                {
                    int id = work.getId();
                    if(id == idNumber){
                        Worker.getWorker().remove(work);
                        hisLogin = work.getUsername();
                        isEmp = true;
                        break;
                    }
                }
                if(isEmp)
                {
                    JOptionPane.showMessageDialog(Manager.deleteEmployeeFrame, "Employee successfully fired");

                    try {
                        FileWriter fw = new FileWriter("Data/employes.txt", false);
                        BufferedWriter bw = new BufferedWriter(fw);

                        for(Employee work : Worker.getWorker())
                        {
                            bw.write(String.format("%d,%s,%s,",work.getId(),work.getName(),work.getSurname()) +
                                    String.format("%.2f",work.getSalary()).replace(",",".") +
                                    String.format(",%s",work.getUsername()));
                            bw.newLine();
                        }
                        bw.close();
                        fw.close();
                    } catch (IOException ep) {
                        ep.printStackTrace();
                    }

                    Scanner in;
                    try {
                        StringBuilder data = new StringBuilder();
                        in = new Scanner(new File("Data/loginCredentials.txt"));
                        while (in.hasNextLine()) {
                            String line = in.nextLine();
                            String[] parts = line.split(",");
                            String lg = parts[0];
                            if (!hisLogin.equals(lg)) {
                                data.append(line).append("\n");
                            }
                        }
                        FileWriter fw = new FileWriter("Data/loginCredentials.txt", false);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(data.toString());
                        bw.close();
                        fw.close();

                    } catch (IOException esd) {
                        throw new RuntimeException(esd);
                    }
                    in.close();
                }
                else {
                    JOptionPane.showMessageDialog(Manager.deleteEmployeeFrame, "No such employee");
                }
            }catch (NumberFormatException ep)
            {
                JOptionPane.showMessageDialog(Manager.deleteEmployeeFrame, "Please provide ID(number)");
            }
        }
    }
}
