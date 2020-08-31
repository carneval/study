package com.bean;

import org.springframework.stereotype.Component;

/**
 * 没有用@Component注入容器
 */

public class Color {

    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Color{" +
          "car=" + car +
          '}';
    }
}
