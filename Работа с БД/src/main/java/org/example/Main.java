package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;

public class Main extends JFrame {
    Main(){
        this.setBounds(0, 0, 500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Game Window");
        this.setLayout(null);

        jta1 = new JTextArea();
        jta1.setBounds(50, 10, 300, 25);
        this.add(jta1);

        fio = new JLabel("ФИО");
        fio.setBounds(0,10,80,25);
        this.add(fio);

        jta2 = new JTextArea();
        jta2.setBounds(50, 45, 100, 25);
        this.add(jta2);

        id = new JLabel("ID");
        id.setBounds(0,45,80,25);
        this.add(id);

        js1 = new JSlider(0,30,0);
        js1.setBounds(50, 85, 300, 25);
        this.add(js1);
        js1.setMajorTickSpacing(10);
        js1.setMinorTickSpacing(1);
        js1.setPaintTicks(true);

        age = new JLabel("AGE");
        age.setBounds(0,85,80,25);
        this.add(age);

        maxAGE = new JLabel("30");
        maxAGE.setBounds(350,85,80,25);
        this.add(maxAGE);

        minAGE = new JLabel("0");
        minAGE.setBounds(30,85,80,25);
        this.add(minAGE);

        rb1 = new JRadioButton("Левша");
        rb1.setBounds(150, 115, 100, 25);
        this.add(rb1);


        rb2 = new JRadioButton("Правша");
        rb2.setBounds(50, 115, 100, 25);
        this.add(rb2);
        this.setVisible(true);

        getDataBase = new JButton("Получить данные");
        getDataBase.setBounds(0,200,200,25);
        this.add(getDataBase);

        getDataBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    User();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    public void User() throws SQLException {
        Connection con1 = null;
        Statement st1 = null;
        String url1 = "jdbc:postgresql://127.0.0.1:5432/postgres";
        con1 = DriverManager.getConnection(url1,"введите пользователя", "введите пароль");
        con1.setAutoCommit(true);

        st1 = con1.createStatement();
        st1.executeUpdate("insert into public.info (fio, id, age, isleft, isright) values  ('"+jta1.getText()+"', '"+jta2.getText()+"', "+js1.getValue()+", "+rb1.isSelected()+", "+rb2.isSelected()+")");

        ResultSet rs1 = st1.executeQuery("SELECT fio, id, age, isleft, isright FROM public.info");
        while (rs1.next()){
            String fio = rs1.getString("fio");
            String id = rs1.getString("id");
            int age = rs1.getInt("age");
            boolean isleft = rs1.getBoolean("isleft");
            boolean isright = rs1.getBoolean("isright");

            System.out.println("fio: " + fio + ", id: " + id + ", age: " + age + ", isleft: " + isleft + ", isright: " + isright);
        }

        st1.close();
        con1.close();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            gameWindow = new Main();
        });
    }

    static Main gameWindow;
    JTextArea jta1, jta2;
    JSlider js1;
    JRadioButton rb1, rb2;
    JLabel fio, id, age, maxAGE, minAGE;
    JButton getDataBase;
}