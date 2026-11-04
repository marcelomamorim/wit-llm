/* @test    @summary Test getBoolean method of UnsafeLongFieldAccessorImpl class    @run main UnsafeLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            Field dummyField = Dummy.class.getDeclaredField("value");
            UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(dummyField);
            accessor.getBoolean(new Dummy());
            System.out.println("Test failed: getBoolean did not throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed: getBoolean threw IllegalArgumentException as expected");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }

    static class Dummy {
        public long value;
    }
}