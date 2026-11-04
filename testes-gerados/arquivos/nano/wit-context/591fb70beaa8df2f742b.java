/* @test    @summary Test for Server constructor null domain argument    @run main ServerWitupTest */

import com.sun.security.ntlm.Server;
import com.sun.security.ntlm.NTLMException;

public class ServerWitupTest {

    public static void main(String[] args) {
        testConstructorNullDomain();
        testConstructorValidArguments();
    }

    private static void testConstructorNullDomain() {
        boolean exceptionThrown = false;
        try {
            new Server(null, "domain");
        } catch (NTLMException e) {
            exceptionThrown = true;
            if (!"domain cannot be null".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NTLMException was not thrown for null domain");
        }
    }

    private static void testConstructorValidArguments() {
        try {
            Server server = new Server("NTLMv2", "MYDOMAIN");
            if (server == null) {
                throw new AssertionError("Server instance is null");
            }
        } catch (NTLMException e) {
            throw new AssertionError("Unexpected NTLMException for valid arguments: " + e.getMessage());
        }
    }
}
