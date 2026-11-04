/*
 * @test
 * @summary Test java.security.Signature.getParameters() method
 * @run main SignatureWitupTest
 */

import java.security.Signature;
import java.security.AlgorithmParameters;

public class SignatureWitupTest {
    public static void main(String[] args) throws Exception {
        // Use a standard algorithm known to be supported
        Signature sig = Signature.getInstance("SHA256withRSA");
        AlgorithmParameters params = sig.getParameters();
        // The returned AlgorithmParameters may be null if the implementation does not use parameters
        // So test that the method returns either null or a non-null AlgorithmParameters instance
        if (params != null) {
            // If not null, verify that the returned object is an instance of AlgorithmParameters
            if (!(params instanceof AlgorithmParameters)) {
                throw new RuntimeException("getParameters() returned an object not of type AlgorithmParameters");
            }
        }
        // If null, this is acceptable behavior
        System.out.println("getParameters() test passed with " + (params == null ? "null" : "non-null") + " result.");
    }
}
