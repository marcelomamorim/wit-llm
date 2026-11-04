/*
 * @test
 * @summary Verifies the default OAEPParameterSpec values created by the private no-arg constructor, observed through DEFAULT and public getters
 * @run main OAEPParameterSpecWitupTest
 */

import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.security.spec.MGF1ParameterSpec;

public class OAEPParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        OAEPParameterSpec d = OAEPParameterSpec.DEFAULT;
        check(d != null, "DEFAULT must not be null");
        check("SHA-1".equals(d.getDigestAlgorithm()),
              "default digest must be SHA-1 but was " + d.getDigestAlgorithm());
        check("MGF1".equals(d.getMGFAlgorithm()),
              "default MGF must be MGF1 but was " + d.getMGFAlgorithm());
        check(MGF1ParameterSpec.SHA1.equals(d.getMGFParameters()),
              "default MGF parameters must be MGF1ParameterSpec.SHA1");
        check(PSource.PSpecified.DEFAULT == d.getPSource(),
              "default PSource must be PSource.PSpecified.DEFAULT");

        OAEPParameterSpec c = new OAEPParameterSpec("SHA-256", "MGF1",
                MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
        check("SHA-256".equals(c.getDigestAlgorithm()),
              "custom digest must be SHA-256 but was " + c.getDigestAlgorithm());
        check(!"SHA-1".equals(c.getDigestAlgorithm()),
              "custom instance must not reflect the default digest");
        check("SHA-1".equals(OAEPParameterSpec.DEFAULT.getDigestAlgorithm()),
              "DEFAULT must remain unchanged after creating another instance");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
