/*
 * @test
 * @summary Verifies MGF1ParameterSpec constructor null handling and public constants
 * @run main MGF1ParameterSpecWitupTest
 */

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;

public class MGF1ParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        testNullDigestNameThrowsNPE();
        testValidDigestNamesAreAccepted();
        testPredefinedConstantsAreNotNull();
        System.out.println("PASSED");
    }

    static void testNullDigestNameThrowsNPE() {
        boolean thrown = false;
        try {
            new MGF1ParameterSpec(null);
        } catch (NullPointerException e) {
            thrown = true;
            if (!"digest algorithm is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NPE message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for null digest name");
        }
    }

    static void testValidDigestNamesAreAccepted() {
        MGF1ParameterSpec s1 = new MGF1ParameterSpec("SHA-1");
        MGF1ParameterSpec s2 = new MGF1ParameterSpec("SHA-256");
        MGF1ParameterSpec s3 = new MGF1ParameterSpec("SHA-512/256");
        if (s1 == null || s2 == null || s3 == null) {
            throw new RuntimeException("Constructor returned null instance");
        }
        if (s1 == s2) {
            throw new RuntimeException("Distinct constructions must yield distinct objects");
        }
    }

    static void testPredefinedConstantsAreNotNull() {
        AlgorithmParameterSpec[] specs = {
            MGF1ParameterSpec.SHA1,
            MGF1ParameterSpec.SHA224,
            MGF1ParameterSpec.SHA256,
            MGF1ParameterSpec.SHA384,
            MGF1ParameterSpec.SHA512,
            MGF1ParameterSpec.SHA512_224,
            MGF1ParameterSpec.SHA512_256
        };
        for (AlgorithmParameterSpec spec : specs) {
            if (spec == null) {
                throw new RuntimeException("Predefined MGF1ParameterSpec constant is null");
            }
        }
    }
}
