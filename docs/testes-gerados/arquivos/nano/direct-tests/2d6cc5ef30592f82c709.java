/*
 * @test
 * @summary Test getLong method of UnsafeDoubleFieldAccessorImpl
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsException();
    }

    private static void testGetLongThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl(dummyField);
            accessor.getLong(new Dummy());
            System.out.println("Test failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed: IllegalArgumentException thrown as expected");
        } catch (NoSuchFieldException e) {
            System.out.println("Test setup failed: field not found");
        } catch (Exception e) {
            System.out.println("Test failed: unexpected exception: " + e);
        }
    }
}
