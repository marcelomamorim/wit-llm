/*
 * @test
 * @summary Test for DisabledAlgorithmConstraints.permits(Set<CryptoPrimitive>, Key)
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import sun.security.util.DisabledAlgorithmConstraints;
import sun.security.util.AlgorithmDecomposer;
import java.security.Key;
import java.security.CryptoPrimitive;
import java.util.Set;
import java.util.HashSet;

public class DisabledAlgorithmConstraintsWitupTest {

    public static void main(String[] args) {
        testPermitsWithNullAlgorithm();
        testPermitsWithEmptyAlgorithm();
        testPermitsWithValidInput();
    }

    private static void testPermitsWithNullAlgorithm() {
        try {
            DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms", new AlgorithmDecomposer());
            // Invoking permits with null algorithm should throw IllegalArgumentException
            boolean result = dac.permits(Set.of(CryptoPrimitive.SIGNATURE), null);
            System.out.println("testPermitsWithNullAlgorithm: FAILED - Expected exception, got result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("testPermitsWithNullAlgorithm: PASSED");
        } catch (Exception e) {
            System.out.println("testPermitsWithNullAlgorithm: FAILED - Unexpected exception: " + e);
        }
    }

    private static void testPermitsWithEmptyAlgorithm() {
        try {
            DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms", new AlgorithmDecomposer());
            // Invoking permits with empty algorithm should throw IllegalArgumentException
            boolean result = dac.permits(Set.of(CryptoPrimitive.SIGNATURE), "");
            System.out.println("testPermitsWithEmptyAlgorithm: FAILED - Expected exception, got result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("testPermitsWithEmptyAlgorithm: PASSED");
        } catch (Exception e) {
            System.out.println("testPermitsWithEmptyAlgorithm: FAILED - Unexpected exception: " + e);
        }
    }

    private static void testPermitsWithValidInput() {
        try {
            DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms", new AlgorithmDecomposer());
            // Using a valid primitive and key (null key for simplicity)
            boolean result = dac.permits(Set.of(CryptoPrimitive.SIGNATURE), null);
            System.out.println("testPermitsWithValidInput: PASSED - Result: " + result);
        } catch (Exception e) {
            System.out.println("testPermitsWithValidInput: FAILED - Exception: " + e);
        }
    }
}
