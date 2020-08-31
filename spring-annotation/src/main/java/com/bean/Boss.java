package com.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Boss {

    private Car car;

    //@Autowired
    public Boss (@Autowired Car car) {
        this.car = car;
        System.out.println("boss...有参构造器");
    }

    public Car getCar() {
        return car;
    }

    //@Autowired
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
          "car=" + car +
          '}';
    }
}
