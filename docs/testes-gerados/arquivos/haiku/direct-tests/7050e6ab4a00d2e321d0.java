/*
 * @test
 * @summary Verify that Field.getShort on a char field is rejected via the public reflection API
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    public char c = 'x';

    public static void main(String[] args) throws Exception {
        UnsafeCharacterFieldAccessorImplWitupTest t = new UnsafeCharacterFieldAccessorImplWitupTest();
        Field f = UnsafeCharacterFieldAccessorImplWitupTest.class.getField("c");
        if (f.getType() != char.class) {
            throw new RuntimeException("unexpected field type: " + f.getType());
        }

        if (f.getChar(t) != 'x') {
            throw new RuntimeException("getChar returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getShort(t);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getShort on char field did not throw IllegalArgumentException");
        }

        thrown = false;
        try {
            f.getShort(t);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("second getShort call did not throw IllegalArgumentException");
        }
    }
}
