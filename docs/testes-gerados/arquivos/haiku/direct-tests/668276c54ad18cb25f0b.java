/*
 * @test
 * @summary Verify that ParserSAX.setEntityResolver rejects null with NullPointerException and leaves the content handler unchanged
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main/othervm --add-opens java.base/jdk.internal.util.xml.impl=ALL-UNNAMED ParserSAXWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ParserSAXWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("jdk.internal.util.xml.impl.ParserSAX");
        Constructor<?> ctor = cls.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object parser = ctor.newInstance();

        Class<?> resolverType = Class.forName("jdk.internal.org.xml.sax.EntityResolver");
        Method setResolver = cls.getMethod("setEntityResolver", resolverType);
        Method getContentHandler = cls.getMethod("getContentHandler");

        for (int i = 0; i < 2; i++) {
            try {
                setResolver.invoke(parser, new Object[] { null });
                throw new AssertionError("expected NullPointerException on iteration " + i);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (!(cause instanceof NullPointerException)) {
                    throw new AssertionError("unexpected cause: " + cause, cause);
                }
            }
        }

        Object handler = getContentHandler.invoke(parser);
        if (handler != null) {
            throw new AssertionError("content handler should remain null, was: " + handler);
        }
    }
}
