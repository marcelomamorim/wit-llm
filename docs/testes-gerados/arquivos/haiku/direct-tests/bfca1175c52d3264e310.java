/*
 * @test
 * @summary Verify that ExplodedSystemModules.moduleResolutions() throws InternalError
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

        Method m = c.getDeclaredMethod("moduleResolutions");
        m.setAccessible(true);

        try {
            Object result = m.invoke(instance);
            throw new RuntimeException("Expected InvocationTargetException, got result: " + result);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause == null) {
                throw new RuntimeException("InvocationTargetException without cause");
            }
            if (!(cause instanceof InternalError)) {
                throw new RuntimeException("Expected InternalError but was " + cause);
            }
        }
    }
}
