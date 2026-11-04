/*
 * @test
 * @summary WITUP-derived checks for X509CertSelector.setBasicConstraints
 * @run main X509CertSelectorWitupTest
 */
import java.security.cert.X509CertSelector;

public class X509CertSelectorWitupTest {
    public static void main(String[] args) throws Exception {
        X509CertSelector sel = new X509CertSelector();

        if (sel.getBasicConstraints() != -1) {
            throw new AssertionError("default basic constraints expected -1, got " + sel.getBasicConstraints());
        }

        sel.setBasicConstraints(-2);
        if (sel.getBasicConstraints() != -2) {
            throw new AssertionError("expected -2 after setBasicConstraints(-2), got " + sel.getBasicConstraints());
        }

        sel.setBasicConstraints(7);
        if (sel.getBasicConstraints() != 7) {
            throw new AssertionError("expected 7 after setBasicConstraints(7), got " + sel.getBasicConstraints());
        }

        boolean thrown = false;
        try {
            sel.setBasicConstraints(-3);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("setBasicConstraints(-3) must throw IllegalArgumentException");
        }

        if (sel.getBasicConstraints() != 7) {
            throw new AssertionError("value must be unchanged after rejected input, got " + sel.getBasicConstraints());
        }
    }
}
