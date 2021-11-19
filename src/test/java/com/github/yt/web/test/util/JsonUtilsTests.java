package com.github.yt.web.test.util;

import com.github.yt.web.test.example.entity.CircularReferenceA;
import com.github.yt.web.test.example.entity.CircularReferenceB;
import com.github.yt.web.util.JsonUtils;
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
