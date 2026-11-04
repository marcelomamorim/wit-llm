/*
 * @test
 * @summary Test javax.net.ssl.SSLEngine.getHandshakeSession() throws UnsupportedOperationException
 * @run main javax.net.ssl.SSLEngineWitupTest
 */

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;

public class SSLEngineWitupTest {
    public static void main(String[] args) {
        SSLEngine engine = new SSLEngine() {
            // Use default protected constructor
        };
        try {
            SSLSession session = engine.getHandshakeSession();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown, got: " + session);
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
