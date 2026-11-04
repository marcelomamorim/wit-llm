/*
 * @test
 * @summary Basic instantiation test for OAEPParameterSpec private constructor
 * @run main OAEPParameterSpecWitupTest
 */

public class OAEPParameterSpecWitupTest {
    public static void main(String[] args) {
        // The private constructor is not accessible, so we test the DEFAULT instance
        javax.crypto.spec.OAEPParameterSpec spec = javax.crypto.spec.OAEPParameterSpec.DEFAULT;
        if (spec == null) {
            throw new RuntimeException("OAEPParameterSpec.DEFAULT must not be null");
        }
        // Check default values
        if (!"SHA-1".equals(spec.getDigestAlgorithm())) {
            throw new RuntimeException("Default digest algorithm should be SHA-1");
        }
        if (!"MGF1".equals(spec.getMGFAlgorithm())) {
            throw new RuntimeException("Default MGF algorithm should be MGF1");
        }
        if (spec.getMGFParameters() == null) {
            throw new RuntimeException("Default MGF parameters should not be null");
        }
        if (spec.getPSource() == null) {
            throw new RuntimeException("Default PSource should not be null");
        }
    }
}
