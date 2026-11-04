/*
 * @test
 * @summary Test UnsafeQualifiedCharacterFieldAccessorImpl.getLong(Object) method
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class TestObj {
        public volatile char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getField("c");
        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        // getLong should return the char value as a long
        long val = accessor.getLong(obj);
        if (val != 'A') {
            throw new AssertionError("Expected 'A' as long value, got: " + val);
        }

        // Changing the char field and testing again
        obj.c = 'Z';
        val = accessor.getLong(obj);
        if (val != 'Z') {
            throw new AssertionError("Expected 'Z' as long value, got: " + val);
        }

        // Test with null object should throw IllegalArgumentException
        boolean threw = false;
        try {
            accessor.getLong(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException when passing null to getLong");
        }

        System.out.println("Test passed.");
    }
}
