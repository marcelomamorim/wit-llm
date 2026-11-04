/* @test    @summary Test for Scanner(Path, Charset) constructor handling null charset.    @run main ScannerWitupTest */

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class ScannerWitupTest {

    public static void main(String[] args) throws IOException {
        testNullCharsetThrowsNullPointerException();
        testValidPathAndCharset();
    }

    private static void testNullCharsetThrowsNullPointerException() {
        Path path = Paths.get("/tmp/testfile");
        Charset charset = null;
        try {
            new java.util.Scanner(path, charset);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testValidPathAndCharset() throws IOException {
        Path path = Files.createTempFile("testfile", ".txt");
        Files.write(path, "sample text".getBytes());
        Charset charset = StandardCharsets.UTF_8;
        try {
            java.util.Scanner scanner = new java.util.Scanner(path, charset);
            if (scanner != null) {
                System.out.println("PASS: Scanner instantiated successfully");
                scanner.close();
            } else {
                System.out.println("FAIL: Scanner is null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during instantiation: " + e);
        } finally {
            Files.deleteIfExists(path);
        }
    }
}
