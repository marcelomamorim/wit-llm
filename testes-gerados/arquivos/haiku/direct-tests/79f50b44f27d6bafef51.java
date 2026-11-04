/*
 * @test
 * @summary ExplodedSystemModules.moduleTargets must throw InternalError
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

        Method m = c.getDeclaredMethod("moduleTargets");
        m.setAccessible(true);

        boolean thrownInternalError = false;
        try {
            m.invoke(instance);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof InternalError) {
                thrownInternalError = true;
            } else {
                throw new RuntimeException("unexpected cause: " + e.getCause(), e);
            }
        }

        if (!thrownInternalError) {
            throw new RuntimeException("moduleTargets did not throw InternalError");
        }
    }
}
