/*
 * @test
 * @summary Verify that SSLSessionImpl.getValue(null) throws IllegalArgumentException on the null session
 * @modules java.base/sun.security.ssl
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
        Method m = c.getMethod("getValue", String.class);
        m.setAccessible(true);
        try {
            m.invoke(session, (Object) null);
            throw new RuntimeException("expected IllegalArgumentException was not thrown");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause: " + cause, cause);
            }
        }
    }
}
