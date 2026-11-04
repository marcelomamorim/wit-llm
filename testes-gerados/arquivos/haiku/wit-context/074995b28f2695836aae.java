/*
 * @test
 * @summary Checks that PKCS8Key.encode() returns the stored PKCS#8 encoding as a defensive copy
 * @modules java.base/sun.security.pkcs
 * @run main PKCS8KeyWitupTest
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.util.Arrays;
import sun.security.pkcs.PKCS8Key;

public class PKCS8KeyWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        PrivateKey priv = kp.getPrivate();
        if (!(priv instanceof PKCS8Key)) {
            throw new RuntimeException("RSA private key is not a PKCS8Key: " + priv.getClass());
        }
        PKCS8Key key = (PKCS8Key) priv;

        byte[] expected = priv.getEncoded();
        byte[] enc = key.encode();
        if (!Arrays.equals(enc, expected)) {
            throw new RuntimeException("encode() differs from getEncoded()");
        }

        enc[0] ^= 0x01;
        byte[] enc2 = key.encode();
        if (!Arrays.equals(enc2, expected)) {
            throw new RuntimeException("encode() result was modified by caller mutation");
        }
        if (enc2 == enc) {
            throw new RuntimeException("encode() must return a fresh copy");
        }

        byte[] enc3 = key.encode();
        if (enc3 == enc2) {
            throw new RuntimeException("encode() must not return the same array instance");
        }
        if (!Arrays.equals(enc3, expected)) {
            throw new RuntimeException("repeated encode() differs from getEncoded()");
        }
    }
}
