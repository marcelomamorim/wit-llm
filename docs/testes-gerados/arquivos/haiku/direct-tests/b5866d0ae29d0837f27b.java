/*
 * @test
 * @summary getDecimalExponent on exceptional (NaN/Infinity) converters must throw IllegalArgumentException
 * @modules java.base/jdk.internal.math
 * @run main/othervm --add-opens java.base/jdk.internal.math=ALL-UNNAMED FloatingDecimalWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jdk.internal.math.FloatingDecimal;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) throws Exception {
        Method factory = FloatingDecimal.class.getDeclaredMethod("getBinaryToASCIIConverter", double.class);
        factory.setAccessible(true);

        checkExceptional(factory, Double.NaN);
        checkExceptional(factory, Double.POSITIVE_INFINITY);
        checkExceptional(factory, Double.NEGATIVE_INFINITY);
    }

    private static void checkExceptional(Method factory, double value) throws Exception {
        Object converter = factory.invoke(null, value);
        Method getExp = converter.getClass().getMethod("getDecimalExponent");
        getExp.setAccessible(true);
        try {
            getExp.invoke(converter);
            throw new AssertionError("Expected IllegalArgumentException for " + value);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected cause for " + value + ": " + cause, cause);
            }
        }
    }
}
