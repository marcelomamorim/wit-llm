/*
 * @test
 * @summary Verify DerInputStream.getDerValue returns sequential DER values and rejects truncated input
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSequentialValues();
        testOffsetConstructor();
        testTruncatedValueThrowsIOException();
    }

    static void testSequentialValues() throws Exception {
        byte[] data = {0x02, 0x01, 0x05, 0x02, 0x01, 0x06};
        DerInputStream in = new DerInputStream(data);
        DerValue first = in.getDerValue();
        if (first == null) {
            throw new RuntimeException("first getDerValue returned null");
        }
        DerValue second = in.getDerValue();
        if (second == null) {
            throw new RuntimeException("second getDerValue returned null");
        }
    }

    static void testOffsetConstructor() throws Exception {
        byte[] data = {(byte) 0xFF, (byte) 0xFF, 0x02, 0x01, 0x07};
        DerInputStream in = new DerInputStream(data, 2, 3, true);
        DerValue v = in.getDerValue();
        if (v == null) {
            throw new RuntimeException("offset-based getDerValue returned null");
        }
    }

    static void testTruncatedValueThrowsIOException() {
        byte[] truncated = {0x02, 0x05, 0x01};
        DerInputStream in = new DerInputStream(truncated);
        try {
            in.getDerValue();
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("expected IOException for truncated DER value");
    }
}
