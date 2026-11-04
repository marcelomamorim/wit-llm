/*
 * @test
 * @summary Verify DerValue.getBoolean decoding and its IOException paths
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testFalse();
        testTrue();
        testNonZeroIsTrue();
        testWrongTagThrows();
        testInvalidLengthThrows();
        System.out.println("PASSED");
    }

    static void testFalse() throws Exception {
        DerValue v = new DerValue(new byte[]{0x01, 0x01, 0x00});
        if (v.getBoolean()) {
            throw new RuntimeException("expected false for 0x00");
        }
    }

    static void testTrue() throws Exception {
        DerValue v = new DerValue(new byte[]{0x01, 0x01, 0x01});
        if (!v.getBoolean()) {
            throw new RuntimeException("expected true for 0x01");
        }
    }

    static void testNonZeroIsTrue() throws Exception {
        DerValue v = new DerValue(new byte[]{0x01, 0x01, (byte) 0xFF});
        if (!v.getBoolean()) {
            throw new RuntimeException("expected true for 0xFF");
        }
    }

    static void testWrongTagThrows() {
        expectIOException(new byte[]{0x02, 0x01, 0x00});
    }

    static void testInvalidLengthThrows() {
        expectIOException(new byte[]{0x01, 0x02, 0x00, 0x00});
    }

    static void expectIOException(byte[] encoded) {
        try {
            new DerValue(encoded).getBoolean();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException");
    }
}
