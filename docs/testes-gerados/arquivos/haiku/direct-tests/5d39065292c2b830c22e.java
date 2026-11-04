/*
 * @test
 * @summary Verify that Field.getFloat on an int field (served by UnsafeIntegerFieldAccessorImpl) widens the int value to float
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public int value;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeIntegerFieldAccessorImplWitupTest.class.getField("value");
        UnsafeIntegerFieldAccessorImplWitupTest t = new UnsafeIntegerFieldAccessorImplWitupTest();

        t.value = 42;
        check(f.getFloat(t) == 42.0f, "42 should map to 42.0f");

        t.value = 0;
        check(f.getFloat(t) == 0.0f, "0 should map to 0.0f");

        t.value = -7;
        check(f.getFloat(t) == -7.0f, "-7 should map to -7.0f");

        t.value = Integer.MIN_VALUE;
        check(f.getFloat(t) == (float) Integer.MIN_VALUE, "MIN_VALUE widening mismatch");

        t.value = Integer.MAX_VALUE;
        check(f.getFloat(t) == (float) Integer.MAX_VALUE, "MAX_VALUE widening mismatch");

        t.value = 16777217;
        check(f.getFloat(t) == (float) 16777217, "precision loss case mismatch");

        for (int v : new int[] {1, -1, 123456, -987654, 1 << 30}) {
            t.value = v;
            check(f.getFloat(t) == (float) f.getInt(t),
                  "getFloat must equal (float) getInt for " + v);
        }

        t.value = 1;
        f.setInt(t, 7);
        check(f.getFloat(t) == 7.0f, "getFloat must observe updated value 7");

        Object wrong = "not the declaring class";
        try {
            f.getFloat(wrong);
            throw new RuntimeException("expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
