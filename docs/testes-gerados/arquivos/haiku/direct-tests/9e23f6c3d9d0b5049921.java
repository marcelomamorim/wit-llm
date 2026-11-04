/*
 * @test
 * @summary Verifies Field.getChar behavior on a final char instance field, which reaches UnsafeQualifiedCharacterFieldAccessorImpl.getChar through public reflection API
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public final char value = 'x';

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedCharacterFieldAccessorImplWitupTest t = new UnsafeQualifiedCharacterFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getField("value");

        char c = f.getChar(t);
        if (c != 'x') {
            throw new RuntimeException("Expected 'x' but got " + c);
        }

        boolean iae = false;
        try {
            f.getChar("not a holder");
        } catch (IllegalArgumentException e) {
            iae = true;
        }
        if (!iae) {
            throw new RuntimeException("Expected IllegalArgumentException for wrong receiver type");
        }
    }
}
