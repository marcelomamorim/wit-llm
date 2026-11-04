/*
 * @test
 * @summary Test for getByte method in UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsIllegalArgumentException();
    }

    public static void testGetByteThrowsIllegalArgumentException() {
        // Setup: create a dummy Field object (using reflection on a dummy class)
        class Dummy {
            public long value;
        }
        Dummy dummyInstance = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        // Instantiate UnsafeQualifiedLongFieldAccessorImpl with the field
        jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl(field, false);
        // Call getByte and expect IllegalArgumentException
        try {
            accessor.getByte(dummyInstance);
            // If no exception, test fails
            System.err.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("PASS: getByte threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
