/*
 * @test
 * @summary Witup-derived tests for DerInputStream.getUTCTime tag validation and UTCTime parsing
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */
import java.io.IOException;
import java.util.Date;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidUtcTime();
        testWrongTagRejected();
        testEmptyInputRejected();
    }

    static void testValidUtcTime() throws Exception {
        byte[] data = {0x17, 0x0D, '1', '3', '0', '1', '0', '1', '0', '0', '0', '0', '0', '0', 'Z'};
        Date d = new DerInputStream(data).getUTCTime();
        if (d == null) {
            throw new RuntimeException("getUTCTime returned null");
        }
        if (d.getTime() != 1356998400000L) {
            throw new RuntimeException("Unexpected time: " + d.getTime());
        }
    }

    static void testWrongTagRejected() throws Exception {
        byte[] data = {0x02, 0x01, 0x00};
        expectIOException(data);
    }

    static void testEmptyInputRejected() throws Exception {
        expectIOException(new byte[0]);
    }

    static void expectIOException(byte[] data) throws Exception {
        boolean thrown = false;
        try {
            new DerInputStream(data).getUTCTime();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException was not thrown");
        }
    }
}
