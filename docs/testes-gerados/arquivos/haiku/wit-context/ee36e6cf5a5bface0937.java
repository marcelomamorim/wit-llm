/*
 * @test
 * @summary Witup-derived checks for Scanner(ReadableByteChannel, Charset) null handling and basic reading
 * @run main ScannerWitupTest
 */
import java.io.ByteArrayInputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testNullSourceThrowsNPE();
        testValidChannelReadsLine();
    }

    private static void testNullCharsetThrowsNPE() {
        ReadableByteChannel ch = Channels.newChannel(new ByteArrayInputStream(new byte[] {'a'}));
        try {
            new Scanner(ch, (java.nio.charset.Charset) null);
            throw new AssertionError("expected NullPointerException for null charset");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullSourceThrowsNPE() {
        try {
            new Scanner((ReadableByteChannel) null, StandardCharsets.UTF_8);
            throw new AssertionError("expected NullPointerException for null source");
        } catch (NullPointerException expected) {
        }
    }

    private static void testValidChannelReadsLine() {
        byte[] data = "hello\n".getBytes(StandardCharsets.UTF_8);
        ReadableByteChannel ch = Channels.newChannel(new ByteArrayInputStream(data));
        Scanner sc = new Scanner(ch, StandardCharsets.UTF_8);
        try {
            String line = sc.nextLine();
            if (!"hello".equals(line)) {
                throw new AssertionError("unexpected line: " + line);
            }
        } finally {
            sc.close();
        }
    }
}
