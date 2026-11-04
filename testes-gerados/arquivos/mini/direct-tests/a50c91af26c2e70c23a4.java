/*
 * @test
 * @summary Test getFloat(Object) method of UnsafeQualifiedIntegerFieldAccessorImpl
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class TestObj {
        volatile int intField = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field f = TestObj.class.getDeclaredField("intField");
        UnsafeQualifiedIntegerFieldAccessorImpl acc = new UnsafeQualifiedIntegerFieldAccessorImpl(f, false);

        // Test getFloat returns the int value as float
        float val = acc.getFloat(obj);
        if (val != obj.intField) {
            throw new RuntimeException("getFloat returned " + val + ", expected " + obj.intField);
        }

        // Test getFloat throws IllegalArgumentException if null passed
        boolean threw = false;
        try {
            acc.getFloat(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("getFloat did not throw IllegalArgumentException for null input");
        }

        System.out.println("Test passed");
    }
}
