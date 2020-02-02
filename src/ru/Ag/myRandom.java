package ru.Ag;

public class myRandom {
    private int minX = 1;
    private int maxX = 63;
    private int minY = 1;
    private int maxY = 30;

    public int getX() {
        maxX -= minX;
        return (int) (Math.random() * ++maxX) + minX;
    }
    public int getY() {
        maxY -= minY;
        return (int) (Math.random() * ++maxY) + minY;
    }
}
