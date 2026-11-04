/*
 * @test
 * @summary Test com.sun.security.ntlm.Server.type2 method behavior
 * @run main ServerWitupTest
 */

import com.sun.security.ntlm.NTLMException;
import com.sun.security.ntlm.Server;

public class ServerWitupTest {

    public static void main(String[] args) throws Exception {
        testType2WithValidNonce();
        testType2WithNullNonceThrows();
        testType2WithNullType1();
    }

    private static void testType2WithValidNonce() throws Exception {
        final byte[] nonce = new byte[] {1,2,3,4,5,6,7,8};
        Server server = new Server(null, "DOMAIN") {
            @Override
            public char[] getPassword(String ntdomain, String username) {
                return null;
            }
        };

        byte[] result = server.type2(new byte[] {10,20,30}, nonce);
        if (result == null || result.length == 0) {
            throw new RuntimeException("type2 returned null or empty byte array");
        }

        // The returned array should contain the nonce bytes starting at offset 24
        for (int i = 0; i < nonce.length; i++) {
            if (result[24 + i] != nonce[i]) {
                throw new RuntimeException("Nonce bytes not correctly written at offset 24");
            }
        }
    }

    private static void testType2WithNullNonceThrows() {
        Server server = new Server(null, "DOMAIN") {
            @Override
            public char[] getPassword(String ntdomain, String username) {
                return null;
            }
        };

        try {
            server.type2(new byte[] {1,2,3}, null);
            throw new RuntimeException("Expected NTLMException not thrown for null nonce");
        } catch (NTLMException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("nonce cannot be null")) {
                throw new RuntimeException("NTLMException thrown but message unexpected: " + msg);
            }
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type thrown", t);
        }
    }

    private static void testType2WithNullType1() throws Exception {
        final byte[] nonce = new byte[] {9,8,7,6,5,4,3,2};
        Server server = new Server(null, "DOMAIN") {
            @Override
            public char[] getPassword(String ntdomain, String username) {
                return null;
            }
        };

        byte[] result = server.type2(null, nonce);
        if (result == null || result.length == 0) {
            throw new RuntimeException("type2 returned null or empty byte array when type1 is null");
        }

        // The returned array should contain the nonce bytes starting at offset 24
        for (int i = 0; i < nonce.length; i++) {
            if (result[24 + i] != nonce[i]) {
                throw new RuntimeException("Nonce bytes not correctly written at offset 24 when type1 is null");
            }
        }
    }
}
