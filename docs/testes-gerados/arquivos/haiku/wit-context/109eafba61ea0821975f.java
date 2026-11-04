/*
 * @test
 * @summary Witup-derived checks for Scanner(InputStream, Charset): null arguments and charset handling
 * @run main ScannerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testNullSourceThrowsNPE();
        testUtf8DecodingHonored();
        testIso88591DecodingHonored();
    }

    static void testNullCharsetThrowsNPE() {
        boolean thrown = false;
        try {
            InputStream in = new ByteArrayInputStream(new byte[] {'a'});
            new Scanner(in, (Charset) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null charset");
        }
    }

    static void testNullSourceThrowsNPE() {
        boolean thrown = false;
        try {
            new Scanner((InputStream) null, StandardCharsets.UTF_8);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null source");
        }
    }

    static void testUtf8DecodingHonored() {
        byte[] data = new byte[] {(byte) 0xC3, (byte) 0xA9};
        Scanner sc = new Scanner(new ByteArrayInputStream(data), StandardCharsets.UTF_8);
        String token = sc.next();
        sc.close();
        String expected = String.valueOf((char) 0xE9);
        if (!expected.equals(token)) {
            throw new RuntimeException("UTF-8 decoding mismatch: " + token);
        }
    }

    static void testIso88591DecodingHonored() {
        byte[] data = new byte[] {(byte) 0xE9};
        Scanner sc = new Scanner(new ByteArrayInputStream(data), StandardCharsets.ISO_8859_1);
        String token = sc.next();
        sc.close();
        String expected = String.valueOf((char) 0xE9);
        if (!expected.equals(token)) {
            throw new RuntimeException("ISO-8859-1 decoding mismatch: " + token);
        }
    }
}
