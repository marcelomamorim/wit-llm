/*
 * @test
 * @summary Verifies that SSLSessionImpl.removeValue(null) throws IllegalArgumentException
 * @run main/othervm --add-opens java.base/sun.security.ssl=ALL-UNNAMED SSLSessionImplWitupTest
 */

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SSLSessionImplWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.security.ssl.SSLSessionImpl");
        Field f = c.getDeclaredField("nullSession");
        f.setAccessible(true);
        Object session = f.get(null);

        Method m = c.getDeclaredMethod("removeValue", String.class);
        m.setAccessible(true);

        boolean thrown = false;
        try {
            m.invoke(session, (Object) null);
        } catch (InvocationTargetException e) {
            thrown = true;
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause", e.getCause());
            }
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for null key");
        }
    }
}
