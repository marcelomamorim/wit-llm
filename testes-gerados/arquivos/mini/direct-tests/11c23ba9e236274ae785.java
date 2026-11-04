/*
 * @test
 * @summary Test that getChar(Object) throws IllegalArgumentException as expected
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy static double field to pass to the constructor
        Field doubleField = Dummy.class.getDeclaredField("staticDouble");

        // Create instance with isReadOnly false
        UnsafeQualifiedStaticDoubleFieldAccessorImpl accessor =
            new UnsafeQualifiedStaticDoubleFieldAccessorImpl(doubleField, false);

        // Test getChar throws IllegalArgumentException
        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static class Dummy {
        static double staticDouble = 0.0;
    }
}
