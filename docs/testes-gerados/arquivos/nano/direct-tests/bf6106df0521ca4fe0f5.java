/* @test    @summary Test ObjectOutputStream constructor with valid OutputStream    @run main ObjectOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithValidOutputStream();
    }

    public static void testConstructorWithValidOutputStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Verify that the stream header is written (non-empty byte array)
        byte[] headerBytes = baos.toByteArray();
        assert headerBytes.length > 0 : "Stream header should be written";
        oos.close();
    }
}
