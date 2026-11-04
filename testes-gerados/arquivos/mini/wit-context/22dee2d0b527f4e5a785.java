/*
 * @test
 * @summary Test sun.security.ssl.SSLSessionContextImpl.getSession(byte[]) behavior
 * @run main SSLSessionContextImplWitupTest
 */

import sun.security.ssl.SSLSessionContextImpl;
import javax.net.ssl.SSLSession;
import java.util.Arrays;

public class SSLSessionContextImplWitupTest {
    public static void main(String[] args) throws Exception {
        SSLSessionContextImpl context = new SSLSessionContextImpl();

        // Test 1: Passing null should throw NullPointerException
        try {
            context.getSession(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"session id cannot be null".equals(npe.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + npe.getMessage());
            }
        }

        // Test 2: Passing a non-null sessionId that is not cached returns null
        byte[] dummySessionId = new byte[] {1, 2, 3, 4};
        SSLSession session = context.getSession(dummySessionId);
        if (session != null) {
            throw new RuntimeException("Expected null for unknown sessionId, but got non-null");
        }

        // Test 3: Passing an empty byte array sessionId returns null (edge case)
        byte[] emptySessionId = new byte[0];
        SSLSession emptySession = context.getSession(emptySessionId);
        if (emptySession != null) {
            throw new RuntimeException("Expected null for empty sessionId, but got non-null");
        }

        System.out.println("All tests passed.");
    }
}
