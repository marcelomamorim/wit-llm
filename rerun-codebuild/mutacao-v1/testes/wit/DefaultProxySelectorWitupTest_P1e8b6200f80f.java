/* @test    @summary Test connectFailed method for null arguments to verify exception throwing behavior    @run main DefaultProxySelectorWitupTest_P1e8b6200f80f */

import java.net.URI;
import java.net.SocketAddress;
import java.io.IOException;

public class DefaultProxySelectorWitupTest_P1e8b6200f80f {

    public static void main(String[] args) {
        testConnectFailedNullArguments();
    }

    public static void testConnectFailedNullArguments() {
        sun.net.spi.DefaultProxySelector instance = new sun.net.spi.DefaultProxySelector();
        boolean exceptionThrown = false;
        try {
            instance.connectFailed(null, null, null);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
        if (!exceptionThrown) {
            throw new AssertionError("connectFailed did not throw IllegalArgumentException for null arguments");
        }
    }
}
