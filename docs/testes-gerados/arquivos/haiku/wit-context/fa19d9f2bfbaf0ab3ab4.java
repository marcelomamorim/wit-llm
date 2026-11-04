/*
 * @test
 * @summary Verify that ParserSAX.setFeature always throws UnsupportedOperationException
 * @run main/othervm --add-opens java.base/jdk.internal.util.xml.impl=ALL-UNNAMED ParserSAXWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ParserSAXWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("jdk.internal.util.xml.impl.ParserSAX");
        Constructor<?> ctor = c.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object parser = ctor.newInstance();
        Method m = c.getMethod("setFeature", String.class, boolean.class);
        m.setAccessible(true);

        check(m, parser, "http://xml.org/sax/features/namespaces", true);
        check(m, parser, "http://xml.org/sax/features/namespaces", false);
        check(m, parser, "http://xml.org/sax/features/namespace-prefixes", true);
        check(m, parser, "unknown-feature", false);
    }

    private static void check(Method m, Object parser, String name, boolean value)
            throws Exception {
        try {
            m.invoke(parser, name, value);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof UnsupportedOperationException)) {
                throw new AssertionError("unexpected cause: " + cause, cause);
            }
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException for feature " + name);
    }
}
