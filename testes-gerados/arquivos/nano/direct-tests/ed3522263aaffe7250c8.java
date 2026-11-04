/* @test    @summary Test for Server.type2 method to ensure correct message creation and exception handling.    @run main ServerWitupTest */

import com.sun.security.ntlm.Server;
import com.sun.security.ntlm.NTLMException;

public class ServerWitupTest {

    public static void main(String[] args) {
        testType2WithValidInputs();
        testType2WithNullNonce();
        testType2WithNullType1();
    }

    private static void testType2WithValidInputs() {
        try {
            Server server = new Server("NTLM", "DOMAIN");
            byte[] type1 = {0x01, 0x02, 0x03};
            byte[] nonce = {0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11};
            byte[] result = server.type2(type1, nonce);
            if (result == null || result.length == 0) {
                throw new AssertionError("Result should not be null or empty");
            }
        } catch (NTLMException e) {
            throw new AssertionError("Unexpected NTLMException: " + e.getMessage());
        }
    }

    private static void testType2WithNullNonce() {
        try {
            Server server = new Server("NTLM", "DOMAIN");
            byte[] type1 = {0x01, 0x02};
            server.type2(type1, null);
            throw new AssertionError("Expected NTLMException for null nonce was not thrown");
        } catch (NTLMException e) {
            if (!"nonce cannot be null".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testType2WithNullType1() {
        try {
            Server server = new Server("NTLM", "DOMAIN");
            byte[] nonce = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
            byte[] result = server.type2(null, nonce);
            if (result == null || result.length == 0) {
                throw new AssertionError("Result should not be null or empty when type1 is null");
            }
        } catch (NTLMException e) {
            throw new AssertionError("Unexpected NTLMException: " + e.getMessage());
        }
    }
}
