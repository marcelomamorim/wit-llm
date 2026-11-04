/*
 * @test
 * @summary Verify that Field.getByte on an int field reaches UnsafeIntegerFieldAccessorImpl.getByte and throws IllegalArgumentException
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {
    public int value = 42;

    public static void main(String[] args) throws Exception {
        UnsafeIntegerFieldAccessorImplWitupTest target = new UnsafeIntegerFieldAccessorImplWitupTest();
        Field field = UnsafeIntegerFieldAccessorImplWitupTest.class.getField("value");

        if (field.getInt(target) != 42) {
            throw new RuntimeException("getInt baseline failed");
        }

        boolean thrown = false;
        try {
            field.getByte(target);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte on int field");
        }
    }
}
