/*
 * @test
 * @summary Validates DerInputStream.getPrintableString for valid and mismatched DER PrintableString tags
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] abc = {0x13, 0x03, 'a', 'b', 'c'};
        String s = new DerInputStream(abc).getPrintableString();
        check("abc".equals(s), "expected abc but got " + s);

        byte[] padded = {0x00, 0x00, 0x13, 0x02, 'h', 'i'};
        s = new DerInputStream(padded, 2, 4, true).getPrintableString();
        check("hi".equals(s), "expected hi but got " + s);

        byte[] wrongTag = {0x0C, 0x03, 'a', 'b', 'c'};
        boolean threw = false;
        try {
            new DerInputStream(wrongTag).getPrintableString();
        } catch (IOException e) {
            threw = true;
        }
        check(threw, "expected IOException for non-PrintableString tag");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
