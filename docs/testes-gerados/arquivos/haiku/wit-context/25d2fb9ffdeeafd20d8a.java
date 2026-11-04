/*
 * @test
 * @summary Verifies that Field.getByte on a static int field throws IllegalArgumentException, while Field.get returns the boxed int value
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {
    public static int value = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticIntegerFieldAccessorImplWitupTest.class.getField("value");

        Object boxed = f.get(null);
        if (!Integer.valueOf(42).equals(boxed)) {
            throw new RuntimeException("Expected Integer 42 but got " + boxed);
        }

        try {
            f.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException from getByte on int field");
        } catch (IllegalArgumentException expected) {
            // expected: int to byte is not a widening conversion
        }
    }
}
