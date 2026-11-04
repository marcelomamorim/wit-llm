/*
 * @test
 * @summary GeneralName(GeneralNameInterface) must reject null with NullPointerException and the documented message
 * @modules java.base/sun.security.x509
 * @run main GeneralNameWitupTest
 */

import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;

public class GeneralNameWitupTest {

    public static void main(String[] args) throws Exception {
        checkNullRejected();
        checkNullVariableRejected();
    }

    private static void checkNullRejected() {
        boolean thrown = false;
        String msg = null;
        try {
            new GeneralName((GeneralNameInterface) null);
        } catch (NullPointerException e) {
            thrown = true;
            msg = e.getMessage();
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null literal cast");
        }
        if (!"GeneralName must not be null".equals(msg)) {
            throw new AssertionError("unexpected message: " + msg);
        }
    }

    private static void checkNullVariableRejected() {
        GeneralNameInterface nullName = null;
        boolean thrown = false;
        String msg = null;
        try {
            new GeneralName(nullName);
        } catch (NullPointerException e) {
            thrown = true;
            msg = e.getMessage();
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null variable");
        }
        if (!"GeneralName must not be null".equals(msg)) {
            throw new AssertionError("unexpected message: " + msg);
        }
    }
}
