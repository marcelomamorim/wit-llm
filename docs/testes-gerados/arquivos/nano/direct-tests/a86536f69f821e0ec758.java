/*
 * @test
 * @summary Test getByte method of UnsafeQualifiedCharacterFieldAccessorImpl
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() throws Exception {
        class Dummy {
            public char c = 'A';
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("c");
        jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl(field, false);
        try {
            byte result = accessor.getByte(dummy);
            // Since getByte() always throws, this line should not be reached
            System.out.println("Unexpected success: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException");
        }
    }
}
