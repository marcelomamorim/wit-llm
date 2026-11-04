/*
 * @test
 * @summary Test sun.security.ssl.SSLSessionContextImpl.getSession(byte[])
 * @run main SSLSessionContextImplWitupTest
 */

import sun.security.ssl.SSLSessionContextImpl;
import sun.security.ssl.SSLSessionImpl;
import sun.security.ssl.SessionId;
import javax.net.ssl.SSLSession;
import java.util.Arrays;

public class SSLSessionContextImplWitupTest {
    public static void main(String[] args) throws Exception {
        SSLSessionContextImpl context = new SSLSessionContextImpl();

        // Test 1: Null sessionId should throw NullPointerException
        try {
            context.getSession(null);
            throw new RuntimeException("Expected NullPointerException for null sessionId");
        } catch (NullPointerException npe) {
            if (!"session id cannot be null".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch");
            }
        }

        // Test 2: Unknown sessionId returns null
        byte[] unknownId = new byte[] {1, 2, 3, 4};
        SSLSession session = context.getSession(unknownId);
        if (session != null) {
            throw new RuntimeException("Expected null for unknown sessionId");
        }

        // Test 3: Add a session and retrieve it
        // We must create a SSLSessionImpl and put it into the cache via reflection or public API
        // Since no public API to add session is visible, we test only observable behavior
        // So this test is limited to verifying that getSession returns null for unknown id

        System.out.println("All tests passed.");
    }
}
