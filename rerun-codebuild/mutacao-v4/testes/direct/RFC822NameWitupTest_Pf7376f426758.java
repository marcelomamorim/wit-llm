/* @test
   @summary Tests the parseName method of sun.security.x509.RFC822Name.
   @run main RFC822NameWitupTest_Pf7376f426758
 */
import java.io.IOException;
import sun.security.x509.RFC822Name;

public class RFC822NameWitupTest_Pf7376f426758 {
    public static void main(String[] args) {
        testValidNames();
        testNullName();
        testEmptyName();
        testNameEndingWithAt();
        testDomainStartingWithDot();
        testDomainJustDot();
    }

    static void testValidNames() {
        String[] validNames = {"user@domain.com", "@domain.com", "user@.domain.com"};
        for (String name : validNames) {
            try {
                new RFC822Name(name).parseName(name);
            } catch (IOException e) {
                throw new AssertionError("Valid name failed: " + name, e);
            }
        }
    }

    static void testNullName() {
        try {
            new RFC822Name((String) null).parseName(null);
            throw new AssertionError("Expected IOException for null name");
        } catch (IOException e) {
            // expected
        }
    }

    static void testEmptyName() {
        try {
            new RFC822Name("").parseName("");
            throw new AssertionError("Expected IOException for empty name");
        } catch (IOException e) {
            // expected
        }
    }

    static void testNameEndingWithAt() {
        try {
            new RFC822Name("user@").parseName("user@");
            throw new AssertionError("Expected IOException for name ending with @");
        } catch (IOException e) {
            // expected
        }
    }

    static void testDomainStartingWithDot() {
        try {
            new RFC822Name("user@.").parseName("user@.");
        } catch (IOException e) {
            throw new AssertionError("Valid domain starting with . should not throw", e);
        }
    }

    static void testDomainJustDot() {
        try {
            new RFC822Name("user@.").parseName("user@.");
        } catch (IOException e) {
            throw new AssertionError("Valid domain starting with . should not throw", e);
        }
    }
}
