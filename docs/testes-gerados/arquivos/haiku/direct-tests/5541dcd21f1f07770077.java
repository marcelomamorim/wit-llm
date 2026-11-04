/*
 * @test
 * @summary Verify that ParserSAX.parse(InputSource) rejects a null input with IllegalArgumentException
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main/othervm --add-opens java.base/jdk.internal.util.xml.impl=ALL-UNNAMED ParserSAXWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ParserSAXWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("jdk.internal.util.xml.impl.ParserSAX");
        Class<?> inputSourceCls = Class.forName("jdk.internal.org.xml.sax.InputSource");

        Constructor<?> ctor = cls.getConstructor();
        ctor.setAccessible(true);
        Object parser = ctor.newInstance();

        Method parse = cls.getMethod("parse", inputSourceCls);
        parse.setAccessible(true);

        try {
            parse.invoke(parser, new Object[] { null });
            throw new RuntimeException("Expected IllegalArgumentException for null InputSource");
        } catch (InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("Unexpected cause: " + cause, cause);
            }
        }
    }
}
