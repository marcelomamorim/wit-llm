/*
 * @test
 * @summary Field.getLong on a double field must throw IllegalArgumentException (public contract that reaches the double accessor getLong)
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static double VALUE = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getField("VALUE");

        try {
            long v = f.getLong(null);
            throw new AssertionError("expected IllegalArgumentException, got " + v);
        } catch (IllegalArgumentException expected) {
        }

        if (f.getDouble(null) != 1.5) {
            throw new AssertionError("getDouble mismatch");
        }
    }
}
