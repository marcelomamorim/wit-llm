/*
 * @test
 * @summary Verify that Field.getChar on a static double field throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static final double CONST = 1.5;
    public static double plain = 2.5;

    public static void main(String[] args) throws Exception {
        Field constField = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("CONST");
        Field plainField = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("plain");

        checkGetCharThrows(constField);
        checkGetCharThrows(plainField);

        if (!Double.valueOf(1.5).equals(constField.get(null))) {
            throw new AssertionError("unexpected value for CONST");
        }
    }

    private static void checkGetCharThrows(Field f) {
        try {
            f.getChar(null);
            throw new AssertionError("expected IllegalArgumentException for " + f.getName());
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (IllegalAccessException e) {
            throw new AssertionError("unexpected IllegalAccessException", e);
        }
    }
}
