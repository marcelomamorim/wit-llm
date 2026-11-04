/* @test    @summary Test for Signature.getParameters() method    @run main SignatureWitupTest */

import java.security.*;
import static org.junit.Assert.*;

public class SignatureWitupTest {

    public static void main(String[] args) throws Exception {
        testGetParametersReturnsNullWhenUninitialized();
        testGetParametersAfterInitVerify();
        testGetParametersAfterInitSign();
    }

    private static void testGetParametersReturnsNullWhenUninitialized() {
        Signature sig = Signature.getInstance("SHA256withRSA");
        AlgorithmParameters params = sig.getParameters();
        assertNull("Expected null when getParameters() called before init", params);
    }

    private static void testGetParametersAfterInitVerify() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        sig.initVerify(kp.getPublic());
        AlgorithmParameters params = sig.getParameters();
        assertNull("Expected null after initVerify", params);
    }

    private static void testGetParametersAfterInitSign() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        sig.initSign(kp.getPrivate());
        AlgorithmParameters params = sig.getParameters();
        assertNull("Expected null after initSign", params);
    }
}
