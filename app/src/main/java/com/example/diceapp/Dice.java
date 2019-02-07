package com.example.diceapp;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.sql.Timestamp;

public class Dice implements Comparable<Dice>, Serializable {

    private String name;
    private int diceType;
    private Timestamp timestamp;
    private int result;

    public Dice(String name, int diceType) {
        this.name = name;
        this.diceType = diceType;
    }

    public Dice(String name, int result, Timestamp timestamp) {
        this.name = name;
        this.result = result;
        this.timestamp = timestamp;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public int compareTo(@NonNull Dice other) {
        return timestamp.compareTo(other.timestamp);
    }
}
