/*
 * @test
 * @summary Field.getInt on a static double field must throw IllegalArgumentException
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static final double FINAL_D = 1.5;
    public static double plainD = 2.5;

    public static void main(String[] args) throws Exception {
        Field finalField = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getField("FINAL_D");
        Field plainField = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getField("plainD");

        expectGetIntIAE(finalField);
        expectGetIntIAE(plainField);

        if (finalField.getDouble(null) != 1.5) {
            throw new RuntimeException("FINAL_D value changed");
        }
        if (plainField.getDouble(null) != 2.5) {
            throw new RuntimeException("plainD value changed");
        }
    }

    private static void expectGetIntIAE(Field f) {
        try {
            int v = f.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException for "
                    + f.getName() + ", got " + v);
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected IllegalAccessException for " + f.getName(), e);
        }
    }
}
