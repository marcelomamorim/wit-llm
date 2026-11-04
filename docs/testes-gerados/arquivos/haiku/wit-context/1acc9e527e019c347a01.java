/*
 * @test
 * @summary Witup generated tests for ZipOutputStream.finish()
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testFinishOnEmptyStreamWritesEndRecord();
        testFinishIsIdempotent();
        testFinishAfterCloseThrowsIOException();
    }

    static void testFinishOnEmptyStreamWritesEndRecord() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.finish();
        byte[] b = bos.toByteArray();
        if (b.length != 22) {
            throw new RuntimeException("Expected 22 bytes for empty zip, got " + b.length);
        }
        if (b[0] != 0x50 || b[1] != 0x4b || b[2] != 0x05 || b[3] != 0x06) {
            throw new RuntimeException("Missing END signature");
        }
    }

    static void testFinishIsIdempotent() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.finish();
        int firstLength = bos.toByteArray().length;
        zos.finish();
        int secondLength = bos.toByteArray().length;
        if (firstLength != secondLength) {
            throw new RuntimeException("Second finish() changed output: "
                    + firstLength + " -> " + secondLength);
        }
    }

    static void testFinishAfterCloseThrowsIOException() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.close();
        try {
            zos.finish();
            throw new RuntimeException("Expected IOException from finish() after close()");
        } catch (IOException expected) {
        }
    }
}
