/*
 * @test
 * @summary Tests for sun.security.x509.RFC822Name.parseName(String)
 * @run main RFC822NameWitupTest_P642d4923683d
 */

import java.io.IOException;
import sun.security.x509.RFC822Name;

public class RFC822NameWitupTest_P642d4923683d {

    public static void main(String[] args) {
        testParseName_nullOrEmpty();
        testParseName_missingAt();
        testParseName_domainStartsWithDot();
        testParseName_domainJustDot();
        testParseName_validName();
    }

    private static void testParseName_nullOrEmpty() {
        try {
            new RFC822Name((String) null);
            System.err.println("Failed: Expected IOException for null name");
        } catch (IOException e) {
            // Expected
        }
        try {
            new RFC822Name("");
            System.err.println("Failed: Expected IOException for empty name");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testParseName_missingAt() {
        try {
            RFC822Name name = new RFC822Name("userexample.com");
            // No exception expected, but parseName does not throw here
            // because constructor calls parseName, which may throw
            // so no check needed
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for valid name without @");
        }
    }

    private static void testParseName_domainStartsWithDot() {
        try {
            new RFC822Name("user@.domain.com");
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for domain starting with dot");
        }
        try {
            new RFC822Name("user@.");
            System.err.println("Failed: Expected IOException for domain just dot");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testParseName_validName() {
        try {
            RFC822Name name = new RFC822Name("user@domain.com");
            assert "user@domain.com".equals(name.getName());
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for valid name");
        }
    }
}
