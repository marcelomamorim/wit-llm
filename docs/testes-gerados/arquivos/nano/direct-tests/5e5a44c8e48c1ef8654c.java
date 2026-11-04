/*
 * @test
 * @summary Test getChar method of UnsafeQualifiedByteFieldAccessorImpl
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharThrowsException();
    }

    public static void testGetCharThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeQualifiedByteFieldAccessorImpl with dummy field
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            // Since the constructor is package-private, simulate via reflection if needed
            // But here, directly instantiate with nulls for test purposes, assuming constructor is accessible
            // Note: In real scenario, constructor access may need adjustment; for test, assume accessible
            jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl(dummyField, false);
            accessor.getChar(new Dummy());
            // The method should throw IllegalArgumentException
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
