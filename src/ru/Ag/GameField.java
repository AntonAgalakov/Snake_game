package ru.Ag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE_X = 1902;
    private final int SIZE_Y = 980;
    private final int DOT_SIZE = 30;
    private final int ALL_DOTS = 2304;
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
    private boolean run = true;
    private int score = 0;

    public GameField() {
        setBackground(Color.WHITE);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 120 - i * DOT_SIZE;
            y[i] = 120;
        }
        timer = new Timer(400,this);
        timer.start();
        createApple();
    }

    public void restart() {
        inGame = true;
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 120 - i * DOT_SIZE;
            y[i] = 120;
        }
        timer.start();
        createApple();
        right = true;
        left = false;
        up = false;
        down = false;
    }

    public void createApple() {
        appleX = new myRandom().getX() * DOT_SIZE;
        appleY = new myRandom().getY() * DOT_SIZE;
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
            int i;
            g.drawImage(apple, appleX, appleY, this);
            for (i = 0; i < dots; i++)
                g.drawImage(dot, x[i], y[i], this);
            for (i = 0; i < SIZE_X; i++) {
                g.drawImage(board, i, 0, this);
                g.drawImage(board, i, SIZE_Y, this);
            }
            for (i = 0; i < SIZE_Y; i++) {
                g.drawImage(board, 0, i, this);
                g.drawImage(board, SIZE_X, i, this);
            }
        } else {
            g.drawImage(gameOver, 500, 0, this);
            Font myFont = new Font ("Courier New", 1, 28);
            g.setFont (myFont);
            String string1 = "You collected: " + score + " ap";
            g.setColor(Color.RED);
            g.drawString(string1, 780, 70);
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

        if(x[0] >= SIZE_X)
            inGame = false;
        if(x[0] < 30)
            inGame = false;
        if(y[0] >= SIZE_Y)
            inGame = false;
        if(y[0] < 30)
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
            } else if (key == KeyEvent.VK_N) {
                restart();
            } else if (key == KeyEvent.VK_P && run) {
                timer.stop();
                run = false;
            } else if (key == KeyEvent.VK_P && !run) {
                timer.start();
                run = true;
            }

        }
    }
}