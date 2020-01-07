package com.github.yt.web.example.entity;

public class CircularReferenceA {
    private String a;
    private CircularReferenceB circularReferenceB;

    public String getA() {
        return a;
    }

    public CircularReferenceA setA(String a) {
        this.a = a;
        return this;
    }

    public CircularReferenceB getCircularReferenceB() {
        return circularReferenceB;
    }

    public CircularReferenceA setCircularReferenceB(CircularReferenceB circularReferenceB) {
        this.circularReferenceB = circularReferenceB;
        return this;
    }
}
