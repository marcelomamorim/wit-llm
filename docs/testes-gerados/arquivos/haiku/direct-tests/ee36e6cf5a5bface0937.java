/*
 * @test
 * @summary Scanner(ReadableByteChannel, Charset) rejects a null source and decodes UTF-8 channel input
 * @run main ScannerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNullSourceThrowsNpe();
        testUtf8TokensFromChannel();
        testEmptyChannelHasNoTokens();
    }

    static void testNullSourceThrowsNpe() {
        try {
            new Scanner((ReadableByteChannel) null, StandardCharsets.UTF_8);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testUtf8TokensFromChannel() {
        String text = "h\u00e9llo w\u00f6rld 42";
        ReadableByteChannel ch = Channels.newChannel(
                new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
        Scanner sc = new Scanner(ch, StandardCharsets.UTF_8);
        try {
            check("h\u00e9llo".equals(sc.next()), "first token");
            check("w\u00f6rld".equals(sc.next()), "second token");
            check(sc.hasNextInt(), "third token is int");
            check(sc.nextInt() == 42, "int value");
            check(!sc.hasNext(), "no more tokens");
        } finally {
            sc.close();
        }
    }

    static void testEmptyChannelHasNoTokens() {
        ReadableByteChannel ch = Channels.newChannel(new ByteArrayInputStream(new byte[0]));
        Scanner sc = new Scanner(ch, StandardCharsets.UTF_8);
        try {
            check(!sc.hasNext(), "empty channel has no tokens");
        } finally {
            sc.close();
        }
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + what);
        }
    }
}
