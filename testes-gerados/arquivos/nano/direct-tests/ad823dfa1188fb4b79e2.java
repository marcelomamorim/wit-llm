/* @test    @summary Test getFloat method of UnsafeQualifiedObjectFieldAccessorImpl for exception throwing behavior    @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsException();
    }

    private static void testGetFloatThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeQualifiedObjectFieldAccessorImpl with a dummy Field
            Field dummyField = Dummy.class.getDeclaredFields()[0];
            // Since the actual constructor is package-private, simulate behavior by subclassing or reflection if needed
            // For this test, assume we can instantiate directly (or mock if necessary)
            // But as per constraints, we focus on the public method behavior
            // Here, we directly call getFloat, expecting it to throw
            jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false) {
                @Override
                public float getFloat(Object obj) {
                    return super.getFloat(obj);
                }
            };
            // Call getFloat and expect an IllegalArgumentException
            accessor.getFloat(new Object());
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
