/*
 * @test
 * @summary Verify X509CertSelector.setBasicConstraints accepts values >= -2, rejects values < -2, and keeps the previous value on rejection
 * @run main X509CertSelectorWitupTest
 */

import java.security.cert.X509CertSelector;

public class X509CertSelectorWitupTest {

    public static void main(String[] args) throws Exception {
        X509CertSelector s = new X509CertSelector();
        check(s.getBasicConstraints() == -1, "default basic constraints must be -1");

        s.setBasicConstraints(-2);
        check(s.getBasicConstraints() == -2, "-2 must be accepted");

        s.setBasicConstraints(-1);
        check(s.getBasicConstraints() == -1, "-1 must be accepted");

        s.setBasicConstraints(0);
        check(s.getBasicConstraints() == 0, "0 must be accepted");

        s.setBasicConstraints(7);
        check(s.getBasicConstraints() == 7, "7 must be stored");

        boolean thrown = false;
        try {
            s.setBasicConstraints(-3);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "-3 must throw IllegalArgumentException");
        check(s.getBasicConstraints() == 7, "value must be unchanged after rejected -3");

        thrown = false;
        try {
            s.setBasicConstraints(Integer.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "Integer.MIN_VALUE must throw IllegalArgumentException");
        check(s.getBasicConstraints() == 7, "value must be unchanged after rejected Integer.MIN_VALUE");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
