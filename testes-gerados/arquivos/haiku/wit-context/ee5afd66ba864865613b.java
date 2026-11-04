/*
 * @test
 * @summary Verify that SSLEngine.getHandshakeSession() throws UnsupportedOperationException in the base implementation
 * @run main SSLEngineWitupTest
 */

import javax.net.ssl.SSLEngine;

public class SSLEngineWitupTest {

    static class StubEngine extends SSLEngine {
        StubEngine() {
            super();
        }
    }

    public static void main(String[] args) throws Exception {
        SSLEngine engine = new StubEngine();
        boolean thrown = false;
        try {
            engine.getHandshakeSession();
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from getHandshakeSession()");
        }
    }
}
