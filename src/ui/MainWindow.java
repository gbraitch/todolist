package ui;

import javax.swing.*;

public class MainWindow {
    public MainWindow(){
        JFrame frame = new JFrame("TodoList");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton addtodo = new JButton("+");
        JButton deltodo = new JButton("-");
        JButton edittodo = new JButton("edit");
        frame.getContentPane().add(addtodo);
        frame.getContentPane().add(deltodo);
        frame.getContentPane().add(edittodo);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
