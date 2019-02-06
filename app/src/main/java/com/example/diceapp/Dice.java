package com.example.diceapp;

public class Dice {

    private String name;
    private int diceType;

    public Dice(String name, int diceType) {
        this.name = name;
        this.diceType = diceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiceType() {
        return diceType;
    }

    public void setDiceType(int diceType) {
        this.diceType = diceType;
    }
}
