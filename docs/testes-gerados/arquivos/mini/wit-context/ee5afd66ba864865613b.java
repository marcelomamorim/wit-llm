/*
 * @test
 * @summary Test javax.net.ssl.SSLEngine.getHandshakeSession() throws UnsupportedOperationException
 * @run main SSLEngineWitupTest
 */

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;

public class SSLEngineWitupTest {
    public static void main(String[] args) {
        SSLEngine engine = new SSLEngine() {
            // Use anonymous subclass to instantiate abstract SSLEngine
        };

        try {
            SSLSession session = engine.getHandshakeSession();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown, got: " + session);
        } catch (UnsupportedOperationException expected) {
            // Expected exception, test passes
        }
    }
}
