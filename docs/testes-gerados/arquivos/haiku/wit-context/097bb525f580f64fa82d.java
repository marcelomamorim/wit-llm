/*
 * @test
 * @summary Checks init(boolean) of the package-private ConstraintsChecker, including the forward checking rejection
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

        init.invoke(checker, Boolean.FALSE);

        boolean rejected = false;
        try {
            init.invoke(checker, Boolean.TRUE);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof CertPathValidatorException)) {
                throw new AssertionError("unexpected cause: " + cause, cause);
            }
            if (!"forward checking not supported".equals(cause.getMessage())) {
                throw new AssertionError("unexpected message: " + cause.getMessage());
            }
            rejected = true;
        }
        if (!rejected) {
            throw new AssertionError("init(true) did not throw CertPathValidatorException");
        }
    }
}
