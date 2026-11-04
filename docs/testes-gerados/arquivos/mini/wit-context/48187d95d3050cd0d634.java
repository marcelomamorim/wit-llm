/*
 * @test
 * @summary Test ZipOutputStream(OutputStream) constructor behavior
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;
import java.nio.charset.StandardCharsets;

public class ZipOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        // Test normal construction with a valid OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        if (zos == null) {
            throw new AssertionError("ZipOutputStream constructor returned null");
        }
        // The constructor delegates to ZipOutputStream(OutputStream, Charset) with StandardCharsets.UTF_8
        // We test that StandardCharsets.UTF_8 is not null (sanity check)
        if (StandardCharsets.UTF_8 == null) {
            throw new AssertionError("StandardCharsets.UTF_8 is null, unexpected environment");
        }
        zos.close();

        // Test that passing null OutputStream throws NullPointerException
        boolean npeThrown = false;
        try {
            new ZipOutputStream(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null OutputStream");
        }
    }
}
