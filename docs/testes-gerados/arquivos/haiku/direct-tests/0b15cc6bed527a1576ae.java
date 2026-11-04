/*
 * @test
 * @summary Behavioral tests for Base64.Encoder.encode(byte[], byte[])
 * @run main Base64WitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) {
        Base64.Encoder enc = Base64.getEncoder();

        // Empty input produces zero bytes and needs no output space
        check(enc.encode(new byte[0], new byte[0]) == 0, "empty input returns 0");

        // Three input bytes produce four encoded bytes
        byte[] dst = new byte[4];
        int n = enc.encode("Man".getBytes(StandardCharsets.US_ASCII), dst);
        check(n == 4, "Man returns 4");
        check(new String(dst, StandardCharsets.US_ASCII).equals("TWFu"), "Man encodes to TWFu");

        // One input byte is padded with "=="
        byte[] dst2 = new byte[4];
        int n2 = enc.encode("M".getBytes(StandardCharsets.US_ASCII), dst2);
        check(n2 == 4, "M returns 4");
        check(new String(dst2, StandardCharsets.US_ASCII).equals("TQ=="), "M encodes to TQ==");

        // Larger output array: only the encoded prefix is written
        byte[] big = new byte[8];
        for (int i = 0; i < big.length; i++) {
            big[i] = (byte) 'X';
        }
        int n3 = enc.encode("Man".getBytes(StandardCharsets.US_ASCII), big);
        check(n3 == 4, "larger dst returns 4");
        check(new String(big, 0, 4, StandardCharsets.US_ASCII).equals("TWFu"), "prefix encoded");
        check(big[4] == 'X' && big[5] == 'X' && big[6] == 'X' && big[7] == 'X', "tail untouched");

        // Output array too small: IllegalArgumentException
        boolean threw = false;
        try {
            enc.encode("Man".getBytes(StandardCharsets.US_ASCII), new byte[3]);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check(threw, "too small dst throws IllegalArgumentException");

        // Null source: NullPointerException
        boolean npe = false;
        try {
            enc.encode(null, new byte[4]);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null src throws NullPointerException");

        // URL-safe encoder uses '-' and '_' instead of '+' and '/'
        byte[] src = { (byte) 0xFB, (byte) 0xFF };
        byte[] url = new byte[4];
        int n4 = Base64.getUrlEncoder().encode(src, url);
        check(n4 == 4, "url encoder returns 4");
        check(new String(url, StandardCharsets.US_ASCII).equals("-_8="), "url encoder output");

        byte[] std = new byte[4];
        Base64.getEncoder().encode(src, std);
        check(new String(std, StandardCharsets.US_ASCII).equals("+/8="), "standard encoder output");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
