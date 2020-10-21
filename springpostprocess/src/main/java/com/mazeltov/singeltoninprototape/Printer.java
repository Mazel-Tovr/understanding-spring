package com.mazeltov.singeltoninprototape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public abstract class Printer {

//    @Autowired
//    private Number number;

    public void print() {
        System.out.println(getNumber().toString());
    }

    public abstract Number getNumber();

}
