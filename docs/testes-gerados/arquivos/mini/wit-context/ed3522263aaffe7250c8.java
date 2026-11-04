/*
 * @test
 * @summary Test com.sun.security.ntlm.Server.type2 method behavior including exception on null nonce
 * @run main ServerWitupTest
 */

import com.sun.security.ntlm.NTLMException;
import com.sun.security.ntlm.Server;

public class ServerWitupTest {

    public static void main(String[] args) throws Exception {
        testType2WithValidInputs();
        testType2WithNullNonceThrows();
    }

    private static void testType2WithValidInputs() throws Exception {
        // Create Server instance with version null (all versions) and domain "DOMAIN"
        Server server = new Server(null, "DOMAIN") {
            @Override
            public char[] getPassword(String ntdomain, String username) {
                return null; // Not used in this test
            }
        };

        byte[] type1 = new byte[] {0x01, 0x02, 0x03};
        byte[] nonce = new byte[8];
        for (int i = 0; i < nonce.length; i++) {
            nonce[i] = (byte) i;
        }

        byte[] type2 = server.type2(type1, nonce);

        if (type2 == null) {
            throw new RuntimeException("type2 returned null");
        }
        if (type2.length < 32) {
            throw new RuntimeException("type2 message too short");
        }
        // Basic sanity checks on returned array content
        // Check that nonce bytes appear at offset 24
        for (int i = 0; i < nonce.length; i++) {
            if (type2[24 + i] != nonce[i]) {
                throw new RuntimeException("Nonce bytes not correctly copied in type2 message");
            }
        }
    }

    private static void testType2WithNullNonceThrows() {
        try {
            Server server = new Server(null, "DOMAIN") {
                @Override
                public char[] getPassword(String ntdomain, String username) {
                    return null;
                }
            };
            server.type2(new byte[] {0x01}, null);
            throw new RuntimeException("Expected NTLMException not thrown for null nonce");
        } catch (NTLMException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("nonce cannot be null")) {
                throw new RuntimeException("NTLMException thrown but message unexpected: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type thrown", e);
        }
    }
}
