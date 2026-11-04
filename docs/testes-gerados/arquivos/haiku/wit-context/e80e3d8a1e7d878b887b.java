/*
 * @test
 * @summary Verify that Field.getInt on a static double field throws IllegalArgumentException
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    static double value = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        if (f.getDouble(null) != 1.5) {
            throw new AssertionError("getDouble on static double field returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getInt(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getInt on double field");
        }
    }
}
