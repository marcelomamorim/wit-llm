/* @test    @summary Test getByte method of UnsafeQualifiedStaticIntegerFieldAccessorImpl class    @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteAlwaysThrows();
    }

    private static void testGetByteAlwaysThrows() {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredFields().length > 0 ? Dummy.class.getDeclaredFields()[0] : null;
        // Since getByte always throws, we do not need a real field; pass null
        jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl accessor = 
            new jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl(dummyField, false);
        try {
            accessor.getByte(new Object());
            System.out.println("FAIL: getByte did not throw as expected");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getByte threw IllegalArgumentException as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: getByte threw unexpected exception: " + t);
        }
    }
}
