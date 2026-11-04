/*
 * @test
 * @summary ParserSAX.getProperty must throw UnsupportedOperationException for any name
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main/othervm --add-opens java.base/jdk.internal.util.xml.impl=ALL-UNNAMED ParserSAXWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ParserSAXWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static Object newParser() throws Exception {
        Class<?> cls = Class.forName("jdk.internal.util.xml.impl.ParserSAX");
        Constructor<?> ctor = cls.getDeclaredConstructor();
        ctor.setAccessible(true);
        return ctor.newInstance();
    }

    private static void assertGetPropertyUnsupported(Object parser, String name) throws Exception {
        Method m = parser.getClass().getDeclaredMethod("getProperty", String.class);
        m.setAccessible(true);
        try {
            m.invoke(parser, name);
            throw new RuntimeException("Expected UnsupportedOperationException for name: " + name);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            check(cause instanceof UnsupportedOperationException,
                  "Unexpected cause: " + cause);
        }
    }

    public static void main(String[] args) throws Exception {
        Object parser = newParser();
        assertGetPropertyUnsupported(parser, "http://xml.org/sax/properties/lexical-handler");
        assertGetPropertyUnsupported(parser, "http://xml.org/sax/features/namespaces");
        assertGetPropertyUnsupported(parser, "");
        assertGetPropertyUnsupported(parser, null);
    }
}
