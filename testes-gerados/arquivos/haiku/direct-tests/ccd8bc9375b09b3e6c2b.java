/*
 * @test
 * @summary Verifies the public signature of ProxyGenerator.getClass(String) returns short
 * @run main ProxyGeneratorWitupTest
 */

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ProxyGeneratorWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.reflect.ProxyGenerator");
        Method m = c.getDeclaredMethod("getClass", String.class);
        if (m.getReturnType() != short.class) {
            throw new AssertionError("Unexpected return type: " + m.getReturnType());
        }
        if (!Modifier.isPublic(m.getModifiers())) {
            throw new AssertionError("getClass(String) is not public");
        }
        if (Modifier.isStatic(m.getModifiers())) {
            throw new AssertionError("getClass(String) should not be static");
        }
    }
}
