/*
 * @test
 * @summary Verifies NTLM Server constructor null-domain rejection and type2 message layout
 * @modules java.base/com.sun.security.ntlm
 * @run main ServerWitupTest
 */

import java.util.Arrays;
import com.sun.security.ntlm.Server;
import com.sun.security.ntlm.NTLMException;

public class ServerWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static Server newServer(String version, String domain) throws NTLMException {
        return new Server(version, domain) {
            public char[] getPassword(String ntdomain, String username) {
                return null;
            }
        };
    }

    static void testNullDomainRejected() {
        boolean thrown = false;
        try {
            newServer(null, null);
        } catch (NTLMException e) {
            thrown = true;
        }
        check(thrown, "null domain must throw NTLMException");
    }

    static void testNullNonceRejected() throws Exception {
        Server s = newServer(null, "REALM");
        boolean thrown = false;
        try {
            s.type2(null, null);
        } catch (NTLMException e) {
            thrown = true;
        }
        check(thrown, "null nonce must throw NTLMException");
    }

    static void testType2NullType1Accepted() throws Exception {
        Server s = newServer(null, "REALM");
        byte[] nonce = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] out = s.type2(null, nonce);
        check(out != null, "type2 must return a message");
        check(out.length >= 32, "type2 message too short: " + out.length);
    }

    static void testType2NonceAndFlagsLayout() throws Exception {
        Server s = newServer(null, "REALM");
        byte[] nonce = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] out = s.type2(new byte[32], nonce);
        check(out.length >= 32, "type2 message too short");
        byte[] embedded = Arrays.copyOfRange(out, 24, 32);
        check(Arrays.equals(embedded, nonce), "nonce must be embedded at offset 24");
        int flags = (out[20] & 0xff)
                | ((out[21] & 0xff) << 8)
                | ((out[22] & 0xff) << 16)
                | ((out[23] & 0xff) << 24);
        check(flags == 0x90205, "unexpected flags: " + Integer.toHexString(flags));
    }

    static void testType2Deterministic() throws Exception {
        Server s = newServer(null, "REALM");
        byte[] nonce = {8, 7, 6, 5, 4, 3, 2, 1};
        byte[] a = s.type2(null, nonce);
        byte[] b = s.type2(null, nonce);
        check(Arrays.equals(a, b), "type2 output must be deterministic for same input");
    }

    public static void main(String[] args) throws Exception {
        testNullDomainRejected();
        testNullNonceRejected();
        testType2NullType1Accepted();
        testType2NonceAndFlagsLayout();
        testType2Deterministic();
    }
}
