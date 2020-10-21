package com.mazeltov.singeltoninprototape;

import org.springframework.context.annotation.*;

import java.util.Random;

@Configuration
@ComponentScan(basePackages = "com.mazeltov.singeltoninprototape")
public class Config {

    @Bean
    //@Scope(value = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Scope(value = "prototype")
    public Number number() {
        return new Number(new Random().nextInt(100));
    }

    @Bean
    public Printer printer()
    {
        return new Printer() {
            @Override
            public Number getNumber() {
                return number();
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        while (true) {
            Thread.sleep(200);
            applicationContext.getBean(Printer.class).print();
        }
    }
}
