/*
 * @test
 * @summary Functional checks for Files.copy(Path, OutputStream)
 * @run main FilesWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;

public class FilesWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Path tmp = Files.createTempFile("filesWitup", ".bin");
        try {
            byte[] data = new byte[] {1, 2, 3, 4, 5, 0, (byte) 0xFF};
            Files.write(tmp, data);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            long n = Files.copy(tmp, out);
            check(n == data.length, "copy count mismatch: " + n);
            check(Arrays.equals(data, out.toByteArray()), "copied bytes differ");

            Files.write(tmp, new byte[0]);
            out.reset();
            n = Files.copy(tmp, out);
            check(n == 0, "empty file should copy 0 bytes");
            check(out.size() == 0, "empty file should write nothing");

            Files.write(tmp, data);
            ByteArrayOutputStream appended = new ByteArrayOutputStream();
            appended.write('X');
            Files.copy(tmp, appended);
            byte[] expected = new byte[data.length + 1];
            expected[0] = 'X';
            System.arraycopy(data, 0, expected, 1, data.length);
            check(Arrays.equals(expected, appended.toByteArray()), "copy should append to existing stream");

            try {
                Files.copy(tmp, null);
                throw new RuntimeException("expected NullPointerException for null output");
            } catch (NullPointerException expectedNpe) {
                // expected
            }

            Files.delete(tmp);
            try {
                Files.copy(tmp, new ByteArrayOutputStream());
                throw new RuntimeException("expected NoSuchFileException for missing file");
            } catch (NoSuchFileException expectedMissing) {
                // expected
            } catch (IOException e) {
                throw new RuntimeException("expected NoSuchFileException but got " + e, e);
            }
        } finally {
            Files.deleteIfExists(tmp);
        }
    }
}
