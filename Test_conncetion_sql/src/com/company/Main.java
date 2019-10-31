package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/*class get_Connection {

    private String name = "root";
    private String password = "2ghXPhY8";
    private String URL = "jdbc:mysql://localhost:3306/sql_base?serverTimezone=UTC";
    private Connection conncetion;

    public get_Connection() {
        try {
            conncetion = DriverManager.getConnection(URL, name, password);
            System.out.println("Соединение установлено");
        } catch (SQLException ex){
            System.out.println("Проблемы с соединением! " + ex.getMessage());
        }
    }
}*/
class Frame_of_request extends JFrame {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;

    private JPanel panel;
    private JMenuBar menubar;
    private JMenu menu;
    private JMenuItem menu_item_exit;
    private JTextField text_field;
    private JTextArea text_area;
    private JButton button;

    private String name = "root";
    private String password = "2ghXPhY8";
    private String URL = "jdbc:mysql://localhost:3306/sql_base?serverTimezone=UTC";
    private Connection connection;


    public Frame_of_request() {

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);

        panel = new JPanel();
        menubar = new JMenuBar();
        menu = new JMenu("File");
        menu_item_exit = new JMenuItem("Exit");
        text_area = new JTextArea(18, 57);
        text_area.setLineWrap(true);
        text_area.setWrapStyleWord(true);
        text_field = new JTextField(20);
        button = new JButton("Отправить запрос");
        menubar.add(menu);
        menu.add(menu_item_exit);
        setJMenuBar(menubar);
        panel.add(text_field);
        panel.add(button);
        panel.add(text_area);
        add(panel);

        menu_item_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                System.exit(0);
            }
        });


        connection = get_connection(URL,name,password);
        if(connection == null) System.exit(1);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try{
                    text_area.setText("");
                    Statement statement = connection.createStatement();
                    //String str = new String(text_field.getText());
                    //ResultSet result = statement.executeQuery("select * from sql_base.new_table where name = 'str' ");
                    String str = "select * from sql_base.new_table where name = '" + text_field.getText()+"'";
                    ResultSet result = statement.executeQuery(str);
                    int count = result.getMetaData().getColumnCount();

                    while(result.next()) {
                        for (int i = 1; i <= count; i++) {
                            text_area.append(result.getString(i) + " | ");
                            System.out.print(result.getString(i) + " ");
                        }
                        text_area.append("\n");
                        System.out.println("\n");
                    }
                }catch(SQLException ex){
                    System.out.println("Ошибка создания запроса!");
                }
            }
        });


    }

    private Connection get_connection(String URL, String name, String password) {
        try {
            if (URL.isEmpty() || name.isEmpty() || password.isEmpty()) throw new SQLException("Ошибка соединения");
            return DriverManager.getConnection(URL, name, password);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

}

public class Main {


    public static void main(String[] args) {


        EventQueue.invokeLater(new Runnable(){
            public void run(){

                Frame_of_request frame = new Frame_of_request();
                frame.setTitle("Окно запросов");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });

    }

}
