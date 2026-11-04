/*
 * @test
 * @summary Verify Signature.update(byte) rejects uninitialized objects and forwards bytes to the engine when initialized
 * @run main SignatureWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {

    static final class RecordingSignature extends Signature {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        RecordingSignature() {
            super("TEST");
        }

        @Override
        protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        }

        @Override
        protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        }

        @Override
        protected void engineUpdate(byte b) throws SignatureException {
            out.write(b);
        }

        @Override
        protected void engineUpdate(byte[] b, int off, int len) throws SignatureException {
            out.write(b, off, len);
        }

        @Override
        protected byte[] engineSign() throws SignatureException {
            return new byte[0];
        }

        @Override
        protected boolean engineVerify(byte[] sigBytes) throws SignatureException {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        testUninitializedUpdateThrows();
        testUninitializedRecordingStaysEmpty();
        testVerifyStateForwardsBytes();
        testSignStateForwardsBytes();
    }

    static void testUninitializedUpdateThrows() throws Exception {
        Signature s = Signature.getInstance("SHA256withRSA");
        try {
            s.update((byte) 1);
            throw new RuntimeException("Expected SignatureException for uninitialized update");
        } catch (SignatureException expected) {
            // expected
        }
    }

    static void testUninitializedRecordingStaysEmpty() throws Exception {
        RecordingSignature r = new RecordingSignature();
        try {
            r.update((byte) 0x41);
            throw new RuntimeException("Expected SignatureException");
        } catch (SignatureException expected) {
            // expected
        }
        if (r.out.size() != 0) {
            throw new RuntimeException("Engine must not receive bytes when uninitialized");
        }
    }

    static void testVerifyStateForwardsBytes() throws Exception {
        RecordingSignature r = new RecordingSignature();
        r.initVerify((PublicKey) null);
        r.update((byte) 0x41);
        r.update((byte) 0x42);
        byte[] got = r.out.toByteArray();
        if (got.length != 2 || got[0] != 0x41 || got[1] != 0x42) {
            throw new RuntimeException("Unexpected bytes forwarded in verify state");
        }
    }

    static void testSignStateForwardsBytes() throws Exception {
        RecordingSignature r = new RecordingSignature();
        r.initSign((PrivateKey) null);
        r.update((byte) 0x7F);
        byte[] got = r.out.toByteArray();
        if (got.length != 1 || got[0] != 0x7F) {
            throw new RuntimeException("Unexpected bytes forwarded in sign state");
        }
    }
}
