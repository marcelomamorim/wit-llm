/*
 * @test
 * @summary Test ZipOutputStream.setLevel(int) method
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.Deflater;

public class ZipOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        // Test setting valid compression levels
        for (int level = Deflater.NO_COMPRESSION; level <= Deflater.BEST_COMPRESSION; level++) {
            zos.setLevel(level);
        }

        // Test setting an invalid compression level (less than NO_COMPRESSION)
        zos.setLevel(-1); // Should not throw, Deflater.setLevel accepts it but clamps internally

        // Test setting an invalid compression level (greater than BEST_COMPRESSION)
        zos.setLevel(10); // Should not throw, Deflater.setLevel accepts it but clamps internally

        zos.close();
    }
}
