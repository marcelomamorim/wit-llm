/*
 * @test
 * @summary Verifies that the public DefaultSSLContext() constructor creates a usable SSLContextSpi instance on each call
 * @modules java.base/sun.security.ssl
 * @run main/othervm --add-opens java.base/sun.security.ssl=ALL-UNNAMED SSLContextImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SSLContextImplWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> defaultCtxClass =
                Class.forName("sun.security.ssl.SSLContextImpl$DefaultSSLContext");
        Constructor<?> ctor = defaultCtxClass.getDeclaredConstructor();
        ctor.setAccessible(true);

        Object first = newInstance(ctor);
        Object second = newInstance(ctor);

        Class<?> spi = Class.forName("javax.net.ssl.SSLContextSpi");
        if (!spi.isInstance(first) || !spi.isInstance(second)) {
            throw new RuntimeException("DefaultSSLContext is not an SSLContextSpi");
        }
        if (first == second) {
            throw new RuntimeException("DefaultSSLContext() returned the same instance twice");
        }
    }

    private static Object newInstance(Constructor<?> ctor) throws Exception {
        try {
            return ctor.newInstance();
        } catch (InvocationTargetException e) {
            throw new RuntimeException("DefaultSSLContext() failed", e.getCause());
        }
    }
}
