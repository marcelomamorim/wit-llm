/* @test    @summary Test getByte method of UnsafeQualifiedFloatFieldAccessorImpl that throws IllegalArgumentException    @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    public static void testGetByteThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeQualifiedFloatFieldAccessorImpl with dummy field
            Field dummyField = Dummy.class.getDeclaredFields().length > 0 ? Dummy.class.getDeclaredFields()[0] : null;
            // Since the constructor is package-private, simulate the behavior by subclassing or reflection if needed
            // For simplicity, assume we can instantiate directly with null (not realistic, but for test illustration)
            // In real scenario, reflection or a test-specific constructor would be used.
            // Here, we focus on calling getByte and expecting an exception.
            // Since the method always throws, we can invoke it directly.
            jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl(dummyField, false) {
                @Override
                public byte getByte(Object obj) {
                    return super.getByte(obj);
                }
            };
            accessor.getByte(new Object());
            // If no exception, fail the test
            System.err.println("Test failed: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed: IllegalArgumentException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Test failed: Unexpected exception: " + t);
        }
    }
}
