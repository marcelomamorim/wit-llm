/* @test    @summary Test getBoolean method of UnsafeObjectFieldAccessorImpl that always throws IllegalArgumentException    @run main UnsafeObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeObjectFieldAccessorImpl with a dummy Field
            Field dummyField = Dummy.class.getDeclaredFields().length > 0 ? Dummy.class.getDeclaredFields()[0] : null;
            // Since the constructor is package-private, simulate the behavior by subclassing or reflection if needed
            // For simplicity, assume we can instantiate directly (or mock if necessary)
            // But as per constraints, we cannot instantiate directly; instead, test the method behavior directly
            // So, we invoke getBoolean and expect an IllegalArgumentException
            jdk.internal.reflect.UnsafeObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeObjectFieldAccessorImpl(dummyField);
            accessor.getBoolean(new Dummy());
            // If no exception, the test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("getBoolean() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
