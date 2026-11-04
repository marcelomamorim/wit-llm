/*
 * @test
 * @summary Verifies that ParserSAX.parse(InputSource) rejects a null InputSource with IllegalArgumentException
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main ParserSAXWitupTest
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
        Class<?> inputSourceCls = Class.forName("jdk.internal.org.xml.sax.InputSource");
        Method parse = cls.getMethod("parse", inputSourceCls);
        parse.setAccessible(true);
        try {
            parse.invoke(parser, new Object[]{null});
            throw new RuntimeException("expected IllegalArgumentException was not thrown");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause: " + e.getCause(), e);
            }
        }
    }
}
