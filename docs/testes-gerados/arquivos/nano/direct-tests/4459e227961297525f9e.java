/*
 * @test
 * @summary Test for MethodHandles.findClass method.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testFindClassWithExistingClass();
        testFindClassWithNonExistingClass();
        testFindClassWithNull();
    }

    private static void testFindClassWithExistingClass() {
        try {
            Class<?> cls = MethodHandles.findClass("java.lang.String");
            if (cls != null && cls.equals(String.class)) {
                System.out.println("PASS: findClass with existing class.");
            } else {
                System.out.println("FAIL: findClass with existing class.");
            }
        } catch (ClassNotFoundException | IllegalAccessException e) {
            System.out.println("FAIL: findClass with existing class. Exception: " + e);
        }
    }

    private static void testFindClassWithNonExistingClass() {
        try {
            MethodHandles.findClass("com.nonexistent.ClassName");
            System.out.println("FAIL: findClass with non-existing class did not throw.");
        } catch (ClassNotFoundException e) {
            System.out.println("PASS: findClass with non-existing class threw ClassNotFoundException.");
        } catch (IllegalAccessException e) {
            System.out.println("FAIL: findClass with non-existing class threw IllegalAccessException.");
        }
    }

    private static void testFindClassWithNull() {
        try {
            MethodHandles.findClass(null);
            System.out.println("FAIL: findClass with null did not throw.");
        } catch (NullPointerException e) {
            System.out.println("PASS: findClass with null threw NullPointerException.");
        } catch (ClassNotFoundException | IllegalAccessException e) {
            System.out.println("FAIL: findClass with null threw unexpected exception: " + e);
        }
    }
}
