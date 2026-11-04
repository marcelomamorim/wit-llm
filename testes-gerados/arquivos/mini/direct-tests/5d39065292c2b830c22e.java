/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getFloat(Object) method
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        int intField = 123;
    }

    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Field f = TestClass.class.getDeclaredField("intField");
        UnsafeIntegerFieldAccessorImpl acc = new UnsafeIntegerFieldAccessorImpl(f);

        // getFloat should return the int value as float
        float val = acc.getFloat(obj);
        if (val != 123.0f) {
            throw new RuntimeException("Expected 123.0f but got " + val);
        }

        // getFloat with null should throw IllegalArgumentException
        boolean threw = false;
        try {
            acc.getFloat(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for null argument");
        }
    }
}
