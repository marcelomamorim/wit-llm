/* @test    @summary Test getInt method of UnsafeQualifiedLongFieldAccessorImpl for exception throwing behavior    @run main UnsafeQualifiedLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntThrowsException();
    }

    private static void testGetIntThrowsException() throws Exception {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredField("dummy");
        dummyField.setAccessible(true);
        jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl(dummyField, false);
        try {
            accessor.getInt(new Object());
            System.out.println("Test failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed: IllegalArgumentException thrown as expected");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
