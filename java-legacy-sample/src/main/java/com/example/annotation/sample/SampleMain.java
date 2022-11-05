package com.example.annotation.sample;

import com.example.annotation.Tester;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class SampleMain {

    public static void main(String[] args) {

        int passed = 0, failed = 0, count = 0, ignore = 0;

        Class<SampleTester> obj = SampleTester.class;

        for (Method method : obj.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Tester.class)) {
                Annotation annotation = method.getAnnotation(Tester.class);
                Tester test = (Tester) annotation;
                if (test.enabled()) {
                    try {
                        method.invoke(obj.newInstance());
                        System.out.printf("%s - Test '%s' - passed %n", ++count, method.getName());
                        passed++;
                    } catch (Throwable ex) {
                        System.out.printf("%s - Test '%s' - failed: %s %n", ++count, method.getName(), ex.getCause());
                        failed++;
                    }

                } else {
                    System.out.printf("%s - Test '%s' - ignored%n", ++count, method.getName());
                    ignore++;
                }
            }

        }
        System.out.printf("%nResult : Total : %d, Passed: %d, Failed %d, Ignore %d%n", count, passed, failed, ignore);
    }
}
