/*
 * @test
 * @summary Field.getBoolean on a static double field must throw IllegalArgumentException, while getDouble still returns the value
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import java.util.concurrent.Callable;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static double value = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        if (f.getDouble(null) != 1.5) {
            throw new RuntimeException("getDouble returned unexpected value");
        }

        expectIllegalArgument(() -> f.getBoolean(null), "getBoolean");

        if (f.getDouble(null) != 1.5) {
            throw new RuntimeException("value changed after failed getBoolean");
        }
    }

    private static void expectIllegalArgument(Callable<Object> action, String name) {
        boolean thrown = false;
        try {
            action.call();
        } catch (IllegalArgumentException e) {
            thrown = true;
        } catch (Exception e) {
            throw new RuntimeException(name + " threw unexpected exception: " + e, e);
        }
        if (!thrown) {
            throw new RuntimeException(name + " did not throw IllegalArgumentException");
        }
    }
}
