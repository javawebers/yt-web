package com.github.yt.web.util;

import com.github.yt.web.example.entity.CircularReferenceA;
import com.github.yt.web.example.entity.CircularReferenceB;
import org.junit.Test;

public class JsonUtilsTests {

    @Test
    public void test() {
        CircularReferenceB circularReferenceB = new CircularReferenceB();
        CircularReferenceA circularReferenceA = new CircularReferenceA();
        circularReferenceB.setCircularReferenceA(circularReferenceA);
        circularReferenceA.setCircularReferenceB(circularReferenceB);
        circularReferenceB.setB("ddd");
        circularReferenceA.setA("sss");
        JsonUtils.toJsonString(circularReferenceB);
    }
}
