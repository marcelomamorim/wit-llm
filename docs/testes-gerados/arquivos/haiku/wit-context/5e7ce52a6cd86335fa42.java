/*
 * @test
 * @summary Checks Signature.update(ByteBuffer) state and null-argument behavior on an uninitialized Signature
 * @run main SignatureWitupTest
 */

import java.nio.ByteBuffer;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {

    public static void main(String[] args) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");

        ByteBuffer buf = ByteBuffer.wrap(new byte[] {1, 2, 3});
        expectSignatureException(sig, buf);
        if (buf.remaining() != 3) {
            throw new AssertionError("buffer must not be consumed on failed update");
        }

        expectSignatureException(sig, null);
    }

    private static void expectSignatureException(Signature sig, ByteBuffer buf) {
        try {
            sig.update(buf);
        } catch (SignatureException e) {
            return;
        }
        throw new AssertionError("expected SignatureException for uninitialized Signature");
    }
}
