/*
 * @test
 * @summary Verifies DEFAULT OAEPParameterSpec values (reached through the private no-arg constructor) and NPE behavior of the public constructor
 * @run main OAEPParameterSpecWitupTest
 */

import java.security.spec.MGF1ParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class OAEPParameterSpecWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static void expectNpe(Runnable action, String message) {
        try {
            action.run();
            check(false, message + " (no exception thrown)");
        } catch (NullPointerException expected) {
            // expected
        } catch (RuntimeException other) {
            check(false, message + " (unexpected " + other + ")");
        }
    }

    public static void main(String[] args) throws Exception {
        OAEPParameterSpec d = OAEPParameterSpec.DEFAULT;
        check(d != null, "DEFAULT must not be null");
        check("SHA-1".equals(d.getDigestAlgorithm()), "DEFAULT digest must be SHA-1");
        check("MGF1".equals(d.getMGFAlgorithm()), "DEFAULT MGF must be MGF1");
        check(MGF1ParameterSpec.SHA1.equals(d.getMGFParameters()), "DEFAULT MGF parameters must be MGF1ParameterSpec.SHA1");
        check(PSource.PSpecified.DEFAULT.equals(d.getPSource()), "DEFAULT PSource must be PSpecified.DEFAULT");

        expectNpe(() -> {
            new OAEPParameterSpec(null, "MGF1", MGF1ParameterSpec.SHA1, PSource.PSpecified.DEFAULT);
        }, "null mdName must throw NPE");

        expectNpe(() -> {
            new OAEPParameterSpec("SHA-256", null, MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
        }, "null mgfName must throw NPE");

        expectNpe(() -> {
            new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, null);
        }, "null pSrc must throw NPE");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
