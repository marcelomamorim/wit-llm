/* @test    @summary Test Normalizer2.getNFDInstance() returns a non-null Normalizer2 instance    @run main Normalizer2WitupTest */

import sun.text.normalizer.Normalizer2;

public class Normalizer2WitupTest {

    public static void main(String[] args) {
        testGetNFDInstanceNotNull();
        testGetNFDInstanceConsistency();
    }

    private static void testGetNFDInstanceNotNull() {
        Normalizer2 nfdInstance = Normalizer2.getNFDInstance();
        if (nfdInstance == null) {
            throw new AssertionError("Normalizer2.getNFDInstance() returned null");
        }
    }

    private static void testGetNFDInstanceConsistency() {
        Normalizer2 firstCall = Normalizer2.getNFDInstance();
        Normalizer2 secondCall = Normalizer2.getNFDInstance();
        if (firstCall != secondCall) {
            throw new AssertionError("Multiple calls to getNFDInstance() did not return the same instance");
        }
    }
}
