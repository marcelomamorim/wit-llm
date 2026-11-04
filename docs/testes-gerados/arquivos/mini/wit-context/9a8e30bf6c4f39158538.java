/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getByte method throws expected IllegalArgumentException
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    static class Dummy {
        int value = 42;
    }

    public static void main(String[] args) throws Exception {
        Field f = Dummy.class.getDeclaredField("value");
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(f);

        // Test that getByte always throws IllegalArgumentException
        try {
            accessor.getByte(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Additional sanity check: getInt returns the correct int value
        Dummy d = new Dummy();
        int intValue = accessor.getInt(d);
        if (intValue != 42) {
            throw new RuntimeException("getInt returned unexpected value: " + intValue);
        }
    }
}
