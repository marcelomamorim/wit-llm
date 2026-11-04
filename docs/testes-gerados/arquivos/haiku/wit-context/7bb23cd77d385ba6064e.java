/*
 * @test
 * @summary Checks the public observable behavior of KeyChecker.getSupportedExtensions via reflection
 * @modules java.base/sun.security.provider.certpath
 * @run main KeyCheckerWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.CertSelector;
import java.util.Set;

public class KeyCheckerWitupTest {

    private static final String KEY_USAGE_OID = "2.5.29.15";
    private static final String EXTENDED_KEY_USAGE_OID = "2.5.29.37";
    private static final String SUBJECT_ALT_NAME_OID = "2.5.29.17";

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("sun.security.provider.certpath.KeyChecker");
        Constructor<?> ctor = cls.getDeclaredConstructor(int.class, CertSelector.class);
        ctor.setAccessible(true);
        Object checker = ctor.newInstance(3, null);

        Method m = cls.getDeclaredMethod("getSupportedExtensions");
        m.setAccessible(true);

        Set<?> first = invoke(m, checker);
        if (first == null) {
            throw new AssertionError("getSupportedExtensions returned null");
        }
        if (first.size() != 3) {
            throw new AssertionError("Expected 3 extensions but got " + first.size());
        }
        if (!first.contains(KEY_USAGE_OID)) {
            throw new AssertionError("Missing keyUsage OID");
        }
        if (!first.contains(EXTENDED_KEY_USAGE_OID)) {
            throw new AssertionError("Missing extendedKeyUsage OID");
        }
        if (!first.contains(SUBJECT_ALT_NAME_OID)) {
            throw new AssertionError("Missing subjectAltName OID");
        }

        Set<?> second = invoke(m, checker);
        if (first != second) {
            throw new AssertionError("Expected cached set instance on repeated calls");
        }

        @SuppressWarnings("unchecked")
        Set<Object> raw = (Set<Object>) first;
        boolean rejected = false;
        try {
            raw.add("1.2.3.4");
        } catch (UnsupportedOperationException e) {
            rejected = true;
        }
        if (!rejected) {
            throw new AssertionError("Returned set must be unmodifiable");
        }
        if (first.size() != 3) {
            throw new AssertionError("Set size changed after rejected modification");
        }
    }

    private static Set<?> invoke(Method m, Object target) throws Exception {
        try {
            return (Set<?>) m.invoke(target);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            throw new AssertionError(cause);
        }
    }
}
