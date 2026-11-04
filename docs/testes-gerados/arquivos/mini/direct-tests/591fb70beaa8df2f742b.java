/*
 * @test
 * @summary Test constructor of com.sun.security.ntlm.Server for domain null check and version handling
 * @run main ServerWitupTest
 */

import com.sun.security.ntlm.NTLMException;
import com.sun.security.ntlm.Server;

public class ServerWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidDomain();
        testConstructorWithNullDomain();
        testConstructorWithNullVersion();
    }

    static void testConstructorWithValidDomain() throws Exception {
        Server server = new Server("NTLM", "DOMAIN") {
            @Override
            public char[] getPassword(String ntdomain, String username) {
                return null;
            }
        };
        if (server == null) {
            throw new RuntimeException("Server instance should not be null");
        }
    }

    static void testConstructorWithNullDomain() {
        try {
            new Server("NTLM", null) {
                @Override
                public char[] getPassword(String ntdomain, String username) {
                    return null;
                }
            };
            throw new RuntimeException("Expected NTLMException not thrown for null domain");
        } catch (NTLMException e) {
            if (!"domain cannot be null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testConstructorWithNullVersion() throws Exception {
        Server server = new Server(null, "DOMAIN") {
            @Override
            public char[] getPassword(String ntdomain, String username) {
                return null;
            }
        };
        if (server == null) {
            throw new RuntimeException("Server instance should not be null when version is null");
        }
    }
}
