/*
 * @test
 * @summary Field.getShort on a volatile char field must throw IllegalArgumentException
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {
    public volatile char c = 'A';

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedCharacterFieldAccessorImplWitupTest t = new UnsafeQualifiedCharacterFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getField("c");

        boolean thrown = false;
        try {
            f.getShort(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getShort on char field");
        }

        if (f.getChar(t) != 'A') {
            throw new RuntimeException("getChar should still return the field value");
        }
    }
}
