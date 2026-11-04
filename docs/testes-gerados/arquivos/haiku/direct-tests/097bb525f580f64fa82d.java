/*
 * @test
 * @summary Verifies ConstraintsChecker.init(boolean) observable behavior via reflection
 * @run main/othervm --add-opens java.base/sun.security.provider.certpath=ALL-UNNAMED ConstraintsCheckerWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.CertPathValidatorException;

public class ConstraintsCheckerWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("sun.security.provider.certpath.ConstraintsChecker");
        Constructor<?> ctor = cls.getDeclaredConstructor(int.class);
        ctor.setAccessible(true);
        Object checker = ctor.newInstance(3);

        Method init = cls.getDeclaredMethod("init", boolean.class);
        init.setAccessible(true);

        init.invoke(checker, false);
        init.invoke(checker, false);

        boolean thrown = false;
        try {
            init.invoke(checker, true);
        } catch (InvocationTargetException e) {
            thrown = true;
            Throwable cause = e.getCause();
            if (!(cause instanceof CertPathValidatorException)) {
                throw new RuntimeException("unexpected cause: " + cause, cause);
            }
            if (!"forward checking not supported".equals(cause.getMessage())) {
                throw new RuntimeException("unexpected message: " + cause.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("expected CertPathValidatorException for forward=true");
        }

        init.invoke(checker, false);
    }
}
