/*
 * @test
 * @summary White-box WITUP-derived checks for com.sun.security.ntlm.Server.type2 (null nonce exception, valid output, determinism)
 * @modules java.base/com.sun.security.ntlm
 * @run main ServerWitupTest
 */

import com.sun.security.ntlm.NTLMException;
import com.sun.security.ntlm.Server;

import java.util.Arrays;

public class ServerWitupTest {

    public static void main(String[] args) throws Exception {
        Server server = new Server(null, "REALM") {
            public char[] getPassword(String ntdomain, String username) {
                return null;
            }
        };

        byte[] nonce = new byte[] {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] type1 = new byte[] {78, 84, 76, 77, 83, 83, 80, 0, 1, 0, 0, 0};

        testNullNonceThrows(server, type1);
        testNullNonceThrowsWithNullType1(server);
        testValidOutputHasNtlmSignature(server, type1, nonce);
        testNullType1Accepted(server, nonce);
        testDeterministicOutput(server, type1, nonce);
    }

    private static void testNullNonceThrows(Server server, byte[] type1) {
        try {
            server.type2(type1, null);
        } catch (NTLMException e) {
            return;
        }
        throw new RuntimeException("Expected NTLMException for null nonce");
    }

    private static void testNullNonceThrowsWithNullType1(Server server) {
        try {
            server.type2(null, null);
        } catch (NTLMException e) {
            return;
        }
        throw new RuntimeException("Expected NTLMException for null nonce with null type1");
    }

    private static void testValidOutputHasNtlmSignature(Server server, byte[] type1, byte[] nonce)
            throws Exception {
        byte[] out = server.type2(type1, nonce);
        if (out == null || out.length < 9) {
            throw new RuntimeException("Type 2 output is null or too short");
        }
        String sig = new String(out, 0, 7, "US-ASCII");
        if (!"NTLMSSP".equals(sig) || out[7] != 0) {
            throw new RuntimeException("Type 2 output does not start with NTLMSSP signature");
        }
        if (out[8] != 2) {
            throw new RuntimeException("Type 2 message type field is not 2");
        }
    }

    private static void testNullType1Accepted(Server server, byte[] nonce) throws Exception {
        byte[] out = server.type2(null, nonce);
        if (out == null || out.length == 0) {
            throw new RuntimeException("Expected non-empty Type 2 output when type1 is null");
        }
    }

    private static void testDeterministicOutput(Server server, byte[] type1, byte[] nonce)
            throws Exception {
        byte[] first = server.type2(type1, nonce);
        byte[] second = server.type2(type1, nonce);
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("Type 2 output is not deterministic for same inputs");
        }
    }
}
