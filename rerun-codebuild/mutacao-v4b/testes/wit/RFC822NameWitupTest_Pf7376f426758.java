/* @test
   @summary Testa o método parseName de sun.security.x509.RFC822Name para validação de exceções.
   @run main RFC822NameWitupTest_Pf7376f426758
 */
import java.io.IOException;
import sun.security.x509.RFC822Name;

public class RFC822NameWitupTest_Pf7376f426758 {
    public static void main(String[] args) throws Exception {
        testParseNameNullOrEmpty();
        testParseNameMissingAt();
        testParseNameDomainJustDot();
    }

    private static void testParseNameNullOrEmpty() {
        try {
            RFC822Name name = new RFC822Name("");
            throw new AssertionError("Expected IOException for empty name");
        } catch (IOException e) {
            // expected
        }
        try {
            RFC822Name name = new RFC822Name(null);
            throw new AssertionError("Expected IOException for null name");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testParseNameMissingAt() {
        String invalidName = "userexample.com";
        try {
            RFC822Name name = new RFC822Name(invalidName);
            // No exception expected here, parseName does not throw for missing '@'
            // but the domain extraction will fail if '@' is missing
            // So, test that getName() returns the original string
            if (!name.getName().equals(invalidName)) {
                throw new AssertionError("Name mismatch");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for name without '@'");
        }
    }

    private static void testParseNameDomainJustDot() {
        String invalidName = "user@.";
        try {
            RFC822Name name = new RFC822Name(invalidName);
            // parseName does not throw, but domain starts with '.'
            if (!name.getName().equals(invalidName)) {
                throw new AssertionError("Name mismatch");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for domain starting with '.'");
        }
    }
}
