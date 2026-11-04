/*
 * @test
 * @summary Checks public Field.getFloat behavior for char fields, which reaches UnsafeQualifiedCharacterFieldAccessorImpl.getFloat, and the IllegalArgumentException for an incompatible receiver
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    private char value = 'A';
    private final char fixed = 'Z';

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedCharacterFieldAccessorImplWitupTest t =
            new UnsafeQualifiedCharacterFieldAccessorImplWitupTest();
        t.testGetFloatReadsCharValue();
        t.testGetFloatAfterSetChar();
        t.testGetFloatOnFinalField();
        t.testGetFloatWrongReceiverThrowsIAE();
    }

    private void testGetFloatReadsCharValue() throws Exception {
        Field f = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getDeclaredField("value");
        f.setAccessible(true);
        float result = f.getFloat(this);
        if (result != (float) 'A') {
            throw new RuntimeException("expected 65.0 but was " + result);
        }
    }

    private void testGetFloatAfterSetChar() throws Exception {
        Field f = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getDeclaredField("value");
        f.setAccessible(true);
        f.setChar(this, 'Q');
        float result = f.getFloat(this);
        if (result != (float) 'Q') {
            throw new RuntimeException("expected 81.0 but was " + result);
        }
    }

    private void testGetFloatOnFinalField() throws Exception {
        Field f = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getDeclaredField("fixed");
        f.setAccessible(true);
        float result = f.getFloat(this);
        if (result != (float) 'Z') {
            throw new RuntimeException("expected 90.0 but was " + result);
        }
    }

    private void testGetFloatWrongReceiverThrowsIAE() throws Exception {
        Field f = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getDeclaredField("value");
        f.setAccessible(true);
        try {
            f.getFloat(new Object());
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for incompatible receiver");
    }
}
