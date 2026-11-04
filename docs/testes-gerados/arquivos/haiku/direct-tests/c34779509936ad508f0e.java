/*
 * @test
 * @summary Verifies that the private default constructor of PSSParameterSpec throws and that public constructors and DEFAULT still behave correctly
 * @run main/othervm --add-opens java.base/java.security.spec=ALL-UNNAMED PSSParameterSpecWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.spec.PSSParameterSpec;

public class PSSParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        Constructor<PSSParameterSpec> c = PSSParameterSpec.class.getDeclaredConstructor();
        c.setAccessible(true);
        try {
            c.newInstance();
            throw new AssertionError("private constructor did not throw");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof RuntimeException)) {
                throw new AssertionError("unexpected cause: " + cause);
            }
            if (!"default constructor not allowed".equals(cause.getMessage())) {
                throw new AssertionError("unexpected message: " + cause.getMessage());
            }
        }

        PSSParameterSpec p = new PSSParameterSpec(20);
        if (p.getSaltLength() != 20) {
            throw new AssertionError("saltLen mismatch: " + p.getSaltLength());
        }

        if (PSSParameterSpec.DEFAULT.getSaltLength() != 20) {
            throw new AssertionError("DEFAULT saltLen mismatch");
        }
        if (PSSParameterSpec.DEFAULT.getTrailerField() != PSSParameterSpec.TRAILER_FIELD_BC) {
            throw new AssertionError("DEFAULT trailerField mismatch");
        }
    }
}
