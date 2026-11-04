/*
 * @test
 * @summary Verify that Field.getDouble on char fields widens the stored char value to double, including final fields accessed with setAccessible.
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    private final char finalChar;
    private char mutableChar;

    UnsafeQualifiedCharacterFieldAccessorImplWitupTest() {
        finalChar = 'A';
        mutableChar = 'z';
    }

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedCharacterFieldAccessorImplWitupTest t =
            new UnsafeQualifiedCharacterFieldAccessorImplWitupTest();

        Field finalField = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class
            .getDeclaredField("finalChar");
        finalField.setAccessible(true);
        check(finalField.getDouble(t) == (double) 'A', "final char getDouble");

        Field mutableField = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class
            .getDeclaredField("mutableChar");
        mutableField.setAccessible(true);
        check(mutableField.getDouble(t) == (double) 'z', "mutable char getDouble");

        mutableField.setChar(t, '\uFFFF');
        check(mutableField.getDouble(t) == 65535.0d, "max char getDouble");

        mutableField.setChar(t, '\u0000');
        check(mutableField.getDouble(t) == 0.0d, "min char getDouble");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
