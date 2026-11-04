/*
 * @test
 * @summary Verifies KeyChecker.getSupportedExtensions returns the cached, unmodifiable set of supported extension OIDs
 * @run main/othervm --add-opens java.base/sun.security.provider.certpath=ALL-UNNAMED KeyCheckerWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.CertSelector;
import java.util.Set;

public class KeyCheckerWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.security.provider.certpath.KeyChecker");
        Constructor<?> ctor = c.getDeclaredConstructor(int.class, CertSelector.class);
        ctor.setAccessible(true);
        Object checker = ctor.newInstance(0, null);

        Method m = c.getDeclaredMethod("getSupportedExtensions");
        m.setAccessible(true);

        Set<String> first = invoke(m, checker);

        check(first.size() == 3, "expected 3 supported extensions but got " + first.size());
        check(first.contains("2.5.29.15"), "missing KeyUsage OID");
        check(first.contains("2.5.29.37"), "missing ExtendedKeyUsage OID");
        check(first.contains("2.5.29.17"), "missing SubjectAlternativeName OID");

        Set<String> second = invoke(m, checker);
        check(first == second, "expected cached set instance on repeated calls");

        boolean rejected = false;
        try {
            first.add("1.2.3.4");
        } catch (UnsupportedOperationException e) {
            rejected = true;
        }
        check(rejected, "returned set must be unmodifiable");
        check(first.size() == 3, "set size changed after failed add");
    }

    @SuppressWarnings("unchecked")
    private static Set<String> invoke(Method m, Object target) throws Exception {
        try {
            return (Set<String>) m.invoke(target);
        } catch (InvocationTargetException e) {
            throw new AssertionError("getSupportedExtensions threw: " + e.getCause(), e.getCause());
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
