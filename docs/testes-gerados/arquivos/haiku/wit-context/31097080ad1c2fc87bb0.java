/*
 * @test
 * @summary Verifies that ParserSAX.getFeature(String) currently throws UnsupportedOperationException
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

        Method m = c.getDeclaredMethod("getFeature", String.class);
        m.setAccessible(true);

        try {
            m.invoke(parser, "http://xml.org/sax/features/namespaces");
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof UnsupportedOperationException)) {
                throw new RuntimeException("Unexpected cause for getFeature", e);
            }
        }
    }
}
