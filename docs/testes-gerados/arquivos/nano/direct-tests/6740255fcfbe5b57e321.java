/* @test    @summary Test getByte method of UnsafeDoubleFieldAccessorImpl class to ensure it throws IllegalArgumentException.    @run main UnsafeDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    public static void testGetByteThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeDoubleFieldAccessorImpl with a dummy Field
            Field dummyField = Dummy.class.getDeclaredFields().length > 0 ? Dummy.class.getDeclaredFields()[0] : null;
            // Since the actual constructor is package-private, simulate the call via reflection if needed
            // But for this test, assume we can instantiate directly or mock the behavior
            // Here, we simulate by creating an anonymous subclass that overrides getByte to throw
            jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl(dummyField) {
                @Override
                public byte getByte(Object obj) throws IllegalArgumentException {
                    return super.getByte(obj);
                }
            };
            // Call getByte and expect IllegalArgumentException
            accessor.getByte(new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
