/*
 * @test
 * @summary Tests for sun.security.x509.RFC822Name.parseName(String)
 * @run main RFC822NameWitupTest_P9a2a2fca2652
 */

import java.io.IOException;
import sun.security.x509.RFC822Name;

public class RFC822NameWitupTest_P9a2a2fca2652 {

    public static void main(String[] args) {
        testParseNameNullOrEmpty();
        testParseNameMissingAt();
        testParseNameDomainStartsWithDot();
        testParseNameDomainJustDot();
        testParseNameValid();
    }

    public static void testParseNameNullOrEmpty() {
        try {
            new RFC822Name((String) null);
            throw new AssertionError("Expected IOException for null name");
        } catch (IOException e) {
            // expected
        }
        try {
            new RFC822Name("");
            throw new AssertionError("Expected IOException for empty name");
        } catch (IOException e) {
            // expected
        }
    }

    public static void testParseNameMissingAt() {
        try {
            new RFC822Name("userexample.com");
            // Should not throw, as parseName does not validate '@' presence explicitly
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    public static void testParseNameDomainStartsWithDot() {
        try {
            new RFC822Name("user@.");
            throw new AssertionError("Expected IOException for domain starting with dot");
        } catch (IOException e) {
            // expected
        }
    }

    public static void testParseNameDomainJustDot() {
        try {
            new RFC822Name("user@.");
            throw new AssertionError("Expected IOException for domain just dot");
        } catch (IOException e) {
            // expected
        }
    }

    public static void testParseNameValid() {
        try {
            RFC822Name name = new RFC822Name("user@domain.com");
            if (!"user@domain.com".equals(name.getName())) {
                throw new AssertionError("Name mismatch");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
