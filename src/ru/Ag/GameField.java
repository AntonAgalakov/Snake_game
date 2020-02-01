package ru.Ag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private Image board;
    private Image gameOver;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private int score = 0;


    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250,this);
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = new myRandom().get() * DOT_SIZE;
        appleY = new myRandom().get() * DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon imgApple = new ImageIcon("./img/apple.png");
        apple = imgApple.getImage();
        ImageIcon imgDot = new ImageIcon("./img/dot.png");
        dot = imgDot.getImage();
        ImageIcon imgBoard = new ImageIcon("./img/border.png");
        board = imgBoard.getImage();
        ImageIcon imgGameOver = new ImageIcon("./img/gameover.png");
        gameOver = imgGameOver.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++)
                g.drawImage(dot, x[i], y[i], this);
            for (int i = 0; i < SIZE; i++) {
                g.drawImage(board, 0, i, this);
                g.drawImage(board, i, 0, this);
                g.drawImage(board, SIZE, i, this);
                g.drawImage(board, i, SIZE, this);
            }
        } else {
            setBackground(Color.BLACK);
            g.drawImage(gameOver, 5, 0, this);
            String string2 = "You collected: " + score + " ap";
            g.setColor(Color.RED);
            g.drawString(string2, 100, 50);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left)
            x[0] -= DOT_SIZE;
        if (right)
            x[0] += DOT_SIZE;
        if (up)
            y[0] -= DOT_SIZE;
        if (down)
            y[0] += DOT_SIZE;
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            score++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--)
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                break;
            }

        if(x[0] >= 320)
            inGame = false;
        if(x[0] < 16)
            inGame = false;
        if(y[0] >= 320)
            inGame = false;
        if(y[0] < 16)
            inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                right = false;
                up = false;
                down = false;
            } else if (key == KeyEvent.VK_RIGHT && !left){
                right = true;
                left = false;
                up = false;
                down = false;
            } else if (key == KeyEvent.VK_UP && !down){
                up = true;
                right = false;
                left = false;
                down = false;
            } else if (key == KeyEvent.VK_DOWN && !up){
                down = true;
                up = false;
                right = false;
                left = false;
            }
        }
    }
}