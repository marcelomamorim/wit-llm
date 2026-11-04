/* @test    @summary Test constructor of ZipOutputStream with OutputStream parameter    @run main ZipOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;
import java.io.IOException;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithOutputStream();
    }

    public static void testConstructorWithOutputStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(baos);
            if (zos == null) {
                throw new AssertionError("ZipOutputStream instance is null");
            }
        } catch (IOException e) {
            throw new AssertionError("IOException thrown during ZipOutputStream creation: " + e.getMessage());
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}