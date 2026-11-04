/* @test    @summary Test constructor of com.sun.security.ntlm.Server for null domain and valid parameters    @run main ServerWitupTest */

import com.sun.security.ntlm.Server;
import com.sun.security.ntlm.NTLMException;

public class ServerWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullDomain();
        testConstructorWithValidParameters();
    }

    private static void testConstructorWithNullDomain() {
        boolean exceptionThrown = false;
        try {
            new Server(null, "domain");
        } catch (NTLMException e) {
            exceptionThrown = true;
            assert "domain cannot be null".equals(e.getMessage()) : "Unexpected exception message";
        }
        assert exceptionThrown : "Expected NTLMException was not thrown for null domain";
    }

    private static void testConstructorWithValidParameters() {
        try {
            Server server = new Server("NTLM", "domain");
            assert server != null : "Server instance should not be null";
        } catch (NTLMException e) {
            assert false : "NTLMException was not expected here";
        }
    }
}
