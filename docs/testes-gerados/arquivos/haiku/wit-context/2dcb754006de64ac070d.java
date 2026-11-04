/*
 * @test
 * @summary Verifies observable Field.getChar behavior that reaches UnsafeCharacterFieldAccessorImpl.getChar
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static class Target {
        public char value = 'x';
        public final char finalValue = 'q';
    }

    public static void main(String[] args) throws Exception {
        testReadsCharValue();
        testReadsFinalCharValue();
        testWrongObjectTypeThrowsIAE();
        testNullObjectThrowsNPE();
    }

    static void testReadsCharValue() throws Exception {
        Field f = Target.class.getDeclaredField("value");
        Target t = new Target();
        char c = f.getChar(t);
        if (c != 'x') {
            throw new RuntimeException("expected 'x' but got " + c);
        }
        f.setChar(t, 'z');
        if (f.getChar(t) != 'z') {
            throw new RuntimeException("expected 'z' after setChar");
        }
    }

    static void testReadsFinalCharValue() throws Exception {
        Field f = Target.class.getDeclaredField("finalValue");
        Target t = new Target();
        if (f.getChar(t) != 'q') {
            throw new RuntimeException("expected 'q' from final field");
        }
    }

    static void testWrongObjectTypeThrowsIAE() throws Exception {
        Field f = Target.class.getDeclaredField("value");
        try {
            f.getChar(new Object());
            throw new RuntimeException("expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            f.getChar("not a Target");
            throw new RuntimeException("expected IllegalArgumentException for String object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNullObjectThrowsNPE() throws Exception {
        Field f = Target.class.getDeclaredField("value");
        try {
            f.getChar(null);
            throw new RuntimeException("expected NullPointerException for null instance");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
