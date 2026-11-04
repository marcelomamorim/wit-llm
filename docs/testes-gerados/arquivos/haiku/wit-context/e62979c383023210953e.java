/*
 * @test
 * @summary Verifies that Signer.setKeyPair stores the public and private keys observable through getPublicKey and getPrivateKey
 * @run main SignerWitupTest
 */

import java.security.Identity;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signer;

@SuppressWarnings({"deprecation", "removal"})
public class SignerWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair pair = kpg.generateKeyPair();
        PublicKey pub = pair.getPublic();
        PrivateKey priv = pair.getPrivate();

        Signer signer = new Signer("witup-signer") { };
        signer.setKeyPair(pair);

        if (!pub.equals(signer.getPublicKey())) {
            throw new RuntimeException("public key not stored by setKeyPair");
        }
        if (!priv.equals(signer.getPrivateKey())) {
            throw new RuntimeException("private key not stored by setKeyPair");
        }

        KeyPair pair2 = kpg.generateKeyPair();
        signer.setKeyPair(pair2);
        if (!pair2.getPublic().equals(signer.getPublicKey())) {
            throw new RuntimeException("public key not replaced by second setKeyPair");
        }
        if (!pair2.getPrivate().equals(signer.getPrivateKey())) {
            throw new RuntimeException("private key not replaced by second setKeyPair");
        }
    }
}
