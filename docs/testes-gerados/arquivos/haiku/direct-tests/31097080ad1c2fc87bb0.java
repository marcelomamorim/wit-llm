/*
 * @test
 * @summary Verify that ParserSAX.getFeature always throws UnsupportedOperationException
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

        Method m = cls.getMethod("getFeature", String.class);
        m.setAccessible(true);

        String[] names = {
            "http://xml.org/sax/features/namespaces",
            "http://xml.org/sax/features/namespace-prefixes",
            "unknown-feature",
            "",
            null
        };
        for (String name : names) {
            expectUnsupported(m, parser, name);
        }

        System.out.println("PASS");
    }

    static void expectUnsupported(Method m, Object target, String name) throws Exception {
        try {
            Object result = m.invoke(target, name);
            throw new RuntimeException("Expected UnsupportedOperationException for "
                    + name + " but got result " + result);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof UnsupportedOperationException)) {
                throw new RuntimeException("Unexpected cause for " + name, cause);
            }
        }
    }
}
