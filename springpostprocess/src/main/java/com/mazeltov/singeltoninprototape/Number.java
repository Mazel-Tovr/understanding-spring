package com.mazeltov.singeltoninprototape;

import org.springframework.stereotype.Component;


public class Number {

    private int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Number{" +
                "number=" + number +
                '}';
    }
}
