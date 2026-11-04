/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.run() method behavior
 * @run main FtpClientWitupTest
 */

import java.net.Socket;
import java.net.Proxy;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        testRunWithNonNullProxy();
        testRunWithNullProxy();
    }

    private static void testRunWithNonNullProxy() throws Exception {
        // Create an instance of FtpClient via factory
        FtpClient client = FtpClient.create();

        // Use reflection to set the proxy field to a non-null Proxy instance
        // Because proxy field is private, and no setter is visible, we skip this test
        // Instead, we test that run() returns a Socket when proxy is non-null by subclassing

        FtpClient clientWithProxy = new FtpClient() {
            {
                // Set proxy to a direct proxy (non-null)
                try {
                    java.lang.reflect.Field proxyField = FtpClient.class.getDeclaredField("proxy");
                    proxyField.setAccessible(true);
                    proxyField.set(this, Proxy.NO_PROXY);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Socket run() {
                return super.run();
            }
        };

        Socket s = clientWithProxy.run();
        if (s == null) {
            throw new AssertionError("run() returned null Socket when proxy is non-null");
        }
        s.close();
    }

    private static void testRunWithNullProxy() {
        // Create an instance of FtpClient with proxy == null
        FtpClient clientWithNullProxy = new FtpClient() {
            {
                try {
                    java.lang.reflect.Field proxyField = FtpClient.class.getDeclaredField("proxy");
                    proxyField.setAccessible(true);
                    proxyField.set(this, null);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Socket run() {
                return super.run();
            }
        };

        try {
            clientWithNullProxy.run();
            throw new AssertionError("Expected IllegalArgumentException when proxy is null");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
