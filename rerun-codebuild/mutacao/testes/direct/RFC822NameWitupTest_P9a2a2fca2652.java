/* @test    @summary Test parseName method of RFC822Name for valid and invalid inputs    @run main RFC822NameWitupTest_P9a2a2fca2652 */

import java.io.IOException;
import sun.security.x509.RFC822Name;

public class RFC822NameWitupTest_P9a2a2fca2652 {

    public static void main(String[] args) {
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
            assert "user@example.com".equals(name.getName()) : "Valid name should be accepted";
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception for valid name", e);
        }
    }

    static void testNullName() {
        try {
            new RFC822Name((String) null);
            throw new AssertionError("Expected IOException for null name");
        } catch (IOException e) {
            assert e.getMessage().equals("RFC822Name may not be null or empty") : "Incorrect message for null name";
        }
    }

    static void testEmptyName() {
        try {
            new RFC822Name("");
            throw new AssertionError("Expected IOException for empty name");
        } catch (IOException e) {
            assert e.getMessage().equals("RFC822Name may not be null or empty") : "Incorrect message for empty name";
        }
    }

    static void testNameEndingWithAt() {
        try {
            new RFC822Name("user@");
            throw new AssertionError("Expected IOException for name ending with @");
        } catch (IOException e) {
            assert e.getMessage().equals("RFC822Name may not end with @") : "Incorrect message for name ending with @";
        }
    }

    static void testDomainStartingWithDot() {
        try {
            new RFC822Name("user@.example.com");
            // Should be accepted as parseName does not throw for starting with '.'
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception for domain starting with dot", e);
        }
    }

    static void testDomainJustDot() {
        try {
            new RFC822Name("user@.");
            throw new AssertionError("Expected IOException for domain just dot");
        } catch (IOException e) {
            assert e.getMessage().equals("RFC822Name domain may not be just .") : "Incorrect message for domain just dot";
        }
    }
}
