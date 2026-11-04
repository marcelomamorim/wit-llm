/* @test    @summary Test getLong method of UnsafeStaticDoubleFieldAccessorImpl for exception throwing behavior    @run main UnsafeStaticDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsException();
    }

    private static void testGetLongThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            // Instantiate the class with dummy field
            UnsafeStaticDoubleFieldAccessorImpl accessor = new UnsafeStaticDoubleFieldAccessorImpl(dummyField);
            // Call getLong, expecting an IllegalArgumentException
            accessor.getLong(null);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
