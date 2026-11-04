/*
 * @test
 * @summary Verify KeyChecker.init(boolean) rejects forward checking and accepts reverse checking
 * @modules java.base/sun.security.provider.certpath
 * @run main/othervm --add-opens java.base/sun.security.provider.certpath=ALL-UNNAMED KeyCheckerWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertSelector;

public class KeyCheckerWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.security.provider.certpath.KeyChecker");
        Constructor<?> ctor = c.getDeclaredConstructor(int.class, CertSelector.class);
        ctor.setAccessible(true);
        Object checker = ctor.newInstance(2, null);

        Method init = c.getDeclaredMethod("init", boolean.class);
        init.setAccessible(true);

        testForwardRejected(init, checker);
        testReverseAccepted(init, checker);
    }

    private static void testForwardRejected(Method init, Object checker) throws Exception {
        try {
            init.invoke(checker, true);
            throw new RuntimeException("expected CertPathValidatorException for forward=true");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof CertPathValidatorException)) {
                throw new RuntimeException("unexpected cause: " + cause, cause);
            }
            if (!"forward checking not supported".equals(cause.getMessage())) {
                throw new RuntimeException("unexpected message: " + cause.getMessage());
            }
        }
    }

    private static void testReverseAccepted(Method init, Object checker) throws Exception {
        try {
            init.invoke(checker, false);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("init(false) should not throw", e.getCause());
        }
    }
}
