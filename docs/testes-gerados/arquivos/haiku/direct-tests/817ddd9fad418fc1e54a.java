/*
 * @test
 * @summary Verifies Scanner(InputStream, String charsetName) decodes input using the named charset
 * @run main ScannerWitupTest
 */

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testUtf8Decoding();
        testIso88591Decoding();
        testUtf8BytesReadAsIso88591();
    }

    static void testUtf8Decoding() throws Exception {
        String text = "h\u00e9llo w\u00f6rld";
        byte[] utf8 = text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        try (java.util.Scanner sc = new java.util.Scanner(
                new java.io.ByteArrayInputStream(utf8), "UTF-8")) {
            check("h\u00e9llo", sc.next());
            check("w\u00f6rld", sc.next());
            check(false, sc.hasNext());
        }
    }

    static void testIso88591Decoding() throws Exception {
        byte[] latin1 = {(byte) 0xE9};
        try (java.util.Scanner sc = new java.util.Scanner(
                new java.io.ByteArrayInputStream(latin1), "ISO-8859-1")) {
            check("\u00e9", sc.next());
            check(false, sc.hasNext());
        }
    }

    static void testUtf8BytesReadAsIso88591() throws Exception {
        byte[] utf8 = "\u00e9".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        try (java.util.Scanner sc = new java.util.Scanner(
                new java.io.ByteArrayInputStream(utf8), "ISO-8859-1")) {
            check("\u00c3\u00a9", sc.next());
            check(false, sc.hasNext());
        }
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("expected [" + expected + "] but was [" + actual + "]");
        }
    }

    static void check(boolean expected, boolean actual) {
        if (expected != actual) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }
}
