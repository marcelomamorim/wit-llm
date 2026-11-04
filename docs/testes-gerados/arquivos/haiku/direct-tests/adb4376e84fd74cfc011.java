/*
 * @test
 * @summary Verifies Formatter(File) writes formatted output to the file, truncates existing content, and reports FileNotFoundException for an unreachable path.
 * @run main FormatterWitupTest
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Formatter;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("fmtwitup", ".txt");
        try {
            try (Formatter fmt = new Formatter(f)) {
                fmt.format("%s-%d", "a", 7);
            }
            String s = new String(Files.readAllBytes(f.toPath()), StandardCharsets.US_ASCII);
            if (!s.equals("a-7")) {
                throw new RuntimeException("unexpected content: " + s);
            }

            Files.write(f.toPath(), "old-long-content-to-be-truncated".getBytes(StandardCharsets.US_ASCII));
            try (Formatter fmt = new Formatter(f)) {
                fmt.format("new");
            }
            s = new String(Files.readAllBytes(f.toPath()), StandardCharsets.US_ASCII);
            if (!s.equals("new")) {
                throw new RuntimeException("existing content not truncated: " + s);
            }
        } finally {
            f.delete();
        }

        File missing = new File(File.createTempFile("fmtwitupdir", "").getAbsolutePath()
                + File.separator + "no-such-dir-witup" + File.separator + "out.txt");
        try {
            new Formatter(missing);
            throw new RuntimeException("expected FileNotFoundException");
        } catch (FileNotFoundException expected) {
            // expected
        }
    }
}
