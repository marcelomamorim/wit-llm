/*
 * @test
 * @summary Files.readString(Path) must decode the file as UTF-8, return empty string for empty files, match readString(path, UTF_8), and report IOException for malformed input or missing files
 * @run main FilesWitupTest
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Path file = Files.createTempFile("witupReadString", ".txt");
        try {
            String text = "héllo €\nline2";
            Files.write(file, text.getBytes(StandardCharsets.UTF_8));
            String read = Files.readString(file);
            check(text.equals(read), "readString should decode UTF-8 content: " + read);
            check(read.equals(Files.readString(file, StandardCharsets.UTF_8)),
                  "readString(path) should equal readString(path, UTF_8)");

            Files.write(file, new byte[0]);
            check("".equals(Files.readString(file)), "empty file should yield empty string");

            Files.write(file, new byte[] { (byte) 0xC3, (byte) 0x28 });
            boolean threw = false;
            try {
                Files.readString(file);
            } catch (IOException expected) {
                threw = true;
            }
            check(threw, "malformed UTF-8 input should raise IOException");
        } finally {
            Files.deleteIfExists(file);
        }

        Path missing = file.resolveSibling("witup-missing-" + System.nanoTime() + ".txt");
        boolean missingThrew = false;
        try {
            Files.readString(missing);
        } catch (IOException expected) {
            missingThrew = true;
        }
        check(missingThrew, "missing file should raise IOException");
    }
}
