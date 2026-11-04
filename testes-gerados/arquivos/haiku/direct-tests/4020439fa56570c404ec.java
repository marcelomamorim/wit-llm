/*
 * @test
 * @summary Verifies that the public no-argument constructor of MethodType.ConcurrentWeakInternSet creates usable, distinct instances
 * @modules java.base/java.lang.invoke
 * @run main/othervm --add-opens java.base/java.lang.invoke=ALL-UNNAMED MethodTypeWitupTest
 */

import java.lang.reflect.Constructor;

public class MethodTypeWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.invoke.MethodType$ConcurrentWeakInternSet");
        Constructor<?> ctor = c.getConstructor();
        ctor.setAccessible(true);
        Object a = ctor.newInstance();
        Object b = ctor.newInstance();
        if (a == null || b == null) {
            throw new RuntimeException("constructor returned null");
        }
        if (a.getClass() != c) {
            throw new RuntimeException("unexpected instance class: " + a.getClass());
        }
        if (a == b) {
            throw new RuntimeException("constructor returned the same instance twice");
        }
    }
}
