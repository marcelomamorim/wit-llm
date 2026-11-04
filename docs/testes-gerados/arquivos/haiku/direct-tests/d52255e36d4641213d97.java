/*
 * @test
 * @summary Verify Field.getBoolean on static double fields throws IllegalArgumentException and getDouble still returns the stored value
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {
    public static final double FINAL_VALUE = 1.5;
    public static double value = 2.5;

    public static void main(String[] args) throws Exception {
        check("FINAL_VALUE", 1.5);
        check("value", 2.5);
    }

    static void check(String name, double expected) throws Exception {
        Field f = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField(name);
        try {
            boolean b = f.getBoolean(null);
            throw new RuntimeException(name + ": expected IllegalArgumentException, got " + b);
        } catch (IllegalArgumentException expectedException) {
        }
        double actual = f.getDouble(null);
        if (actual != expected) {
            throw new RuntimeException(name + ": expected " + expected + " but got " + actual);
        }
    }
}
