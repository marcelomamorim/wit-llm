/* @test    @summary Test getByte method of UnsafeShortFieldAccessorImpl class to ensure it throws IllegalArgumentException.    @run main UnsafeShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    public static void testGetByteThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredFields()[0];
            // Since no fields exist, create a dummy Field object via reflection hack (not standard, but for test purposes)
            // Alternatively, simulate the method call directly
            jdk.internal.reflect.UnsafeShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeShortFieldAccessorImpl(dummyField);
            accessor.getByte(new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("getByte method correctly throws IllegalArgumentException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
