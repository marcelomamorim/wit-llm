/*
 * @test
 * @summary Verifies the public constructor of the package-private JarVerifier class via reflection
 * @run main/othervm --add-opens java.base/java.util.jar=ALL-UNNAMED JarVerifierWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class JarVerifierWitupTest {

    static Class<?> cls;
    static Constructor<?> ctor;

    static Object newVerifier(byte[] raw) throws Exception {
        try {
            return ctor.newInstance((Object) raw);
        } catch (InvocationTargetException e) {
            throw new Exception("constructor threw: " + e.getCause(), e.getCause());
        }
    }

    public static void main(String[] args) throws Exception {
        cls = Class.forName("java.util.jar.JarVerifier");
        ctor = cls.getDeclaredConstructor(byte[].class);
        ctor.setAccessible(true);

        Object v1 = newVerifier(new byte[] {1, 2, 3});
        if (v1 == null || !cls.isInstance(v1)) {
            throw new RuntimeException("constructor with bytes did not create a JarVerifier");
        }

        Object v2 = newVerifier(new byte[0]);
        if (v2 == null || !cls.isInstance(v2)) {
            throw new RuntimeException("constructor with empty array did not create a JarVerifier");
        }

        Object v3 = newVerifier(null);
        if (v3 == null || !cls.isInstance(v3)) {
            throw new RuntimeException("constructor with null did not create a JarVerifier");
        }

        if (v1 == v2 || v2 == v3 || v1 == v3) {
            throw new RuntimeException("constructor returned a shared instance");
        }
    }
}
