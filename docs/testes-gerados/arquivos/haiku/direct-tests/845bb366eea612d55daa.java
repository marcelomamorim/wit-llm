/*
 * @test
 * @summary Tests File.getCanonicalPath for relative, idempotent and invalid (NUL-containing) paths
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    public static void main(String[] args) throws Exception {
        testDotResolvesToUserDir();
        testCanonicalIsIdempotent();
        testNulPathThrowsIOException();
    }

    static void testDotResolvesToUserDir() throws IOException {
        String expected = new File(System.getProperty("user.dir")).getCanonicalPath();
        String actual = new File(".").getCanonicalPath();
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    static void testCanonicalIsIdempotent() throws IOException {
        String first = new File(".").getCanonicalPath();
        String second = new File(first).getCanonicalPath();
        if (!first.equals(second)) {
            throw new RuntimeException("Canonical path not idempotent: " + first + " vs " + second);
        }
    }

    static void testNulPathThrowsIOException() {
        File f = new File("abc\u0000def");
        try {
            f.getCanonicalPath();
        } catch (IOException e) {
            if (!"Invalid file path".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected IOException for invalid path");
    }
}
