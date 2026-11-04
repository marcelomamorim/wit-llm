/* @test    @summary Test for Identity.setPublicKey(PublicKey) method behavior.    @run main IdentityWitupTest */

import java.security.*;
import java.security.cert.Certificate;

public class IdentityWitupTest {

    public static void main(String[] args) throws Exception {
        testSetPublicKeyWithNull();
        testSetPublicKeyWithValidKey();
    }

    private static void testSetPublicKeyWithNull() {
        Identity identity = new Identity("TestIdentity", null) {
            @Override
            public String getName() { return null; }
            @Override
            public String toString() { return "TestIdentity"; }
        };
        try {
            identity.setPublicKey(null);
            System.out.println("PASS: setPublicKey(null) did not throw");
        } catch (KeyManagementException e) {
            System.out.println("FAIL: setPublicKey(null) threw KeyManagementException");
        } catch (Exception e) {
            System.out.println("FAIL: setPublicKey(null) threw unexpected exception: " + e);
        }
    }

    private static void testSetPublicKeyWithValidKey() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey pubKey = kp.getPublic();
        Identity identity = new Identity("TestIdentity", null) {
            @Override
            public String getName() { return null; }
            @Override
            public String toString() { return "TestIdentity"; }
        };
        try {
            identity.setPublicKey(pubKey);
            if (identity.publicKey == pubKey) {
                System.out.println("PASS: setPublicKey with valid key");
            } else {
                System.out.println("FAIL: setPublicKey with valid key did not set publicKey");
            }
        } catch (Exception e) {
            System.out.println("FAIL: setPublicKey with valid key threw exception: " + e);
        }
    }
}
