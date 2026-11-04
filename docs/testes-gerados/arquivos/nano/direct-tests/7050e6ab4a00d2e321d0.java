/* @test    @summary Test getShort method of UnsafeCharacterFieldAccessorImpl class    @run main UnsafeCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl(dummyField);
            accessor.getShort(new Dummy());
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: getShort throws IllegalArgumentException as expected");
            return;
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception: " + t);
            return;
        }
        System.out.println("Failed: getShort did not throw IllegalArgumentException");
    }
}
