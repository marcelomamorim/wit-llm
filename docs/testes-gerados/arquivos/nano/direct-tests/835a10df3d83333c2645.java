/* @test    @summary Test setting a public key on Identity instance    @run main IdentityWitupTest */

import java.security.PublicKey;
import java.security.KeyManagementException;

public class IdentityWitupTest {

    public static void main(String[] args) {
        testSetPublicKeyWithValidKey();
        testSetPublicKeyWithNull();
    }

    static void testSetPublicKeyWithValidKey() {
        class DummyPublicKey implements PublicKey {
            @Override public String getAlgorithm() { return "RSA"; }
            @Override public String getFormat() { return "X.509"; }
            @Override public byte[] getEncoded() { return new byte[0]; }
        }
        try {
            java.security.Identity identity = new java.security.Identity() {
                @Override public String getName() { return "TestIdentity"; }
                @Override public java.security.Principal getPrincipal() { return null; }
            };
            PublicKey key = new DummyPublicKey();
            identity.setPublicKey(key);
            assert identity.publicKey == key : "PublicKey was not set correctly";
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    static void testSetPublicKeyWithNull() {
        class DummyPublicKey implements PublicKey {
            @Override public String getAlgorithm() { return "RSA"; }
            @Override public String getFormat() { return "X.509"; }
            @Override public byte[] getEncoded() { return new byte[0]; }
        }
        try {
            java.security.Identity identity = new java.security.Identity() {
                @Override public String getName() { return "TestIdentity"; }
                @Override public java.security.Principal getPrincipal() { return null; }
            };
            identity.setPublicKey(null);
            assert identity.publicKey == null : "PublicKey should be null after setting null";
        } catch (KeyManagementException e) {
            throw new RuntimeException(e); 
        }
    }
}
