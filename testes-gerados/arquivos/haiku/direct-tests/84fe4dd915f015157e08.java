/*
 * @test
 * @summary Scanner(Path, String) decodes the file with the given charset, rejects a null source and reports a missing file with IOException
 * @run main ScannerWitupTest
 */
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testUtf8Path();
        testLatin1Path();
        testNullSourceThrowsNPE();
        testMissingFileThrowsIOException();
    }

    static void testUtf8Path() throws Exception {
        Path p = Files.createTempFile("scw", ".txt");
        try {
            Files.write(p, "h\u00e9llo w\u00f6rld".getBytes(StandardCharsets.UTF_8));
            try (Scanner s = new Scanner(p, "UTF-8")) {
                check("h\u00e9llo", s.next());
                check("w\u00f6rld", s.next());
                check(Boolean.FALSE, s.hasNext());
            }
        } finally {
            Files.deleteIfExists(p);
        }
    }

    static void testLatin1Path() throws Exception {
        Path p = Files.createTempFile("scw", ".txt");
        try {
            byte[] data = {0x63, 0x61, 0x66, (byte) 0xE9};
            Files.write(p, data);
            try (Scanner s = new Scanner(p, "ISO-8859-1")) {
                check("caf\u00e9", s.next());
            }
        } finally {
            Files.deleteIfExists(p);
        }
    }

    static void testNullSourceThrowsNPE() throws Exception {
        try {
            new Scanner((Path) null, "UTF-8");
            fail("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(source)
        }
    }

    static void testMissingFileThrowsIOException() throws Exception {
        Path p = Files.createTempFile("scw", ".txt");
        Files.delete(p);
        try {
            new Scanner(p, "UTF-8");
            fail("expected IOException for missing file");
        } catch (IOException expected) {
            // expected: file no longer exists
        }
    }

    static void check(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }
}
