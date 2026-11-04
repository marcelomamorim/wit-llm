/* @test    @summary Tests for Server.type2(byte[], byte[]) method behavior and exception handling    @run main ServerWitupTest */

import com.sun.security.ntlm.Server;
import com.sun.security.ntlm.NTLMException;

public class ServerWitupTest {

    public static void main(String[] args) {
        testType2WithNullNonce();
        testType2WithValidInputs();
    }

    private static void testType2WithNullNonce() {
        try {
            Server server = new Server("NTLMv2", "DOMAIN") {
                @Override
                public char[] getPassword(String ntdomain, String username) {
                    return "password".toCharArray();
                }
            };
            byte[] type1 = new byte[] {0x01, 0x02};
            server.type2(type1, null);
            System.out.println("FAIL: Expected NTLMException for null nonce");
        } catch (NTLMException e) {
            if (NTLMException.PROTOCOL.equals(e.getMessage()) || e.getCause() != null) {
                System.out.println("PASS: Caught expected NTLMException for null nonce");
            } else {
                System.out.println("FAIL: Caught NTLMException, but message or cause mismatch");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testType2WithValidInputs() {
        try {
            Server server = new Server("NTLMv2", "DOMAIN") {
                @Override
                public char[] getPassword(String ntdomain, String username) {
                    return "password".toCharArray();
                }
            };
            byte[] type1 = new byte[] {0x01, 0x02};
            byte[] nonce = new byte[] {0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11};
            byte[] result = server.type2(type1, nonce);
            if (result != null && result.length > 0) {
                System.out.println("PASS: type2 returned non-empty byte array");
            } else {
                System.out.println("FAIL: type2 returned null or empty array");
            }
        } catch (NTLMException e) {
            System.out.println("FAIL: Unexpected NTLMException: " + e);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
