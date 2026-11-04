/* @test    @summary Test getByte method of UnsafeStaticIntegerFieldAccessorImpl class    @run main UnsafeStaticIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredFields().length == 0 ? null : Dummy.class.getDeclaredFields()[0];
        jdk.internal.reflect.UnsafeStaticIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticIntegerFieldAccessorImpl(dummyField);
        try {
            accessor.getByte(null);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getByte threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
