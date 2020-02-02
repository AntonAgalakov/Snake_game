package ru.Ag;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow() {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocation(400, 400);
        GameField gameField = new GameField();
        add(new GameField());
        setVisible(true);
    }
}
