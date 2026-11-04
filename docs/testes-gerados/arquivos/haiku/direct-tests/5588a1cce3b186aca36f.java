/*
 * @test
 * @summary Verify that Field.getFloat on a char field returns the widened char value
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class Holder {
        public final char finalChar;
        public char mutableChar;

        Holder(char c) {
            this.finalChar = c;
            this.mutableChar = c;
        }
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder('A');

        Field finalField = Holder.class.getField("finalChar");
        check(finalField.getFloat(h) == 65.0f, "final char 'A' as float");

        Holder zero = new Holder('\u0000');
        check(finalField.getFloat(zero) == 0.0f, "final char NUL as float");

        Holder max = new Holder('\uFFFF');
        check(finalField.getFloat(max) == 65535.0f, "final char 0xFFFF as float");

        Field mutableField = Holder.class.getField("mutableChar");
        mutableField.setChar(h, 'z');
        check(mutableField.getFloat(h) == 122.0f, "mutable char 'z' as float after setChar");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
