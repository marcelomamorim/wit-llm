/*
 * @test
 * @summary Test UnsafeQualifiedCharacterFieldAccessorImpl.getDouble(Object) method
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class TestObj {
        volatile char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getDeclaredField("c");
        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        // getDouble should return the char value as double
        double val = accessor.getDouble(obj);
        if (val != 'A') {
            throw new RuntimeException("Expected 'A' as double, got: " + val);
        }

        // Changing the char field and testing again
        obj.c = 'Z';
        val = accessor.getDouble(obj);
        if (val != 'Z') {
            throw new RuntimeException("Expected 'Z' as double, got: " + val);
        }

        // Test with null object should throw IllegalArgumentException
        boolean threw = false;
        try {
            accessor.getDouble(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException when passing null to getDouble");
        }

        System.out.println("Test passed.");
    }
}
