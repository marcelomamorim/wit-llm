/*
 * @test
 * @summary Behavioral tests for SocketChannelImpl.read(ByteBuffer) via public SocketChannel API
 * @run main SocketChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNullBufferThrowsNPE();
        testReadReturnsDataAndCount();
        testReadReturnsEofAfterPeerClose();
        testNonBlockingReadWithoutDataReturnsZero();
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static SocketChannel[] connectPair() throws IOException {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            SocketChannel client = SocketChannel.open(ssc.getLocalAddress());
            SocketChannel server = ssc.accept();
            return new SocketChannel[] { client, server };
        }
    }

    static void testNullBufferThrowsNPE() throws IOException {
        try (SocketChannel sc = SocketChannel.open()) {
            try {
                sc.read((ByteBuffer) null);
                fail("expected NullPointerException for null buffer");
            } catch (NullPointerException expected) {
                // expected: Objects.requireNonNull(buf)
            }
        }
    }

    static void testReadReturnsDataAndCount() throws IOException {
        SocketChannel[] pair = connectPair();
        try (SocketChannel client = pair[0]; SocketChannel server = pair[1]) {
            ByteBuffer out = ByteBuffer.wrap("abc".getBytes(StandardCharsets.US_ASCII));
            while (out.hasRemaining()) {
                server.write(out);
            }

            ByteBuffer in = ByteBuffer.allocate(16);
            int total = 0;
            while (total < 3) {
                int n = client.read(in);
                if (n == -1) {
                    fail("unexpected EOF before all data was read");
                }
                total += n;
            }
            if (total != 3) {
                fail("expected 3 bytes read, got " + total);
            }
            if (in.position() != 3) {
                fail("buffer position mismatch: " + in.position());
            }
            if (in.get(0) != 'a' || in.get(1) != 'b' || in.get(2) != 'c') {
                fail("unexpected data read");
            }
        }
    }

    static void testReadReturnsEofAfterPeerClose() throws IOException {
        SocketChannel[] pair = connectPair();
        try (SocketChannel client = pair[0]) {
            pair[1].close();
            ByteBuffer in = ByteBuffer.allocate(8);
            int n = client.read(in);
            if (n != -1) {
                fail("expected -1 (EOF) after peer close, got " + n);
            }
        }
    }

    static void testNonBlockingReadWithoutDataReturnsZero() throws IOException {
        SocketChannel[] pair = connectPair();
        try (SocketChannel client = pair[0]; SocketChannel server = pair[1]) {
            client.configureBlocking(false);
            ByteBuffer in = ByteBuffer.allocate(8);
            int n = client.read(in);
            if (n != 0) {
                fail("expected 0 for non-blocking read with no data, got " + n);
            }
            if (in.position() != 0) {
                fail("buffer should not be modified");
            }
        }
    }
}
