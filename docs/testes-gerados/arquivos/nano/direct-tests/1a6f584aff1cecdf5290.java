/* @test    @summary Test setLevel method of ZipOutputStream for various levels    @run main ZipOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testSetLevelDefault();
        testSetLevelZero();
        testSetLevelMax();
        testSetLevelInvalid();
    }

    static void testSetLevelDefault() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        int defaultLevel = 6; // default compression level in ZipOutputStream
        zos.setLevel(defaultLevel);
        // Verify that setting to default does not throw and is accepted
        // Since no direct getter, test that no exception occurs
        zos.close();
    }

    static void testSetLevelZero() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        int level = 0;
        zos.setLevel(level);
        zos.close();
    }

    static void testSetLevelMax() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        int level = 9;
        zos.setLevel(level);
        zos.close();
    }

    static void testSetLevelInvalid() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ZipOutputStream zos = new ZipOutputStream(baos);
            int invalidLevel = -1;
            zos.setLevel(invalidLevel);
            zos.close();
        } catch (IllegalArgumentException | IOException e) {
            // Expected exception for invalid level
        }
    }
}
