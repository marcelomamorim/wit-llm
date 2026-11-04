/* @test    @summary Test getChar method of UnsafeLongFieldAccessorImpl for exception throwing    @run main UnsafeLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() throws Exception {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredField("dummy");
        dummyField.setAccessible(true);
        jdk.internal.reflect.UnsafeLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeLongFieldAccessorImpl(dummyField);
        try {
            accessor.getChar(new Object());
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getChar threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
