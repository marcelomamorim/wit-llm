/*
 * @test
 * @summary AccessibleObject.getAnnotation base implementation must throw AssertionError
 * @run main AccessibleObjectWitupTest
 */

import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    static class Holder extends AccessibleObject {
    }

    public static void main(String[] args) {
        AccessibleObject ao = new Holder();

        try {
            ao.getAnnotation(Override.class);
            throw new RuntimeException("getAnnotation did not throw AssertionError");
        } catch (AssertionError expected) {
            // expected: base implementation asserts that subclasses override it
        }

        try {
            ao.isAnnotationPresent(Override.class);
            throw new RuntimeException("isAnnotationPresent did not propagate AssertionError");
        } catch (AssertionError expected) {
            // expected: default isAnnotationPresent delegates to getAnnotation
        }
    }
}
