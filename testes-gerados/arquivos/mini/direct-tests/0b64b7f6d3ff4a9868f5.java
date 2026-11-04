/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putNull method
 * @run main sun.security.util.DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putNull();
        byte[] result = dos.toByteArray();

        // Expected encoding for DER NULL is: tag_Null (0x05), length 0x00
        if (result.length != 2) {
            throw new RuntimeException("Unexpected length: " + result.length);
        }
        if (result[0] != DerValue.tag_Null) {
            throw new RuntimeException("Unexpected tag byte: " + (result[0] & 0xff));
        }
        if (result[1] != 0) {
            throw new RuntimeException("Unexpected length byte: " + (result[1] & 0xff));
        }
    }
}
