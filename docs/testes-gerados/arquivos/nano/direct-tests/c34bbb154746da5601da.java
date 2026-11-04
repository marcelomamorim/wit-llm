/* @test    @summary Test Normalizer2.getNFKDInstance() returns a non-null instance    @run main Normalizer2WitupTest */

import sun.text.normalizer.Normalizer2;

public class Normalizer2WitupTest {

    public static void main(String[] args) {
        testGetNFKDInstanceNotNull();
        testGetNFKDInstanceConsistent();
    }

    private static void testGetNFKDInstanceNotNull() {
        Normalizer2 instance = Normalizer2.getNFKDInstance();
        if (instance == null) {
            throw new AssertionError("Normalizer2.getNFKDInstance() returned null");
        }
    }

    private static void testGetNFKDInstanceConsistent() {
        Normalizer2 firstInstance = Normalizer2.getNFKDInstance();
        Normalizer2 secondInstance = Normalizer2.getNFKDInstance();
        if (firstInstance != secondInstance) {
            throw new AssertionError("Multiple calls to getNFKDInstance() returned different instances");
        }
    }
}
