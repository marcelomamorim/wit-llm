/*
 * @test
 * @summary ParserSAX.getProperty currently throws UnsupportedOperationException for every name
 * @modules java.base/jdk.internal.util.xml.impl
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
        Method m = cls.getDeclaredMethod("getProperty", String.class);
        m.setAccessible(true);

        String[] names = {
            "http://xml.org/sax/properties/lexical-handler",
            "",
            "anything"
        };
        for (String name : names) {
            checkUnsupported(parser, m, name);
        }
        checkUnsupported(parser, m, null);
    }

    private static void checkUnsupported(Object parser, Method m, String name)
            throws Exception {
        try {
            Object result = m.invoke(parser, name);
            throw new AssertionError("expected exception for name=" + name
                    + " but got result=" + result);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof UnsupportedOperationException)) {
                throw new AssertionError("unexpected cause for name=" + name, cause);
            }
        }
    }
}
