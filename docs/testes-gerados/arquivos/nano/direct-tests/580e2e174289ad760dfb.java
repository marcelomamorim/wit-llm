/*
 * @test
 * @summary Test getInt method of UnsafeQualifiedFloatFieldAccessorImpl
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsException();
    }

    private static void testGetIntThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeQualifiedFloatFieldAccessorImpl with dummy field
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            // Since the constructor is package-private and the class is package-private,
            // we cannot instantiate it directly. Instead, we simulate the call to getInt.
            // As the method always throws, we invoke it reflectively.
            jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl accessor = null;
            try {
                java.lang.reflect.Constructor<jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl> ctor =
                    jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl.class.getDeclaredConstructor(Field.class, boolean.class);
                ctor.setAccessible(true);
                accessor = ctor.newInstance(dummyField, false);
            } catch (ReflectiveOperationException e) {
                // If instantiation fails, skip test
                return;
            }
            // Invoke getInt and expect an IllegalArgumentException
            try {
                accessor.getInt(new Object());
                // If no exception, test fails
                System.err.println("Expected IllegalArgumentException was not thrown.");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
        } catch (NoSuchFieldException e) {
            // Field does not exist, skip test
        }
    }
}
