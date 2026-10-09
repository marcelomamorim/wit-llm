/* @test    @summary Test parseName method of RFC822Name for valid and invalid inputs    @run main RFC822NameWitupTest_P002f15f58929 */

import java.io.IOException;
import sun.security.x509.RFC822Name;

public class RFC822NameWitupTest_P002f15f58929 {

    public static void main(String[] args) throws Exception {
        testValidName();
        testNullName();
        testEmptyName();
        testNameEndingWithAt();
        testDomainStartingWithDot();
        testDomainJustDot();
    }

    static void testValidName() {
        try {
            RFC822Name name = new RFC822Name("user@example.com");
            if (!"user@example.com".equals(name.getName())) {
                throw new AssertionError("Valid name did not match");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception for valid name", e);
        }
    }

    static void testNullName() {
        try {
            RFC822Name name = new RFC822Name((String) null);
            throw new AssertionError("Expected IOException for null name");
        } catch (IOException e) {
            if (!"RFC822Name may not be null or empty".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for null name", e);
            }
        }
    }

    static void testEmptyName() {
        try {
            RFC822Name name = new RFC822Name("");
            throw new AssertionError("Expected IOException for empty name");
        } catch (IOException e) {
            if (!"RFC822Name may not be null or empty".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for empty name", e);
            }
        }
    }

    static void testNameEndingWithAt() {
        try {
            RFC822Name name = new RFC822Name("user@");
            throw new AssertionError("Expected IOException for name ending with @");
        } catch (IOException e) {
            if (!"RFC822Name may not end with @".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for name ending with @", e);
            }
        }
    }

    static void testDomainStartingWithDot() {
        try {
            RFC822Name name = new RFC822Name("user@.");
            // Should succeed, as parseName only checks for domain starting with '.'
            if (!"user@.".equals(name.getName())) {
                throw new AssertionError("Name mismatch for domain starting with dot");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception for domain starting with dot", e);
        }
    }

    static void testDomainJustDot() {
        try {
            RFC822Name name = new RFC822Name("user@.");
            if (!"user@.".equals(name.getName())) {
                throw new AssertionError("Name mismatch for domain just dot");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception for domain just dot", e);
        }
    }
}
