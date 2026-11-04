/*
 * @test
 * @summary Test Scanner constructor with ReadableByteChannel and Charset
 * @run main java.util.ScannerWitupTest
 */

import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Channels;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.Objects;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidChannelAndCharset();
        testConstructorWithNullChannel();
    }

    private static void testConstructorWithValidChannelAndCharset() throws Exception {
        String input = "Hello World";
        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ReadableByteChannel channel = Channels.newChannel(bais);
        Charset charset = StandardCharsets.UTF_8;

        Scanner scanner = new Scanner(channel, charset);
        String token = scanner.next();
        if (!"Hello".equals(token)) {
            throw new AssertionError("Expected 'Hello' but got '" + token + "'");
        }
        scanner.close();
    }

    private static void testConstructorWithNullChannel() {
        try {
            new Scanner((ReadableByteChannel) null, StandardCharsets.UTF_8);
            throw new AssertionError("Expected NullPointerException for null source");
        } catch (NullPointerException e) {
            if (!"source".equals(e.getMessage())) {
                throw new AssertionError("Expected NullPointerException with message 'source' but got: " + e.getMessage());
            }
        }
    }
}
