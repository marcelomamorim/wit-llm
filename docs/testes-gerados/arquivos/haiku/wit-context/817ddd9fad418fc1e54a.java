/*
 * @test
 * @summary Verifies Scanner(InputStream, String charsetName) rejects a null charset name and decodes input with a valid charset name
 * @run main ScannerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNullCharsetNameThrowsNpe();
        testUtf8CharsetNameDecodesInput();
    }

    static void testNullCharsetNameThrowsNpe() {
        InputStream in = new ByteArrayInputStream("abc".getBytes(StandardCharsets.US_ASCII));
        try {
            new Scanner(in, (String) null);
            throw new RuntimeException("expected NullPointerException for null charsetName");
        } catch (NullPointerException expected) {
        }
    }

    static void testUtf8CharsetNameDecodesInput() {
        byte[] data = "\u00e9t\u00e9\nline2".getBytes(StandardCharsets.UTF_8);
        Scanner sc = new Scanner(new ByteArrayInputStream(data), "UTF-8");
        try {
            String first = sc.nextLine();
            if (!first.equals("\u00e9t\u00e9")) {
                throw new RuntimeException("unexpected first line: " + first);
            }
            String second = sc.nextLine();
            if (!second.equals("line2")) {
                throw new RuntimeException("unexpected second line: " + second);
            }
        } finally {
            sc.close();
        }
    }
}
