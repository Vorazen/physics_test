package ru.vorazen.my_physics;

import javax.swing.JFrame;

public class MyMain {
    public static void main(String[] args) {
        JFrame window = new JFrame("2d game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        MyPanel gamePanel = new MyPanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.startGameThread();

    }
}
