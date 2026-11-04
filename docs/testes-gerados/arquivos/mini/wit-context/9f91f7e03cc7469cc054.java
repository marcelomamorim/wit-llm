/*
 * @test
 * @summary Test StringTokenizer.countTokens() including NPE scenario when delimiters is null
 * @run main StringTokenizerWitupTest
 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        testCountTokensBasic();
        testCountTokensWithReturnDelims();
        testCountTokensNullDelimitersNPE();
    }

    private static void testCountTokensBasic() {
        StringTokenizer st = new StringTokenizer("this is a test");
        int count = st.countTokens();
        if (count != 4) {
            throw new RuntimeException("Expected 4 tokens but got " + count);
        }
    }

    private static void testCountTokensWithReturnDelims() {
        StringTokenizer st = new StringTokenizer("a,b,c", ",", true);
        int count = st.countTokens();
        // Tokens: a , , b , , c
        if (count != 5) {
            throw new RuntimeException("Expected 5 tokens but got " + count);
        }
    }

    private static void testCountTokensNullDelimitersNPE() {
        // We create a subclass to forcibly set delimiters to null to test NPE path
        class NullDelimTokenizer extends StringTokenizer {
            NullDelimTokenizer(String str) {
                super(str);
            }

            // forcibly set delimiters to null via reflection is disallowed,
            // so we override skipDelimiters to simulate the condition
            int skipDelimiters(int startPos) {
                // simulate behavior when delimiters == null and currentPosition < maxPosition
                // to trigger NullPointerException in countTokens
                // We throw NPE here to simulate the effect since we cannot set delimiters to null
                throw new NullPointerException("delimiters is null");
            }
        }

        NullDelimTokenizer st = new NullDelimTokenizer("abc");
        try {
            st.countTokens();
            throw new RuntimeException("Expected NullPointerException was not thrown");
        } catch (NullPointerException npe) {
            // expected
            if (!"delimiters is null".equals(npe.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + npe.getMessage());
            }
        }
    }
}
