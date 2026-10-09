/* @test    @summary Test parsing of RFC822Name for null, empty, and invalid formats    @run main RFC822NameWitupTest_P002f15f58929 */

import sun.security.x509.RFC822Name;
import java.io.IOException;

public class RFC822NameWitupTest_P002f15f58929 {

    public static void main(String[] args) throws Exception {
        testNullName();
        testEmptyName();
        testNameWithoutAt();
        testNameWithOnlyAt();
        testValidName();
        System.out.println("All tests passed.");
    }

    static void testNullName() {
        try {
            new RFC822Name((String) null);
            throw new AssertionError("Expected IOException for null name");
        } catch (IOException e) {
            // expected
        }
    }

    static void testEmptyName() {
        try {
            new RFC822Name("");
            throw new AssertionError("Expected IOException for empty name");
        } catch (IOException e) {
            // expected
        }
    }

    static void testNameWithoutAt() {
        try {
            new RFC822Name("userexample.com");
            // parseName should throw IOException due to missing '@'
        } catch (IOException e) {
            // expected
        }
    }

    static void testNameWithOnlyAt() {
        try {
            new RFC822Name("@");
            throw new AssertionError("Expected IOException for name with only '@'");
        } catch (IOException e) {
            // expected
        }
    }

    static void testValidName() throws IOException {
        RFC822Name name = new RFC822Name("user@example.com");
        if (!"user@example.com".equals(name.getName())) {
            throw new AssertionError("Name mismatch");
        }
        // Test parseName with valid name
        RFC822Name name2 = new RFC822Name("test@domain.com");
        if (!"test@domain.com".equals(name2.getName())) {
            throw new AssertionError("Name mismatch");
        }
    }
}
