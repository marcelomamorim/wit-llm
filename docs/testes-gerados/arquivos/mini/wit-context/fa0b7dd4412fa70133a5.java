/*
 * @test
 * @summary Test sun.text.normalizer.Normalizer2.getNFDInstance() for expected behavior and exception path
 * @run main Normalizer2WitupTest
 */

import sun.text.normalizer.Normalizer2;
import sun.text.normalizer.Norm2AllModes;

public class Normalizer2WitupTest {
    public static void main(String[] args) {
        // Test that getNFDInstance() returns a non-null Normalizer2 instance
        Normalizer2 nfd = Normalizer2.getNFDInstance();
        if (nfd == null) {
            throw new AssertionError("getNFDInstance() returned null");
        }

        // Test that the returned instance is the decomp Normalizer2 from Norm2AllModes.getNFCInstance()
        Normalizer2 nfcInstance = Norm2AllModes.getNFCInstance();
        if (nfd != nfcInstance.decomp) {
            throw new AssertionError("getNFDInstance() did not return the expected decomp instance");
        }

        // The WIT expath suggests an exception can be thrown if NFCSingleton.INSTANCE.exception != null
        // However, this is internal state and no public API to set it, so we cannot test the exception path directly.
        // This expath is noted but not tested here due to lack of public access.

        System.out.println("Normalizer2.getNFDInstance() test passed");
    }
}
