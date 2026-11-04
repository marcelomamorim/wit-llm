/*
 * @test
 * @summary Scanner(Path, Charset) reads UTF-8 tokens from a file and reports a missing file via IOException
 * @run main ScannerWitupTest
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ScannerWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Path tmp = Files.createTempFile("scanner-witup", ".txt");
        try {
            Files.write(tmp, "\u00e9 42\nline two\n".getBytes(StandardCharsets.UTF_8));
            try (Scanner sc = new Scanner(tmp, StandardCharsets.UTF_8)) {
                check("\u00e9".equals(sc.next()), "first token should be the UTF-8 decoded character");
                check(sc.nextInt() == 42, "second token should be 42");
                check("".equals(sc.nextLine()), "rest of the first line should be empty");
                check("line two".equals(sc.nextLine()), "second line should be 'line two'");
                check(!sc.hasNextLine(), "no more lines should remain");
            }
        } finally {
            Files.deleteIfExists(tmp);
        }

        Path missing = Files.createTempFile("scanner-witup-missing", ".txt");
        Files.delete(missing);
        boolean threw = false;
        try {
            new Scanner(missing, StandardCharsets.UTF_8).close();
        } catch (IOException expected) {
            threw = true;
        }
        check(threw, "opening a nonexistent file should throw IOException");
    }
}
