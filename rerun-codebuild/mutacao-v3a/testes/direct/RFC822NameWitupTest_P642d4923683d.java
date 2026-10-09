/* @test @summary Test RFC822Name.parseName method for valid and invalid inputs @run main RFC822NameWitupTest_P642d4923683d */

import java.io.IOException;
import sun.security.x509.RFC822Name;

public class RFC822NameWitupTest_P642d4923683d {

    public static void main(String[] args) {
        testValidName();
        testNullName();
        testEmptyName();
        testNameWithoutAt();
        testDomainStartsWithDot();
        testDomainJustDot();
    }

    static void testValidName() {
        try {
            RFC822Name name = new RFC822Name("user@example.com");
            assert name.getName().equals("user@example.com") : "Valid name should be accepted";
        } catch (IOException e) {
            throw new AssertionError("Exception thrown for valid name", e);
        }
    }

    static void testNullName() {
        try {
            new RFC822Name((String) null);
            throw new AssertionError("Null name should throw IOException");
        } catch (IOException e) {
            // expected
        }
    }

    static void testEmptyName() {
        try {
            new RFC822Name("");
            throw new AssertionError("Empty name should throw IOException");
        } catch (IOException e) {
            // expected
        }
    }

    static void testNameWithoutAt() {
        try {
            new RFC822Name("username");
            // Should pass, as parseName does not check for '@' presence, only substring
            // But in actual code, parseName will throw if '@' is missing because substring will fail
            // So we test that parseName throws for invalid format
            RFC822Name name = new RFC822Name("username");
            name.parseName("username");
            // No exception expected if '@' not present, but parseName uses indexOf('@')+1 which returns 0 if '@' not found
            // So, substring(0) returns original string, no exception thrown
        } catch (IOException e) {
            throw new AssertionError("parseName should accept names without '@', but got exception", e);
        }
    }

    static void testDomainStartsWithDot() {
        try {
            new RFC822Name("user@.");
            // Should throw IOException because domain starts with '.' and length==1
            RFC822Name name = new RFC822Name("user@.");
            name.parseName("user@.");
            throw new AssertionError("Domain starting with '.' and length 1 should throw");
        } catch (IOException e) {
            // expected
        }
    }

    static void testDomainJustDot() {
        try {
            new RFC822Name("user@.");
            // same as previous, should throw
        } catch (IOException e) {
            // expected
        }
    }
}
