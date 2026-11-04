/*
 * @test
 * @summary Verify getOption argument checks and supported options through the public AsynchronousSocketChannel API
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousSocketChannel;

public class AsynchronousSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        try (AsynchronousSocketChannel ch = AsynchronousSocketChannel.open()) {
            testNullOptionThrowsNPE(ch);
            testUnsupportedOptionThrowsUOE(ch);
            testSupportedOptionReturnsValue(ch);
        }
    }

    private static void testNullOptionThrowsNPE(AsynchronousSocketChannel ch) {
        try {
            ch.getOption(null);
        } catch (NullPointerException expected) {
            return;
        } catch (Exception e) {
            throw new AssertionError("unexpected exception: " + e, e);
        }
        throw new AssertionError("expected NullPointerException for null option");
    }

    private static void testUnsupportedOptionThrowsUOE(AsynchronousSocketChannel ch) {
        try {
            ch.getOption(StandardSocketOptions.IP_MULTICAST_IF);
        } catch (UnsupportedOperationException expected) {
            return;
        } catch (Exception e) {
            throw new AssertionError("unexpected exception: " + e, e);
        }
        throw new AssertionError("expected UnsupportedOperationException for IP_MULTICAST_IF");
    }

    private static void testSupportedOptionReturnsValue(AsynchronousSocketChannel ch) throws Exception {
        if (!ch.supportedOptions().contains(StandardSocketOptions.SO_RCVBUF)) {
            throw new AssertionError("SO_RCVBUF should be supported");
        }
        Integer rcvBuf = ch.getOption(StandardSocketOptions.SO_RCVBUF);
        if (rcvBuf == null || rcvBuf <= 0) {
            throw new AssertionError("unexpected SO_RCVBUF value: " + rcvBuf);
        }
    }
}
