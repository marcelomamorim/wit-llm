/* @test    @summary Test for Normalizer2.getNFDInstance() method behavior.
 * @run main Normalizer2WitupTest
 */

import sun.text.normalizer.Normalizer2;

public class Normalizer2WitupTest {

    public static void main(String[] args) {
        testGetNFDInstanceReturnsNonNull();
        testGetNFDInstanceDoesNotThrow();
    }

    private static void testGetNFDInstanceReturnsNonNull() {
        Normalizer2 instance = Normalizer2.getNFDInstance();
        if (instance == null) {
            throw new AssertionError("Expected non-null Normalizer2 instance from getNFDInstance()");
        }
    }

    private static void testGetNFDInstanceDoesNotThrow() {
        try {
            Normalizer2 instance = Normalizer2.getNFDInstance();
            // No exception expected, just ensure method call is safe
        } catch (Throwable t) {
            throw new AssertionError("Method getNFDInstance() threw an exception: " + t);
        }
    }
}
