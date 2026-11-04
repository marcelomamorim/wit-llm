/*
 * @test
 * @summary Checks observable behavior of Signature.update(byte[]) delegating to update(data, 0, data.length)
 * @run main SignatureWitupTest
 */

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Arrays;

public class SignatureWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        byte[] data = "hello world".getBytes(StandardCharsets.UTF_8);

        // update(byte[]) must behave like update(data, 0, data.length)
        Signature viaSingleArg = Signature.getInstance("SHA256withRSA");
        viaSingleArg.initSign(kp.getPrivate());
        viaSingleArg.update(data);
        byte[] sig1 = viaSingleArg.sign();

        Signature viaThreeArg = Signature.getInstance("SHA256withRSA");
        viaThreeArg.initSign(kp.getPrivate());
        viaThreeArg.update(data, 0, data.length);
        byte[] sig2 = viaThreeArg.sign();

        check(Arrays.equals(sig1, sig2), "update(byte[]) differs from update(byte[],int,int)");

        // The produced signature must verify with the public key
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(kp.getPublic());
        verifier.update(data);
        check(verifier.verify(sig1), "signature produced via update(byte[]) did not verify");

        // Split updates must equal a single update of the concatenated data
        Signature split = Signature.getInstance("SHA256withRSA");
        split.initSign(kp.getPrivate());
        split.update("hello ".getBytes(StandardCharsets.UTF_8));
        split.update("world".getBytes(StandardCharsets.UTF_8));
        byte[] sigSplit = split.sign();
        check(Arrays.equals(sig1, sigSplit), "split update(byte[]) calls differ from single call");

        // Empty array update must not change the signed content
        Signature emptyUpdate = Signature.getInstance("SHA256withRSA");
        emptyUpdate.initSign(kp.getPrivate());
        emptyUpdate.update(new byte[0]);
        byte[] sigEmptyUpdate = emptyUpdate.sign();

        Signature noUpdate = Signature.getInstance("SHA256withRSA");
        noUpdate.initSign(kp.getPrivate());
        byte[] sigNoUpdate = noUpdate.sign();
        check(Arrays.equals(sigEmptyUpdate, sigNoUpdate), "empty update changed the signed content");

        // Different content must not verify with the signature of the original data
        Signature tampered = Signature.getInstance("SHA256withRSA");
        tampered.initVerify(kp.getPublic());
        tampered.update("hello there".getBytes(StandardCharsets.UTF_8));
        check(!tampered.verify(sig1), "tampered data verified with original signature");

        // A null array is dereferenced by update(byte[]) through data.length
        Signature nullCase = Signature.getInstance("SHA256withRSA");
        nullCase.initSign(kp.getPrivate());
        boolean npe = false;
        try {
            nullCase.update((byte[]) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "update((byte[]) null) did not throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
