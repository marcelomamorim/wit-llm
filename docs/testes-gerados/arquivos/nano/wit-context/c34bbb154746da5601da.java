/* @test    @summary Test for Normalizer2.getNFKDInstance() method.    @run main Normalizer2WitupTest */

import sun.text.normalizer.Normalizer2;

public class Normalizer2WitupTest {

    public static void main(String[] args) {
        testGetNFKDInstanceReturnsNonNull();
    }

    public static void testGetNFKDInstanceReturnsNonNull() {
        Normalizer2 result = Normalizer2.getNFKDInstance();
        if (result == null) {
            throw new AssertionError("Expected non-null result from getNFKDInstance()");
        }
    }
}
