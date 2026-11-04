/*
 * @test
 * @summary Black-box checks of JarVerifier.getCodeSource via reflection for unsigned entries
 * @run main/othervm --add-opens java.base/java.util.jar=ALL-UNNAMED JarVerifierWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.CodeSource;

public class JarVerifierWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("java.util.jar.JarVerifier");

        Constructor<?> ctor = cls.getDeclaredConstructor(byte[].class);
        ctor.setAccessible(true);
        Object verifier = ctor.newInstance(new Object[] { new byte[0] });

        Method m = cls.getMethod("getCodeSource", URL.class, String.class);
        m.setAccessible(true);

        URL url = new URL("file:/tmp/witup-test.jar");

        CodeSource cs = invoke(m, verifier, url, "Foo.class");
        check(cs != null, "CodeSource must not be null");
        check(url.toExternalForm().equals(cs.getLocation().toExternalForm()),
              "CodeSource location must match the URL argument");
        check(cs.getCertificates() == null,
              "Unsigned entry must yield null certificates");

        CodeSource cs2 = invoke(m, verifier, url, "Foo.class");
        check(cs2 != null, "Repeated call must not return null");
        check(url.toExternalForm().equals(cs2.getLocation().toExternalForm()),
              "Repeated call must keep the same location");

        CodeSource cs3 = invoke(m, verifier, url, null);
        check(cs3 != null, "Null name must not cause null CodeSource");
        check(url.toExternalForm().equals(cs3.getLocation().toExternalForm()),
              "Null name must keep the URL location");
    }

    private static CodeSource invoke(Method m, Object target, URL url, String name)
            throws Exception {
        try {
            return (CodeSource) m.invoke(target, url, name);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            throw new RuntimeException(cause);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
