/*
 * @test
 * @summary Verifies that the protection parameter returned by a KeyStore.Builder created from a File is the same object that was supplied
 * @run main KeyStoreWitupTest
 */

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyStore;

public class KeyStoreWitupTest {

    public static void main(String[] args) throws Exception {
        File file = File.createTempFile("keystore-witup", ".p12");
        file.deleteOnExit();

        KeyStore.PasswordProtection first = new KeyStore.PasswordProtection("first".toCharArray());
        KeyStore.PasswordProtection second = new KeyStore.PasswordProtection("second".toCharArray());

        KeyStore.Builder builder = KeyStore.Builder.newInstance(file, first);
        Object r1 = invokeGetProtectionParameter(builder);
        if (r1 != first) {
            throw new RuntimeException("getProtectionParameter() did not return the supplied protection parameter");
        }

        Object r2 = invokeGetProtectionParameter(builder);
        if (r2 != first) {
            throw new RuntimeException("getProtectionParameter() is not stable across calls");
        }

        KeyStore.Builder other = KeyStore.Builder.newInstance(file, second);
        Object r3 = invokeGetProtectionParameter(other);
        if (r3 != second) {
            throw new RuntimeException("getProtectionParameter() returned a parameter from another builder");
        }
        if (r3 == r1) {
            throw new RuntimeException("Distinct builders share the same protection parameter");
        }
    }

    private static Object invokeGetProtectionParameter(KeyStore.Builder builder) throws Exception {
        Method m = builder.getClass().getDeclaredMethod("getProtectionParameter");
        m.setAccessible(true);
        try {
            return m.invoke(builder);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            throw new RuntimeException(cause);
        }
    }
}
