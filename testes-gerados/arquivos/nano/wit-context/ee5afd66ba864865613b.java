/*
 * @test
 * @summary Test for SSLEngine.getHandshakeSession() method behavior.
 * @run main SSLEngineWitupTest
 */

import javax.net.ssl.SSLEngine;

public class SSLEngineWitupTest {

    public static void main(String[] args) {
        testGetHandshakeSessionThrowsException();
    }

    public static void testGetHandshakeSessionThrowsException() {
        SSLEngine engine = new SSLEngine() {
            @Override
            public SSLSession getHandshakeSession() {
                throw new UnsupportedOperationException();
            }
        };
        try {
            engine.getHandshakeSession();
            System.out.println("Test failed: no exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException thrown as expected");
        } catch (Exception e) {
            System.out.println("Test failed: unexpected exception: " + e);
        }
    }
}
