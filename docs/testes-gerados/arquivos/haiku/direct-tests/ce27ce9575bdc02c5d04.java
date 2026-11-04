/*
 * @test
 * @summary Verifies that Signature.getParameters() delegates to engineGetParameters() and returns its result
 * @run main SignatureWitupTest
 */

import java.security.AlgorithmParameters;
import java.security.Signature;
import java.security.SignatureException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SignatureWitupTest {

    static class RecordingSignature extends Signature {
        int calls = 0;
        final AlgorithmParameters toReturn;

        RecordingSignature(AlgorithmParameters toReturn) {
            super("WitupTest");
            this.toReturn = toReturn;
        }

        @Override
        protected AlgorithmParameters engineGetParameters() {
            calls++;
            return toReturn;
        }

        @Override
        protected void engineInitVerify(PublicKey publicKey) {
        }

        @Override
        protected void engineInitSign(PrivateKey privateKey) {
        }

        @Override
        protected void engineUpdate(byte b) {
        }

        @Override
        protected void engineUpdate(byte[] b, int off, int len) {
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

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testReturnsNullFromEngine() {
        RecordingSignature s = new RecordingSignature(null);
        AlgorithmParameters p = s.getParameters();
        check(p == null, "expected null from getParameters when engine returns null");
        check(s.calls == 1, "engineGetParameters should be invoked exactly once, was " + s.calls);
    }

    static void testDelegationIsRepeatable() {
        RecordingSignature s = new RecordingSignature(null);
        s.getParameters();
        s.getParameters();
        check(s.calls == 2, "each getParameters call should delegate, calls=" + s.calls);
    }

    static void testStandardAlgorithmDoesNotThrow() throws Exception {
        Signature s = Signature.getInstance("SHA256withRSA");
        AlgorithmParameters p = s.getParameters();
        check(p == null || p.getAlgorithm() != null, "unexpected parameters state");
    }

    public static void main(String[] args) throws Exception {
        testReturnsNullFromEngine();
        testDelegationIsRepeatable();
        testStandardAlgorithmDoesNotThrow();
    }
}
