/*
 * @test
 * @summary Verifies Server constructor accepts a non-null domain and rejects a null domain with NTLMException
 * @modules java.base/com.sun.security.ntlm
 * @run main ServerWitupTest
 */

import com.sun.security.ntlm.NTLMException;
import com.sun.security.ntlm.Server;

public class ServerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static Server newServer(String version, String domain) throws NTLMException {
        return new Server(version, domain) {
            public char[] getPassword(String ntdomain, String username) {
                return null;
            }
        };
    }

    public static void main(String[] args) throws Exception {
        Server s1 = newServer(null, "REALM");
        check(s1 != null, "server with null version and valid domain must be created");

        Server s2 = newServer("NTLMv2", "REALM");
        check(s2 != null, "server with version NTLMv2 and valid domain must be created");

        Server s3 = newServer("NTLM", "");
        check(s3 != null, "server with empty domain must be created");

        boolean thrown = false;
        try {
            newServer(null, null);
        } catch (NTLMException e) {
            thrown = true;
        }
        check(thrown, "null domain must throw NTLMException (null version)");

        thrown = false;
        try {
            newServer("NTLMv2", null);
        } catch (NTLMException e) {
            thrown = true;
        }
        check(thrown, "null domain must throw NTLMException (NTLMv2 version)");
    }
}
