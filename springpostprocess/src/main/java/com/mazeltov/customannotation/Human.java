package com.mazeltov.customannotation;

import javax.annotation.PostConstruct;


public class Human implements Speakable {

    @InjectRandomInt(min = 2, max = 5)
    private int repeat;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @PostConstruct
    public void init() {
        System.out.println("init method");
    }

    public Human() {
        System.out.println("Constructor");
    }

    @Override
    public void saySomething() {
        for (int i = 0; i < repeat; i++) {
            System.out.println("Human say : " + message);
        }
    }
}
