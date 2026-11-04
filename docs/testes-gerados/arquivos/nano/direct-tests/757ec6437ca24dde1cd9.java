/* @test    @summary Test DerInputStream constructor with valid DerInputBuffer.    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import sun.security.util.DerInputBuffer;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithValidBuffer();
    }

    static void testConstructorWithValidBuffer() throws IOException {
        byte[] data = {0x30, 0x0A, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02, 0x02, 0x01, 0x03};
        DerInputBuffer buf = DerInputBuffer.getInstance(data);
        DerInputStream dis = new DerInputStream(buf);
        if (dis == null) {
            throw new AssertionError("DerInputStream instance is null");
        }
        if (dis.tag != 0x30) {
            throw new AssertionError("Unexpected tag: " + dis.tag);
        }
    }
}
