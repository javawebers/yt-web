package com.github.yt.web.test.example.entity;

public class CircularReferenceB {
    private String b;
    private CircularReferenceA circularReferenceA;

    public String getB() {
        return b;
    }

    public CircularReferenceB setB(String b) {
        this.b = b;
        return this;
    }

    public CircularReferenceA getCircularReferenceA() {
        return circularReferenceA;
    }

    public CircularReferenceB setCircularReferenceA(CircularReferenceA circularReferenceA) {
        this.circularReferenceA = circularReferenceA;
        return this;
    }
}
