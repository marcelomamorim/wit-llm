/*
 * @test
 * @summary ParserSAX.setFeature always throws UnsupportedOperationException
 * @modules java.base/jdk.internal.util.xml.impl
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
        Method m = c.getDeclaredMethod("setFeature", String.class, boolean.class);
        m.setAccessible(true);

        check(parser, m, "http://xml.org/sax/features/namespaces", true);
        check(parser, m, "http://xml.org/sax/features/namespaces", false);
        check(parser, m, "http://xml.org/sax/features/namespace-prefixes", true);
        check(parser, m, "http://example.org/unknown", false);
    }

    static void check(Object parser, Method m, String name, boolean value) throws Exception {
        try {
            m.invoke(parser, name, value);
            throw new AssertionError("expected UnsupportedOperationException for " + name);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof UnsupportedOperationException)) {
                throw new AssertionError("unexpected cause for " + name, cause);
            }
        }
    }
}
