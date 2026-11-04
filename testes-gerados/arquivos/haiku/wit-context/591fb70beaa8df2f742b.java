/*
 * @test
 * @summary Witup generated tests for com.sun.security.ntlm.Server constructor
 * @modules java.base/com.sun.security.ntlm
 * @run main ServerWitupTest
 */

import com.sun.security.ntlm.NTLMException;
import com.sun.security.ntlm.Server;

public class ServerWitupTest {

    static class TestServer extends Server {
        TestServer(String version, String domain) throws NTLMException {
            super(version, domain);
        }

        public char[] getPassword(String ntdomain, String username) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        testNullDomainThrows(null);
        testNullDomainThrows("NTLMv2");
        testNullDomainThrows("NTLM");
        testValidDomain(null);
        testValidDomain("NTLM");
        testValidDomain("NTLM2");
        testValidDomain("NTLMv2");
    }

    static void testNullDomainThrows(String version) {
        try {
            new TestServer(version, null);
        } catch (NTLMException e) {
            return;
        }
        throw new RuntimeException("NTLMException expected for null domain, version=" + version);
    }

    static void testValidDomain(String version) throws NTLMException {
        try {
            new TestServer(version, "REALM");
        } catch (NTLMException e) {
            throw new RuntimeException("Unexpected NTLMException for valid domain, version=" + version, e);
        }
    }
}
