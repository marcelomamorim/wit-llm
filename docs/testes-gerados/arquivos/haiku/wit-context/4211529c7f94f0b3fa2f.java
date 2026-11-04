/*
 * @test
 * @summary Witup-generated checks for Files.readString(Path, Charset)
 * @run main FilesWitupTest
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class FilesWitupTest {

    public static void main(String[] args) throws Exception {
        testNullPath();
        testNullCharset();
        testRoundTripUtf8();
        testLatin1Decoding();
        testEmptyFile();
        testMissingFile();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static Path tempWith(byte[] data) throws IOException {
        Path p = Files.createTempFile("witup", ".txt");
        Files.write(p, data);
        return p;
    }

    static void testNullPath() throws IOException {
        try {
            Files.readString(null, StandardCharsets.UTF_8);
            throw new RuntimeException("expected NullPointerException for null path");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullCharset() throws IOException {
        Path p = tempWith("abc".getBytes(StandardCharsets.UTF_8));
        try {
            Files.readString(p, null);
            throw new RuntimeException("expected NullPointerException for null charset");
        } catch (NullPointerException expected) {
        } finally {
            Files.deleteIfExists(p);
        }
    }

    static void testRoundTripUtf8() throws IOException {
        String text = "h\u00e9llo \u4e16\u754c\nline2";
        Path p = tempWith(text.getBytes(StandardCharsets.UTF_8));
        try {
            String read = Files.readString(p, StandardCharsets.UTF_8);
            check(text.equals(read), "UTF-8 round trip mismatch: " + read);
        } finally {
            Files.deleteIfExists(p);
        }
    }

    static void testLatin1Decoding() throws IOException {
        byte[] data = new byte[] { (byte) 0x41, (byte) 0xE9 };
        Path p = tempWith(data);
        try {
            String read = Files.readString(p, StandardCharsets.ISO_8859_1);
            check("A\u00e9".equals(read), "ISO-8859-1 decoding mismatch: " + read);
        } finally {
            Files.deleteIfExists(p);
        }
    }

    static void testEmptyFile() throws IOException {
        Path p = tempWith(new byte[0]);
        try {
            String read = Files.readString(p, StandardCharsets.UTF_8);
            check(read.isEmpty(), "empty file should yield empty string");
        } finally {
            Files.deleteIfExists(p);
        }
    }

    static void testMissingFile() throws IOException {
        Path p = Files.createTempFile("witup", ".txt");
        Files.delete(p);
        try {
            Files.readString(p, StandardCharsets.UTF_8);
            throw new RuntimeException("expected NoSuchFileException for missing file");
        } catch (NoSuchFileException expected) {
        }
    }
}
