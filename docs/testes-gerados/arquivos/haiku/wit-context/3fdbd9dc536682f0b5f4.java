/*
 * @test
 * @summary Verifies that ExplodedSystemModules.moduleReads() unconditionally throws InternalError
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
        Object instance = ctor.newInstance();
        Method m = c.getDeclaredMethod("moduleReads");
        m.setAccessible(true);
        try {
            Object r = m.invoke(instance);
            throw new RuntimeException("expected InternalError but method returned: " + r);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof InternalError)) {
                throw new RuntimeException("unexpected cause", cause);
            }
        }
    }
}
