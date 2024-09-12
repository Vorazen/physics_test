package ru.vorazen.physics;

import javax.swing.JFrame;

public class Test {

    public static void main(String[] args) {
        JFrame window = new JFrame("2d game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        WindowPanel gamePanel = new WindowPanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.startGameThread();

    }
}
