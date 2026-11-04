/*
 * @test
 * @summary Behavioral tests for Files.readString(Path, Charset)
 * @run main FilesWitupTest
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Check failed: " + msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Path p = Files.createTempFile("witup", ".txt");
        try {
            String s = "h\u00e9llo w\u00f6rld";

            Files.write(p, s.getBytes(StandardCharsets.UTF_8));
            check(s.equals(Files.readString(p, StandardCharsets.UTF_8)),
                  "UTF-8 round trip");

            Files.write(p, new byte[0]);
            check("".equals(Files.readString(p, StandardCharsets.UTF_8)),
                  "empty file returns empty string");

            Files.write(p, s.getBytes(StandardCharsets.UTF_16));
            check(s.equals(Files.readString(p, StandardCharsets.UTF_16)),
                  "UTF-16 round trip");

            boolean npePath = false;
            try {
                Files.readString(null, StandardCharsets.UTF_8);
            } catch (NullPointerException e) {
                npePath = true;
            }
            check(npePath, "null path must throw NullPointerException");

            boolean npeCs = false;
            try {
                Files.readString(p, null);
            } catch (NullPointerException e) {
                npeCs = true;
            }
            check(npeCs, "null charset must throw NullPointerException");

            Files.write(p, new byte[] {(byte) 0xC3, (byte) 0x28});
            boolean ioMalformed = false;
            try {
                Files.readString(p, StandardCharsets.UTF_8);
            } catch (IOException e) {
                ioMalformed = true;
            }
            check(ioMalformed, "malformed UTF-8 must throw IOException");

            Files.deleteIfExists(p);
            boolean ioMissing = false;
            try {
                Files.readString(p, StandardCharsets.UTF_8);
            } catch (IOException e) {
                ioMissing = true;
            }
            check(ioMissing, "missing file must throw IOException");
        } finally {
            Files.deleteIfExists(p);
        }
    }
}
