/*
 * @test
 * @summary Field.getShort on a static double field must throw IllegalArgumentException, while Field.get returns the Double value
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static final double CONST = 1.5;

    public static double value = 2.5;

    public static void main(String[] args) throws Exception {
        Field constField = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("CONST");
        Field valueField = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        Object boxed = constField.get(null);
        if (!(boxed instanceof Double) || ((Double) boxed).doubleValue() != 1.5) {
            throw new AssertionError("Unexpected get() result for CONST: " + boxed);
        }

        try {
            short s = constField.getShort(null);
            throw new AssertionError("getShort on final double field returned " + s);
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            short s = valueField.getShort(null);
            throw new AssertionError("getShort on double field returned " + s);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
