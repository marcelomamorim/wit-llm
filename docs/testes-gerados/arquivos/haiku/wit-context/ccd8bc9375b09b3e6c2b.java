/*
 * @test
 * @summary Exercises public Proxy API that reaches ProxyGenerator proxy class generation
 * @run main ProxyGeneratorWitupTest
 */
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyGeneratorWitupTest {
    public static void main(String[] args) throws Exception {
        ClassLoader loader = ProxyGeneratorWitupTest.class.getClassLoader();
        InvocationHandler handler = (p, m, a) -> null;

        Object proxy = Proxy.newProxyInstance(loader, new Class<?>[]{Runnable.class}, handler);
        check(Proxy.isProxyClass(proxy.getClass()), "generated class must be a proxy class");
        check(proxy instanceof Runnable, "proxy must implement requested interface");
        ((Runnable) proxy).run();

        Class<?> c1 = Proxy.getProxyClass(loader, Runnable.class);
        Class<?> c2 = Proxy.getProxyClass(loader, Runnable.class);
        check(c1 == c2, "same interface list must yield the same cached proxy class");
        check(Proxy.isProxyClass(c1), "getProxyClass must return a proxy class");
        check(c1.getSuperclass() == Proxy.class, "proxy class must extend java.lang.reflect.Proxy");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
