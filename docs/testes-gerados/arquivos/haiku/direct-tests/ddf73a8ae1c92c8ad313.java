/*
 * @test
 * @summary Verify that SSLSessionImpl.getValue rejects a null key with IllegalArgumentException
 * @modules java.base/sun.security.ssl
 * @run main/othervm --add-opens java.base/sun.security.ssl=ALL-UNNAMED SSLSessionImplWitupTest
 */

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SSLSessionImplWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.security.ssl.SSLSessionImpl");

        Field nullSessionField = c.getDeclaredField("nullSession");
        nullSessionField.setAccessible(true);
        Object session = nullSessionField.get(null);

        Method getValue = c.getDeclaredMethod("getValue", String.class);
        getValue.setAccessible(true);

        try {
            getValue.invoke(session, (Object) null);
            throw new RuntimeException("Expected IllegalArgumentException for null key");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("Unexpected cause: " + cause, e);
            }
        }
    }
}
