/*
 * @test
 * @summary Verifies DerValue.getGeneralString returns ASCII content for GeneralString tags and rejects other tags with IOException
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        byte generalStringTag = (byte) 0x1b;

        DerValue value = new DerValue(generalStringTag, "abc".getBytes("ASCII"));
        check("abc".equals(value.getGeneralString()));

        DerValue empty = new DerValue(generalStringTag, new byte[0]);
        check("".equals(empty.getGeneralString()));

        DerValue nonAscii = new DerValue(generalStringTag, new byte[] {(byte) 0xC3, (byte) 0xA9});
        check(nonAscii.getGeneralString().length() == 2);

        expectIOException(new DerValue((byte) 0x02, new byte[] {1}));
        expectIOException(new DerValue((byte) 0x04, "x".getBytes("ASCII")));
        expectIOException(new DerValue((byte) 0x13, "x".getBytes("ASCII")));
    }

    static void expectIOException(DerValue v) {
        try {
            v.getGeneralString();
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("expected IOException for non-GeneralString tag");
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("check failed");
        }
    }
}
