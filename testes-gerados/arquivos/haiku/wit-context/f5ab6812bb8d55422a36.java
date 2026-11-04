/*
 * @test
 * @summary Verify Files.copy(Path, OutputStream) copies bytes, rejects null output and reports missing sources
 * @run main FilesWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;

public class FilesWitupTest {

    public static void main(String[] args) throws Exception {
        testCopyWritesAllBytes();
        testNullOutputThrowsNPE();
        testMissingSourceThrowsNoSuchFile();
    }

    static void testCopyWritesAllBytes() throws Exception {
        Path tmp = Files.createTempFile("witup", ".bin");
        try {
            byte[] data = new byte[]{1, 2, 3, 4, 5, (byte) 0xFF};
            Files.write(tmp, data);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            long n = Files.copy(tmp, out);
            if (n != data.length) {
                throw new RuntimeException("unexpected count: " + n);
            }
            if (!Arrays.equals(data, out.toByteArray())) {
                throw new RuntimeException("copied content differs");
            }
        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    static void testNullOutputThrowsNPE() throws Exception {
        Path tmp = Files.createTempFile("witup", ".bin");
        try {
            Files.write(tmp, new byte[]{1});
            try {
                Files.copy(tmp, null);
                throw new RuntimeException("NullPointerException expected");
            } catch (NullPointerException expected) {
                // expected
            }
        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    static void testMissingSourceThrowsNoSuchFile() throws Exception {
        Path tmp = Files.createTempFile("witup", ".bin");
        Files.delete(tmp);
        try {
            Files.copy(tmp, new ByteArrayOutputStream());
            throw new RuntimeException("NoSuchFileException expected");
        } catch (NoSuchFileException expected) {
            // expected
        }
    }
}
