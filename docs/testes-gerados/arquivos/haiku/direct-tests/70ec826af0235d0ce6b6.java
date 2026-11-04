/*
 * @test
 * @summary Field.getLong on a static double field must throw IllegalArgumentException, exercising the double accessor getLong path
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {
    public static final double FINAL_D = 1.5;
    public static double plainD = 2.5;

    public static void main(String[] args) throws Exception {
        check("FINAL_D", 1.5);
        check("plainD", 2.5);
    }

    static void check(String name, double expected) throws Exception {
        Field f = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField(name);
        if (f.getDouble(null) != expected) {
            throw new RuntimeException("unexpected getDouble value for " + name);
        }
        boolean thrown = false;
        try {
            f.getLong(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException from getLong for " + name);
        }
    }
}
