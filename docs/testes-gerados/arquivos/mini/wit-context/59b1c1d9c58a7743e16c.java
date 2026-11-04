/*
 * @test
 * @summary Test UnsafeCharacterFieldAccessorImpl.getLong(Object) behavior
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    static class TestClass {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("c");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(field);

        // Test getLong returns the char value as long
        long val = accessor.getLong(testObj);
        if (val != 'A') {
            throw new RuntimeException("getLong returned " + val + ", expected " + (int) 'A');
        }

        // Test getLong throws IllegalArgumentException if obj is not instance of declaring class
        boolean threwIAE = false;
        try {
            accessor.getLong(new Object());
        } catch (IllegalArgumentException e) {
            threwIAE = true;
        }
        if (!threwIAE) {
            throw new RuntimeException("getLong did not throw IllegalArgumentException for invalid object");
        }

        System.out.println("Test passed.");
    }
}
