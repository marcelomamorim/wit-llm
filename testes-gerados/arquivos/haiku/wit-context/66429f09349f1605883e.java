/*
 * @test
 * @summary WITUP-derived checks for RFC822Name construction from String and parseName validation
 * @modules java.base/sun.security.x509
 * @run main RFC822NameWitupTest
 */

import java.io.IOException;

import sun.security.x509.GeneralNameInterface;
import sun.security.x509.RFC822Name;

public class RFC822NameWitupTest {

    static int failures = 0;

    static void expectIOException(String input) {
        try {
            new RFC822Name(input);
            failures++;
            System.err.println("Expected IOException for input: " + input);
        } catch (IOException e) {
            // expected
        }
    }

    static void checkValid(String input) throws IOException {
        RFC822Name n = new RFC822Name(input);
        if (!input.equals(n.getName())) {
            failures++;
            System.err.println("getName mismatch for: " + input);
        }
        if (n.getType() != GeneralNameInterface.NAME_RFC822) {
            failures++;
            System.err.println("getType mismatch for: " + input);
        }
    }

    public static void main(String[] args) throws IOException {
        expectIOException(null);
        expectIOException("");
        expectIOException("user@");
        expectIOException("@.");
        expectIOException("user@.");

        checkValid("user@example.com");
        checkValid("example.com");
        checkValid("@example.com");

        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }
}
