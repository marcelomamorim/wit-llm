/*
 * @test
 * @summary Verify that ParserSAX.setProperty always throws UnsupportedOperationException
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main/othervm --add-opens java.base/jdk.internal.util.xml.impl=ALL-UNNAMED ParserSAXWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ParserSAXWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("jdk.internal.util.xml.impl.ParserSAX");
        Constructor<?> ctor = c.getConstructor();
        ctor.setAccessible(true);
        Object parser = ctor.newInstance();

        Method setProperty = c.getMethod("setProperty", String.class, Object.class);
        setProperty.setAccessible(true);

        expectUnsupported(setProperty, parser, "http://xml.org/sax/properties/lexical-handler", new Object());
        expectUnsupported(setProperty, parser, "http://xml.org/sax/properties/declaration-handler", null);
        expectUnsupported(setProperty, parser, null, null);
        expectUnsupported(setProperty, parser, "", "value");
        expectUnsupported(setProperty, parser, "unknown-property", Integer.valueOf(1));
    }

    static void expectUnsupported(Method m, Object target, String name, Object value) throws Exception {
        try {
            m.invoke(target, name, value);
            throw new AssertionError("No exception thrown for name=" + name);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof UnsupportedOperationException)) {
                throw new AssertionError("Unexpected cause: " + e.getCause(), e);
            }
        }
    }
}
