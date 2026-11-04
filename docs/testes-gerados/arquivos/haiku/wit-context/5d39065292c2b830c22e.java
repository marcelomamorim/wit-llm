/*
 * @test
 * @summary Verify Field.getFloat on int fields returns widened values and rejects receivers of the wrong type
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {
    public int value;

    public static void main(String[] args) throws Exception {
        UnsafeIntegerFieldAccessorImplWitupTest t = new UnsafeIntegerFieldAccessorImplWitupTest();
        Field f = UnsafeIntegerFieldAccessorImplWitupTest.class.getField("value");

        t.value = 42;
        check(f.getFloat(t) == 42.0f, "getFloat should widen 42 to 42.0f");

        t.value = -7;
        check(f.getFloat(t) == -7.0f, "getFloat should widen -7 to -7.0f");

        t.value = Integer.MAX_VALUE;
        check(f.getFloat(t) == (float) Integer.MAX_VALUE, "getFloat should match (float) Integer.MAX_VALUE");

        boolean thrown = false;
        try {
            f.getFloat("not a receiver of the declaring class");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        check(thrown, "getFloat with a wrong receiver type should throw IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
