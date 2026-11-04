/*
 * @test
 * @summary Test getByte method of UnsafeStaticCharacterFieldAccessorImpl
 * @run main UnsafeStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsException();
    }

    public static void testGetByteThrowsException() throws Exception {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredField("dummy");
        dummyField.setAccessible(true);
        jdk.internal.reflect.UnsafeStaticCharacterFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeStaticCharacterFieldAccessorImpl(dummyField);
        try {
            byte result = accessor.getByte(null);
            if (result != 0) {
                throw new AssertionError("Expected getByte to throw, but returned: " + result);
            }
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
