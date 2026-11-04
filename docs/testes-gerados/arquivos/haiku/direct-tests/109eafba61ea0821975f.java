/*
 * @test
 * @summary Tests for Scanner(InputStream, Charset) constructor behavior
 * @run main ScannerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testNullSourceThrowsNPE();
        testReadsTokensUtf8();
        testNonAsciiUtf8();
        testCharsetAffectsDecoding();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullSourceThrowsNPE() {
        try {
            new Scanner((InputStream) null, StandardCharsets.UTF_8);
            throw new AssertionError("expected NullPointerException for null source");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReadsTokensUtf8() {
        byte[] data = "12 abc 3.5".getBytes(StandardCharsets.UTF_8);
        Scanner sc = new Scanner(new ByteArrayInputStream(data), StandardCharsets.UTF_8);
        check(sc.nextInt() == 12, "first token should be 12");
        check(sc.next().equals("abc"), "second token should be abc");
        check(sc.next().equals("3.5"), "third token should be 3.5");
        check(!sc.hasNext(), "no tokens should remain");
        sc.close();
    }

    static void testNonAsciiUtf8() {
        String word = "h" + String.valueOf((char) 0xE9) + "llo";
        byte[] data = word.getBytes(StandardCharsets.UTF_8);
        Scanner sc = new Scanner(new ByteArrayInputStream(data), StandardCharsets.UTF_8);
        check(sc.next().equals(word), "UTF-8 non-ASCII token should be decoded correctly");
        sc.close();
    }

    static void testCharsetAffectsDecoding() {
        String word = String.valueOf((char) 0xE9);
        byte[] data = word.getBytes(StandardCharsets.ISO_8859_1);
        check(data.length == 1, "ISO-8859-1 encoding of U+00E9 should be one byte");
        Scanner sc = new Scanner(new ByteArrayInputStream(data), StandardCharsets.ISO_8859_1);
        check(sc.next().equals(word), "ISO-8859-1 token should be decoded to U+00E9");
        sc.close();
    }
}
