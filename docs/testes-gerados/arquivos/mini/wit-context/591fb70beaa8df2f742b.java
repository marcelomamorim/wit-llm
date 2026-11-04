/*
 * @test
 * @summary Test constructor of com.sun.security.ntlm.Server for null domain exception
 * @run main ServerWitupTest
 */

import com.sun.security.ntlm.NTLMException;
import com.sun.security.ntlm.Server;

public class ServerWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorNullDomain();
        testConstructorValidDomain();
    }

    private static void testConstructorNullDomain() {
        try {
            new Server(null, null) {
                @Override
                public char[] getPassword(String ntdomain, String username) {
                    return null;
                }
            };
            throw new AssertionError("Expected NTLMException for null domain");
        } catch (NTLMException e) {
            if (!"domain cannot be null".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testConstructorValidDomain() throws Exception {
        Server server = new Server(null, "DOMAIN") {
            @Override
            public char[] getPassword(String ntdomain, String username) {
                return null;
            }
        };
        if (server == null) {
            throw new AssertionError("Server instance should not be null");
        }
    }
}
