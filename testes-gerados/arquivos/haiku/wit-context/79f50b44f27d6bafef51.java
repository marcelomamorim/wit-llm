/*
 * @test
 * @summary Verifies that ExplodedSystemModules.moduleTargets() throws InternalError
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
        Object target = ctor.newInstance();
        Method m = c.getDeclaredMethod("moduleTargets");
        m.setAccessible(true);
        try {
            m.invoke(target);
            throw new AssertionError("expected InternalError from moduleTargets()");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof InternalError)) {
                throw new AssertionError("unexpected cause: " + cause, cause);
            }
        }
    }
}
