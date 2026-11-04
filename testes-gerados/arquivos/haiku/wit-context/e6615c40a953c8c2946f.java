/*
 * @test
 * @summary Verify that ParserSAX.setProperty always throws UnsupportedOperationException
 * @run main/othervm --add-opens java.base/jdk.internal.util.xml.impl=ALL-UNNAMED ParserSAXWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ParserSAXWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("jdk.internal.util.xml.impl.ParserSAX");
        Constructor<?> ctor = cls.getConstructor();
        ctor.setAccessible(true);
        Object parser = ctor.newInstance();
        Method m = cls.getMethod("setProperty", String.class, Object.class);
        m.setAccessible(true);

        checkThrowsUOE(m, parser, "http://xml.org/sax/features/namespaces", Boolean.TRUE);
        checkThrowsUOE(m, parser, "http://xml.org/sax/features/namespace-prefixes", Boolean.FALSE);
        checkThrowsUOE(m, parser, null, null);
        checkThrowsUOE(m, parser, "http://xml.org/sax/properties/lexical-handler", new Object());
    }

    private static void checkThrowsUOE(Method m, Object parser, String name, Object value)
            throws IllegalAccessException {
        try {
            m.invoke(parser, name, value);
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof UnsupportedOperationException)) {
                throw new RuntimeException("Unexpected cause: " + cause, cause);
            }
        }
    }
}
