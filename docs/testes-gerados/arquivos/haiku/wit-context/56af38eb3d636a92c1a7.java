/*
 * @test
 * @summary Verify that reading a char field as byte through the public Field API throws IllegalArgumentException, as UnsafeCharacterFieldAccessorImpl.getByte does
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    public static char staticChar = 'x';

    public static void main(String[] args) throws Exception {
        Field f = UnsafeCharacterFieldAccessorImplWitupTest.class.getField("staticChar");

        Object boxed = f.get(null);
        if (!(boxed instanceof Character) || ((Character) boxed).charValue() != 'x') {
            throw new AssertionError("unexpected get() result: " + boxed);
        }

        try {
            f.getByte(null);
            throw new AssertionError("expected IllegalArgumentException from getByte(null)");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getByte(new Object());
            throw new AssertionError("expected IllegalArgumentException from getByte(Object)");
        } catch (IllegalArgumentException expected) {
        }
    }
}
