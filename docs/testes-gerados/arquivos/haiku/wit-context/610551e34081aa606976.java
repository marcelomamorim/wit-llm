/*
 * @test
 * @summary Verifies that narrowing primitive reads of a static final double field via java.lang.reflect.Field throw IllegalArgumentException, and that getDouble and get return the stored value
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    static final double VALUE = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("VALUE");

        if (f.getDouble(null) != 1.5) {
            throw new AssertionError("getDouble returned unexpected value");
        }
        Object boxed = f.get(null);
        if (!(boxed instanceof Double) || ((Double) boxed).doubleValue() != 1.5) {
            throw new AssertionError("get returned unexpected value: " + boxed);
        }

        expectIllegalArgument(f, "getByte");
        expectIllegalArgument(f, "getInt");
        expectIllegalArgument(f, "getBoolean");
    }

    private static void expectIllegalArgument(Field f, String op) throws IllegalAccessException {
        try {
            switch (op) {
                case "getByte":
                    f.getByte(null);
                    break;
                case "getInt":
                    f.getInt(null);
                    break;
                case "getBoolean":
                    f.getBoolean(null);
                    break;
                default:
                    throw new AssertionError("unknown op " + op);
            }
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError(op + " did not throw IllegalArgumentException");
    }
}
