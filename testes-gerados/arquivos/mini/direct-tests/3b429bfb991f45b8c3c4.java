/*
 * @test
 * @summary Test constructor of MGF1ParameterSpec for valid and null input
 * @run main MGF1ParameterSpecWitupTest
 */

import java.security.spec.MGF1ParameterSpec;

public class MGF1ParameterSpecWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testNullConstructor();
    }

    private static void testValidConstructor() {
        String[] algorithms = {
            "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512", "SHA-512/224", "SHA-512/256"
        };
        for (String alg : algorithms) {
            MGF1ParameterSpec spec = new MGF1ParameterSpec(alg);
            if (!alg.equals(spec.getDigestAlgorithm())) {
                throw new RuntimeException("Expected digest algorithm '" + alg + "' but got '" + spec.getDigestAlgorithm() + "'");
            }
        }
    }

    private static void testNullConstructor() {
        try {
            new MGF1ParameterSpec(null);
            throw new RuntimeException("Expected NullPointerException for null mdName");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("digest algorithm is null")) {
                throw new RuntimeException("Unexpected NullPointerException message: " + msg);
            }
        }
    }
}
