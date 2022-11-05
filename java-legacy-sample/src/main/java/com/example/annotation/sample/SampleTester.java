package com.example.annotation.sample;

import com.example.annotation.Tester;

public class SampleTester {

    @Tester
    void testA() {
        System.out.println("test a");
    }

    @Tester(enabled = false)
    void testB() {
        System.out.println("test b");
    }

    @Tester(enabled = true)
    void testC() {
        System.out.println("test c");
    }

    @Tester(enabled = true)
    void testD() {
        throw new RuntimeException("Throw Exception");
    }
}
