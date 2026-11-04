/* @test    @summary Test getInt method of UnsafeStaticLongFieldAccessorImpl for exception throwing behavior    @run main UnsafeStaticLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntThrowsException();
    }

    private static void testGetIntThrowsException() {
        class DummyField extends Field {
            @Override
            public String toString() {
                return "DummyField";
            }
        }
        // Create an instance of the class with the method to test
        jdk.internal.reflect.UnsafeStaticLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticLongFieldAccessorImpl(new DummyField());
        try {
            accessor.getInt(null);
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
