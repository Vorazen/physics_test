package ru.vorazen.main;

import javax.swing.JFrame;

public class Main {
    public static JFrame window;

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        window = new JFrame("2d game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.startGameThread();
    }
}