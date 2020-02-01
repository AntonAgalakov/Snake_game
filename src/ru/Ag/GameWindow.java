package ru.Ag;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow() {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(336, 420);
        setLocation(400, 400);
        GameField gameField = new GameField();
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();

    }


}
