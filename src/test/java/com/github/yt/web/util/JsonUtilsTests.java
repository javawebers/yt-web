package com.github.yt.web.util;

import com.github.yt.web.entity.CircularReferenceA;
import com.github.yt.web.entity.CircularReferenceB;
import org.testng.annotations.Test;

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
