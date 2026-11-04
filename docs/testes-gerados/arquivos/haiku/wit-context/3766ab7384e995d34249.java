/*
 * @test
 * @summary Verifies DER ENUMERATED encoding produced by DerOutputStream.putEnumerated(int)
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check(0, new byte[] {0x0A, 0x01, 0x00});
        check(1, new byte[] {0x0A, 0x01, 0x01});
        check(127, new byte[] {0x0A, 0x01, 0x7F});
        check(128, new byte[] {0x0A, 0x02, 0x00, (byte) 0x80});
        check(-1, new byte[] {0x0A, 0x01, (byte) 0xFF});
        check(-129, new byte[] {0x0A, 0x02, (byte) 0xFF, 0x7F});

        DerOutputStream sized = new DerOutputStream(16);
        sized.putEnumerated(1);
        byte[] expectedSized = new byte[] {0x0A, 0x01, 0x01};
        if (!Arrays.equals(expectedSized, sized.toByteArray())) {
            throw new RuntimeException("sized stream mismatch: "
                    + Arrays.toString(sized.toByteArray()));
        }

        DerOutputStream multi = new DerOutputStream();
        multi.putEnumerated(1);
        multi.putEnumerated(2);
        byte[] expectedMulti = new byte[] {0x0A, 0x01, 0x01, 0x0A, 0x01, 0x02};
        if (!Arrays.equals(expectedMulti, multi.toByteArray())) {
            throw new RuntimeException("multi-value mismatch: "
                    + Arrays.toString(multi.toByteArray()));
        }
    }

    private static void check(int value, byte[] expected) throws IOException {
        DerOutputStream out = new DerOutputStream();
        out.putEnumerated(value);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("putEnumerated(" + value + ") expected "
                    + Arrays.toString(expected) + " but was " + Arrays.toString(actual));
        }
    }
}
