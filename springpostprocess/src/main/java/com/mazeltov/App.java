package com.mazeltov;

import com.mazeltov.customannotation.Human;
import com.mazeltov.customannotation.Speakable;
import com.mazeltov.custompoxy.SuperDuperBusinessLogic;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */

public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
//        applicationContext.getBean(Speakable.class).saySomething();
//        int i =0;
//        while (i<=5)
//        {
//            i++;
//            applicationContext.getBean(SuperDuperBusinessLogic.class).doBusinessLogic();
//        }
        applicationContext.getBean(SuperDuperBusinessLogic.class).doBusinessLogic();

    }
}
