/*
 * @test
 * @summary Verify DerValue.getIA5String returns ASCII content for IA5String tags and throws IOException for other tags
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] abc = {'a', 'b', 'c'};

        DerValue v = new DerValue(DerValue.tag_IA5String, abc);
        check("abc".equals(v.getIA5String()), "IA5String constructed value should decode to abc");

        DerValue fromDer = new DerValue(new byte[] {0x16, 0x03, 'a', 'b', 'c'});
        check("abc".equals(fromDer.getIA5String()), "IA5String parsed from DER should decode to abc");

        DerValue nonAscii = new DerValue(DerValue.tag_IA5String, new byte[] {'a', (byte) 0x80});
        String s = nonAscii.getIA5String();
        check(s.length() == 2 && s.charAt(0) == 'a' && s.charAt(1) == (char) 0xFFFD,
              "Non-ASCII byte should be decoded as replacement character");

        expectIOException(new DerValue(DerValue.tag_Integer, abc));
        expectIOException(new DerValue(DerValue.tag_OctetString, abc));
        expectIOException(new DerValue(DerValue.tag_Null, new byte[0]));
    }

    private static void expectIOException(DerValue value) {
        try {
            value.getIA5String();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for non-IA5String tag");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
