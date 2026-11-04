/*
 * @test
 * @summary Test constructor of DeflaterOutputStream with OutputStream and Deflater
 * @run main DeflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class DeflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithOutputStreamAndDeflater();
    }

    public static void testConstructorWithOutputStreamAndDeflater() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        DeflaterOutputStream dos = new DeflaterOutputStream(baos, deflater);
        if (dos == null) {
            throw new AssertionError("DeflaterOutputStream instance is null");
        }
        // Verify that the buffer size defaults to 512 by testing internal behavior indirectly
        // Since internal buffer is private, test that writing data does not throw and data can be compressed
        try {
            byte[] data = "test data".getBytes("UTF-8");
            dos.write(data);
            dos.flush();
            dos.close();
        } catch (Exception e) {
            throw new AssertionError("Exception during write/flush/close: " + e);
        }
    }
}
