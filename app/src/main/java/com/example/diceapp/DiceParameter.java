package com.example.diceapp;

import java.io.Serializable;
import java.util.List;

public class DiceParameter implements Serializable {

    private List<Dice> dice;

    public DiceParameter(List<Dice> dice) {
        this.dice = dice;
    }

    public List<Dice> getDice() {
        return dice;
    }

    public void setDice(List<Dice> dice) {
        this.dice = dice;
    }
}
