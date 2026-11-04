/*
 * @test
 * @summary Field.getByte on an int field must throw IllegalArgumentException, reaching UnsafeIntegerFieldAccessorImpl.getByte
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {
    public int value = 42;

    public static void main(String[] args) throws Exception {
        UnsafeIntegerFieldAccessorImplWitupTest target = new UnsafeIntegerFieldAccessorImplWitupTest();
        Field field = UnsafeIntegerFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            field.getByte(target);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from Field.getByte on int field");
        }

        if (field.getInt(target) != 42) {
            throw new RuntimeException("Int value changed after rejected getByte call");
        }
    }
}
