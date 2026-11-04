/*
 * @test
 * @summary Checks public Formatter(File) constructor: writes formatted output to file, and fails for missing parent directory and null file
 * @run main FormatterWitupTest
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Formatter;

public class FormatterWitupTest {

    public static void main(String[] args) throws Exception {
        File dir = Files.createTempDirectory("fmtwitup").toFile();
        try {
            File out = new File(dir, "out.txt");
            Formatter f = new Formatter(out);
            f.format("%d-%s", 42, "ok");
            f.close();
            String s = new String(Files.readAllBytes(out.toPath()), StandardCharsets.US_ASCII);
            if (!"42-ok".equals(s)) {
                throw new RuntimeException("unexpected content: " + s);
            }
            out.delete();

            File missing = new File(dir, "missing" + File.separator + "x.txt");
            try {
                new Formatter(missing);
                throw new RuntimeException("FileNotFoundException expected");
            } catch (FileNotFoundException expected) {
                // expected
            }

            try {
                new Formatter((File) null);
                throw new RuntimeException("NullPointerException expected");
            } catch (NullPointerException expected) {
                // expected
            }
        } finally {
            dir.delete();
        }
    }
}
