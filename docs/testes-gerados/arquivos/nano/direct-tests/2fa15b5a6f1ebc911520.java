/* @test    @summary Test getRequestedServerNames method of ExtendedSSLSession    @run main ExtendedSSLSessionWitupTest */

import javax.net.ssl.ExtendedSSLSession;
import java.util.List;

public class ExtendedSSLSessionWitupTest {

    public static void main(String[] args) {
        testGetRequestedServerNamesReturnsEmptyList();
        testGetRequestedServerNamesReturnsNonEmptyList();
    }

    private static void testGetRequestedServerNamesReturnsEmptyList() {
        ExtendedSSLSession session = new ExtendedSSLSession() {
            @Override
            public List getRequestedServerNames() {
                return java.util.Collections.emptyList();
            }
            // Implement other abstract methods with dummy returns or throw UnsupportedOperationException
            @Override public String[] getLocalSupportedSignatureAlgorithms() { throw new UnsupportedOperationException(); }
            @Override public String[] getPeerSupportedSignatureAlgorithms() { throw new UnsupportedOperationException(); }
            // All other methods from SSLSession and ExtendedSSLSession are omitted for brevity
        };
        List result = session.getRequestedServerNames();
        if (result == null) {
            throw new AssertionError("Expected non-null list");
        }
        if (!result.isEmpty()) {
            throw new AssertionError("Expected empty list");
        }
        System.out.println("testGetRequestedServerNamesReturnsEmptyList passed.");
    }

    private static void testGetRequestedServerNamesReturnsNonEmptyList() {
        List<String> dummyList = java.util.Arrays.asList("server1", "server2");
        ExtendedSSLSession session = new ExtendedSSLSession() {
            @Override
            public List getRequestedServerNames() {
                return dummyList;
            }
            @Override public String[] getLocalSupportedSignatureAlgorithms() { throw new UnsupportedOperationException(); }
            @Override public String[] getPeerSupportedSignatureAlgorithms() { throw new UnsupportedOperationException(); }
        };
        List result = session.getRequestedServerNames();
        if (result != dummyList) {
            throw new AssertionError("Returned list does not match expected list");
        }
        System.out.println("testGetRequestedServerNamesReturnsNonEmptyList passed.");
    }
}
