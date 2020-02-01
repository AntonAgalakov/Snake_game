package ru.Ag;

public class myRandom {
    private int min = 1;
    private int max = 19;

    public int get() {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
