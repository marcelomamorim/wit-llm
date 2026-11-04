/*
 * @test
 * @summary digitsRoundedUp on exceptional BinaryToASCIIConverter must throw IllegalArgumentException
 * @modules java.base/jdk.internal.math
 * @run main/othervm --add-opens java.base/jdk.internal.math=ALL-UNNAMED FloatingDecimalWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FloatingDecimalWitupTest {
    public static void main(String[] args) throws Exception {
        Method factory = Class.forName("jdk.internal.math.FloatingDecimal")
                .getDeclaredMethod("getBinaryToASCIIConverter", double.class);
        factory.setAccessible(true);

        double[] values = {0.0, Double.POSITIVE_INFINITY, Double.NaN};
        for (double v : values) {
            Object conv = factory.invoke(null, v);
            Method m = conv.getClass().getDeclaredMethod("digitsRoundedUp");
            m.setAccessible(true);
            boolean thrown = false;
            try {
                m.invoke(conv);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (!(cause instanceof IllegalArgumentException)) {
                    throw new RuntimeException("unexpected cause for " + v, cause);
                }
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("expected IllegalArgumentException for " + v);
            }
        }
    }
}
