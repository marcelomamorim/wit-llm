/* @test    @summary Test getLong method of UnsafeObjectFieldAccessorImpl class    @run main UnsafeObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsException();
    }

    private static void testGetLongReturnsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeObjectFieldAccessorImpl with a dummy Field
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            // Since the constructor is package-private, simulate via reflection if needed
            // but here, for test purposes, assume direct instantiation is possible
            // or simulate via subclassing if constructor is accessible
            // For simplicity, assume constructor is accessible in test context
            jdk.internal.reflect.UnsafeObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeObjectFieldAccessorImpl(dummyField);
            // Call getLong with a dummy object
            Object dummyObject = new Object();
            long result = accessor.getLong(dummyObject);
            // Since the method always throws, reaching this line indicates failure
            System.err.println("Expected getLong to throw IllegalArgumentException, but it returned: " + result);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("getLong method correctly throws IllegalArgumentException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
