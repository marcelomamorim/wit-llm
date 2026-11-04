/*
 * @test
 * @summary Tests for the Formatter(String fileName) constructor: valid file output, null file name, and missing parent directory.
 * @run main FormatterWitupTest
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Formatter;

public class FormatterWitupTest {

    private static final String VALID_NAME = "formatter-witup-test-output.txt";
    private static final String MISSING_DIR_NAME = "witup-missing-dir/out.txt";

    public static void main(String[] args) throws Exception {
        testWritesToFile();
        testNullFileNameThrowsNPE();
        testMissingDirectoryThrowsFileNotFound();
    }

    private static void testWritesToFile() throws Exception {
        File f = new File(VALID_NAME);
        if (f.exists()) {
            f.delete();
        }
        Formatter formatter = new Formatter(VALID_NAME);
        try {
            formatter.format("%d-%s", 42, "ok");
            formatter.flush();
        } finally {
            formatter.close();
        }
        String content = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
        if (!"42-ok".equals(content)) {
            throw new RuntimeException("Unexpected file content: " + content);
        }
        f.delete();
    }

    private static void testNullFileNameThrowsNPE() {
        try {
            new Formatter((String) null);
        } catch (NullPointerException expected) {
            return;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Expected NullPointerException, got FileNotFoundException", e);
        }
        throw new RuntimeException("Expected NullPointerException for null fileName");
    }

    private static void testMissingDirectoryThrowsFileNotFound() {
        try {
            Formatter formatter = new Formatter(MISSING_DIR_NAME);
            formatter.close();
        } catch (FileNotFoundException expected) {
            return;
        }
        throw new RuntimeException("Expected FileNotFoundException for missing directory");
    }
}
