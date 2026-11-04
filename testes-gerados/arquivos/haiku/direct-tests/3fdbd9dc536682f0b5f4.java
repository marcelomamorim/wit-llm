/*
 * @test
 * @summary Verifies that ExplodedSystemModules.moduleReads() throws InternalError
 * @modules java.base/jdk.internal.module
 * @run main/othervm --add-opens java.base/jdk.internal.module=ALL-UNNAMED ExplodedSystemModulesWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExplodedSystemModulesWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("jdk.internal.module.ExplodedSystemModules");
        Constructor<?> ctor = c.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object obj = ctor.newInstance();
        Method m = c.getDeclaredMethod("moduleReads");
        m.setAccessible(true);
        try {
            m.invoke(obj);
            throw new RuntimeException("expected InvocationTargetException wrapping InternalError");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof InternalError)) {
                throw new RuntimeException("unexpected cause: " + e.getCause(), e.getCause());
            }
        }
    }
}
