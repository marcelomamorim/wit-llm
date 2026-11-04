/*
 * @test
 * @summary Verifies that Signature.getParameters() delegates to engineGetParameters() without throwing for a standard algorithm and returns a stable result
 * @run main SignatureWitupTest
 */

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public class SignatureWitupTest {

    public static void main(String[] args) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");

        AlgorithmParameters first = sig.getParameters();
        AlgorithmParameters second = sig.getParameters();

        if ((first == null) != (second == null)) {
            throw new RuntimeException("getParameters() returned inconsistent nullness across calls");
        }

        if (first != null && first.getAlgorithm() == null) {
            throw new RuntimeException("AlgorithmParameters returned with null algorithm");
        }

        try {
            Signature.getInstance("NoSuchAlg-WitupTest").getParameters();
            throw new RuntimeException("Expected NoSuchAlgorithmException for unknown algorithm");
        } catch (NoSuchAlgorithmException expected) {
        }
    }
}
