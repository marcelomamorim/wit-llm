/*
 * @test
 * @summary Verify that Field.getFloat on a static Object field throws IllegalArgumentException and that Field.get still returns the value
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    static final Object OBJ_FINAL = "final-value";
    static Object objStatic = "static-value";

    public static void main(String[] args) throws Exception {
        checkGetFloatThrows("OBJ_FINAL");
        checkGetFloatThrows("objStatic");
        checkGetReturnsValue("OBJ_FINAL", "final-value");
        checkGetReturnsValue("objStatic", "static-value");
    }

    static void checkGetFloatThrows(String name) throws Exception {
        Field f = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField(name);
        boolean thrown = false;
        try {
            f.getFloat(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getFloat on " + name + " did not throw IllegalArgumentException");
        }
    }

    static void checkGetReturnsValue(String name, String expected) throws Exception {
        Field f = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField(name);
        Object actual = f.get(null);
        if (!expected.equals(actual)) {
            throw new AssertionError("get on " + name + " returned " + actual + ", expected " + expected);
        }
    }
}
