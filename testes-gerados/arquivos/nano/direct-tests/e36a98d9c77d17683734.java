/*
 * @test
 * @summary Test getBoolean method of UnsafeQualifiedShortFieldAccessorImpl
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() throws Exception {
        class Dummy {
            public short value;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        // Simulate the UnsafeQualifiedShortFieldAccessorImpl instance
        jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(field, false);
        try {
            accessor.getBoolean(dummy);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getBoolean threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
