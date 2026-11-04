/*
 * @test
 * @summary NegotiateAuthentication.getHeaderValue always throws RuntimeException with a fixed message
 * @modules java.base/sun.net.www.protocol.http jdk.unsupported
 * @run main/othervm --add-opens java.base/sun.net.www.protocol.http=ALL-UNNAMED NegotiateAuthenticationWitupTest
 */

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class NegotiateAuthenticationWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.net.www.protocol.http.NegotiateAuthentication");

        Method m = c.getDeclaredMethod("getHeaderValue", URL.class, String.class);
        if (m.getReturnType() != String.class) {
            throw new AssertionError("unexpected return type: " + m.getReturnType());
        }
        m.setAccessible(true);

        Object instance = allocateWithoutConstructor(c);

        URL url = new URL("http://example.com/");
        try {
            Object result = m.invoke(instance, url, "GET");
            throw new AssertionError("expected exception, got: " + result);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof RuntimeException)) {
                throw new AssertionError("unexpected cause: " + cause);
            }
            if (!"getHeaderValue not supported".equals(cause.getMessage())) {
                throw new AssertionError("unexpected message: " + cause.getMessage());
            }
        }

        try {
            m.invoke(instance, null, null);
            throw new AssertionError("expected exception for null arguments");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof RuntimeException)
                    || !"getHeaderValue not supported".equals(cause.getMessage())) {
                throw new AssertionError("unexpected cause for null args: " + cause);
            }
        }
    }

    private static Object allocateWithoutConstructor(Class<?> c) throws Exception {
        Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
        Field f = unsafeClass.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Object unsafe = f.get(null);
        Method allocate = unsafeClass.getMethod("allocateInstance", Class.class);
        return allocate.invoke(unsafe, c);
    }
}
