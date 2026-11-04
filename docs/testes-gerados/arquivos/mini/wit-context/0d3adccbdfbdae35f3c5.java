/*
 * @test
 * @summary Test UnsafeQualifiedIntegerFieldAccessorImpl.getLong(Object) for correct behavior and exception on invalid object
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        public int intField = 12345;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for intField
        Field field = TestClass.class.getDeclaredField("intField");

        // Create UnsafeQualifiedIntegerFieldAccessorImpl instance (via reflection since constructor is package-private)
        UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(field, false);

        TestClass testObj = new TestClass();

        // Test getLong returns the int value as long
        long val = accessor.getLong(testObj);
        if (val != testObj.intField) {
            throw new RuntimeException("getLong returned " + val + ", expected " + testObj.intField);
        }

        // Test getLong throws IllegalArgumentException when passed object of wrong type
        boolean threwIAE = false;
        try {
            accessor.getLong(new Object());
        } catch (IllegalArgumentException e) {
            threwIAE = true;
        }
        if (!threwIAE) {
            throw new RuntimeException("getLong did not throw IllegalArgumentException for invalid object");
        }

        System.out.println("Test passed");
    }
}
