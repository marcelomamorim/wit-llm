/*
 * @test
 * @summary Structural check of the public JarVerifier(byte[]) constructor via reflection; the class is package-private so behavioral calls are not reachable from the unnamed package
 * @run main JarVerifierWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class JarVerifierWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.util.jar.JarVerifier");

        if (Modifier.isPublic(c.getModifiers())) {
            throw new RuntimeException("JarVerifier is expected to be package-private");
        }

        Constructor<?> ctor = c.getConstructor(byte[].class);

        if (!Modifier.isPublic(ctor.getModifiers())) {
            throw new RuntimeException("JarVerifier(byte[]) is expected to be public");
        }

        if (ctor.getParameterCount() != 1) {
            throw new RuntimeException("unexpected parameter count: " + ctor.getParameterCount());
        }

        if (ctor.getParameterTypes()[0] != byte[].class) {
            throw new RuntimeException("unexpected parameter type: " + ctor.getParameterTypes()[0]);
        }
    }
}
