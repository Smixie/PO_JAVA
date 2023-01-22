import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Login extends  JFrame implements ActionListener {
    JLabel labelUsername;
    JLabel labelPassword;
    JTextField username;
    JPasswordField password;
    JButton buttonLogin;
    JButton buttonCancel;
    JLabel title;
    public Login()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setTitle("Login");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(255,240,228));

        title = new JLabel("LOGIN TO SHOP");
        title.setBounds(300,150,300,40);
        title.setFont(new Font("Arial",Font.BOLD,20));

        labelUsername = new JLabel("Username");
        labelUsername.setBounds(200,200,100,40);

        username = new JTextField();
        username.setBounds(300,200,300,40);

        labelPassword = new JLabel("Password");
        labelPassword.setBounds(200,250,100,40);

        password = new JPasswordField();
        password.setBounds(300,250,300,40);

        buttonLogin = new JButton("Sign in");
        buttonLogin.setBounds(300,320,100,40);
        buttonLogin.addActionListener(this);

        buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(460,320,100,40);
        buttonCancel.addActionListener(this);


        this.add(title);
        this.add(labelUsername);
        this.add(username);

        this.add(labelPassword);
        this.add(password);

        this.add(buttonLogin);
        this.add(buttonCancel);


        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if(ev.getSource() == buttonCancel)
        {
            username.setText("");
            password.setText("");
        }

        if(ev.getSource() == buttonLogin)
        {
            int status = 0;
            Scanner in;
            try {
                in = new Scanner(new File("Data/loginCredentials.txt"));
                String user = username.getText();
                String pwd = new String(password.getPassword());
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    String[] parts = line.split(",");
                    String lg = parts[0];
                    int stc = Integer.parseInt(parts[2]);
                    String pw = parts[1];
                    if (user.equalsIgnoreCase(lg) && pwd.equalsIgnoreCase(pw)) {
                        JOptionPane.showMessageDialog(this,"You are logged in successfully!");
                        status = stc;
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            in.close();
            if(status == 0)
            {
                JOptionPane.showMessageDialog(this,"Bad username or password!");
            }
            if(status == 1)
            {
                try {
                    this.dispose();
                    Manager.loadManager(username.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(status == 2)
            {
                try {
                    this.dispose();
                    Worker.loadEmployeeShop(username.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(status == 3)
            {
                try {
                    this.dispose();
                    Customer.loadCustomeShop(username.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
