/*
 * @test
 * @summary Test for MethodHandles.findClass method.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.Class;
import java.lang.ClassNotFoundException;
import java.lang.IllegalAccessException;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testFindClassWithValidClassName();
        testFindClassWithInvalidClassName();
    }

    private static void testFindClassWithValidClassName() {
        String className = "java.lang.String";
        try {
            Class<?> cls = MethodHandles.findClass(className);
            if (cls != null && cls.getName().equals(className)) {
                System.out.println("PASS: findClass with valid class name.");
            } else {
                System.out.println("FAIL: findClass with valid class name.");
            }
        } catch (ClassNotFoundException | IllegalAccessException e) {
            System.out.println("FAIL: findClass with valid class name threw exception: " + e);
        }
    }

    private static void testFindClassWithInvalidClassName() {
        String invalidClassName = "non.existent.ClassName";
        try {
            MethodHandles.findClass(invalidClassName);
            System.out.println("FAIL: findClass with invalid class name did not throw.");
        } catch (ClassNotFoundException e) {
            System.out.println("PASS: findClass with invalid class name threw ClassNotFoundException.");
        } catch (IllegalAccessException e) {
            System.out.println("FAIL: findClass with invalid class name threw IllegalAccessException: " + e);
        }
    }
}
