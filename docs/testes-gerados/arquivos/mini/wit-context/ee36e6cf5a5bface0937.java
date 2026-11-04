/*
 * @test
 * @summary Test Scanner(ReadableByteChannel, Charset) constructor for null charset and normal usage
 * @run main ScannerWitupTest
 */

import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.Objects;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testValidReadableByteChannelAndCharset();
    }

    private static void testNullCharsetThrowsNPE() {
        ReadableByteChannel channel = new ReadableByteChannel() {
            private boolean open = true;
            public int read(ByteBuffer dst) throws IOException {
                return -1; // EOF
            }
            public boolean isOpen() {
                return open;
            }
            public void close() throws IOException {
                open = false;
            }
        };

        try {
            new Scanner(channel, null);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidReadableByteChannelAndCharset() throws IOException {
        final String input = "42";
        final byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        final ReadableByteChannel channel = Channels.newChannel(bais);

        Scanner scanner = new Scanner(channel, StandardCharsets.UTF_8);
        try {
            if (!scanner.hasNextInt()) {
                throw new AssertionError("Expected hasNextInt() to be true");
            }
            int value = scanner.nextInt();
            if (value != 42) {
                throw new AssertionError("Expected nextInt() to return 42 but got " + value);
            }
        } finally {
            scanner.close();
        }
    }
}
