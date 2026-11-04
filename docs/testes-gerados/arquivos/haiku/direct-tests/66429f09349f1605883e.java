/*
 * @test
 * @summary Verify RFC822Name(DerValue) decodes valid IA5String values and rejects invalid encodings with IOException
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main RFC822NameWitupTest
 */

import java.io.IOException;

import sun.security.util.DerValue;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.RFC822Name;

public class RFC822NameWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] valid = ia5("a@b.com");
        RFC822Name n = new RFC822Name(new DerValue(valid));
        check("a@b.com".equals(n.getName()), "decoded name");
        check(n.getType() == GeneralNameInterface.NAME_RFC822, "type");

        expectIOException(ia5("user@"));
        expectIOException(ia5("a@."));
        expectIOException(ia5(""));
        expectIOException(ia5("@"));

        byte[] utf8 = {0x0C, 0x07, 'a', '@', 'b', '.', 'c', 'o', 'm'};
        expectIOException(utf8);

        check("a@b.com".equals(new RFC822Name("a@b.com").getName()), "string constructor");
    }

    static byte[] ia5(String s) {
        byte[] b = new byte[2 + s.length()];
        b[0] = 0x16;
        b[1] = (byte) s.length();
        for (int i = 0; i < s.length(); i++) {
            b[2 + i] = (byte) s.charAt(i);
        }
        return b;
    }

    static void expectIOException(byte[] encoded) throws Exception {
        try {
            new RFC822Name(new DerValue(encoded));
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException was not thrown");
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + what);
        }
    }
}
