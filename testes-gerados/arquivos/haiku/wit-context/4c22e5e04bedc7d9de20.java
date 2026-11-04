/*
 * @test
 * @summary Verify Scanner(Path, Charset) rejects a null charset and reads a file with a valid charset
 * @run main ScannerWitupTest
 */

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        Path tmp = Files.createTempFile("ScannerWitupTest", ".txt");
        try {
            Files.write(tmp, "hello 42".getBytes(StandardCharsets.UTF_8));

            Charset nullCharset = null;
            boolean npe = false;
            try {
                Scanner sc = new Scanner(tmp, nullCharset);
                sc.close();
            } catch (NullPointerException e) {
                npe = true;
            }
            if (!npe) {
                throw new RuntimeException("Expected NullPointerException for null charset");
            }

            try (Scanner sc = new Scanner(tmp, StandardCharsets.UTF_8)) {
                String word = sc.next();
                if (!"hello".equals(word)) {
                    throw new RuntimeException("Unexpected token: " + word);
                }
                int value = sc.nextInt();
                if (value != 42) {
                    throw new RuntimeException("Unexpected int: " + value);
                }
            }
        } finally {
            Files.deleteIfExists(tmp);
        }
    }
}
