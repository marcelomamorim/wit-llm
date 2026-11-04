/*
 * @test
 * @summary Verifies that Field.getChar on an int instance field throws IllegalArgumentException, exercising the integer field accessor behavior behind UnsafeIntegerFieldAccessorImpl.getChar
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {
    public int intValue = 42;

    public static void main(String[] args) throws Exception {
        UnsafeIntegerFieldAccessorImplWitupTest holder = new UnsafeIntegerFieldAccessorImplWitupTest();
        Field field = UnsafeIntegerFieldAccessorImplWitupTest.class.getField("intValue");

        int readInt = field.getInt(holder);
        if (readInt != 42) {
            throw new RuntimeException("Expected getInt to return 42 but got " + readInt);
        }

        boolean thrown = false;
        try {
            field.getChar(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getChar on int field");
        }
    }
}
