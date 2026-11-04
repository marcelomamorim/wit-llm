/*
 * @test
 * @summary Checks JarVerifier.getCodeSource for an unsigned entry name returns a CodeSource bound to the given URL
 * @run main/othervm --add-opens java.base/java.util.jar=ALL-UNNAMED JarVerifierWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.CodeSource;

public class JarVerifierWitupTest {

    static Object newVerifier() throws Exception {
        Class<?> cls = Class.forName("java.util.jar.JarVerifier");
        Constructor<?> ctor = cls.getDeclaredConstructor(byte[].class);
        ctor.setAccessible(true);
        try {
            return ctor.newInstance(new Object[] { new byte[0] });
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    static CodeSource getCodeSource(Object verifier, URL url, String name) throws Exception {
        Method m = verifier.getClass().getDeclaredMethod("getCodeSource", URL.class, String.class);
        m.setAccessible(true);
        try {
            return (CodeSource) m.invoke(verifier, url, name);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static void main(String[] args) throws Exception {
        Object verifier = newVerifier();
        URL url = new URL("file:/tmp/witup-test.jar");

        CodeSource cs = getCodeSource(verifier, url, "com/example/Missing.class");
        if (cs == null) {
            throw new RuntimeException("getCodeSource returned null for a non-null URL");
        }
        if (!url.equals(cs.getLocation())) {
            throw new RuntimeException("Unexpected location: " + cs.getLocation());
        }

        CodeSource cs2 = getCodeSource(verifier, url, "another/Unknown.class");
        if (cs2 == null || !url.equals(cs2.getLocation())) {
            throw new RuntimeException("Second lookup did not preserve URL location");
        }
    }
}
