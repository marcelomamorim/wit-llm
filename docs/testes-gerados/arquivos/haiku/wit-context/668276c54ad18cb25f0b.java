/*
 * @test
 * @summary Verify that ParserSAX.setEntityResolver rejects null with NullPointerException and accepts a non-null resolver
 * @run main/othervm --add-opens java.base/jdk.internal.util.xml.impl=ALL-UNNAMED ParserSAXWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ParserSAXWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> parserCls = Class.forName("jdk.internal.util.xml.impl.ParserSAX");
        Class<?> resolverCls = Class.forName("jdk.internal.org.xml.sax.EntityResolver");

        Constructor<?> ctor = parserCls.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object parser = ctor.newInstance();

        Method setter = parserCls.getMethod("setEntityResolver", resolverCls);
        setter.setAccessible(true);

        try {
            setter.invoke(parser, (Object) null);
            throw new AssertionError("expected NullPointerException for null resolver");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof NullPointerException)) {
                throw new AssertionError("unexpected cause: " + e.getCause(), e);
            }
        }

        Object resolver = Proxy.newProxyInstance(
                ParserSAXWitupTest.class.getClassLoader(),
                new Class<?>[] { resolverCls },
                (proxy, method, methodArgs) -> null);
        setter.invoke(parser, resolver);
    }
}
