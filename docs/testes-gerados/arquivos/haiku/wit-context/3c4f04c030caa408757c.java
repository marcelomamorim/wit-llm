/*
 * @test
 * @summary Verify that Field.getShort on an int field throws IllegalArgumentException, exercising UnsafeIntegerFieldAccessorImpl.getShort through public reflection API
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {
    public int value = 42;

    public static void main(String[] args) throws Exception {
        UnsafeIntegerFieldAccessorImplWitupTest obj = new UnsafeIntegerFieldAccessorImplWitupTest();
        Field f = UnsafeIntegerFieldAccessorImplWitupTest.class.getField("value");

        Object boxed = f.get(obj);
        if (!(boxed instanceof Integer) || ((Integer) boxed).intValue() != 42) {
            throw new RuntimeException("unexpected get result: " + boxed);
        }

        try {
            f.getShort(obj);
            throw new RuntimeException("expected IllegalArgumentException from getShort on int field");
        } catch (IllegalArgumentException expected) {
        }
    }
}
