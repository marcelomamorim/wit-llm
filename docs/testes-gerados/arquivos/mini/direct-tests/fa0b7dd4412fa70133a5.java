/*
 * @test
 * @summary Test sun.text.normalizer.Normalizer2.getNFDInstance() returns a non-null instance
 * @run main Normalizer2WitupTest
 */

public class Normalizer2WitupTest {
    public static void main(String[] args) {
        sun.text.normalizer.Normalizer2 nfd = sun.text.normalizer.Normalizer2.getNFDInstance();
        if (nfd == null) {
            throw new AssertionError("getNFDInstance() returned null");
        }
        // Test that the instance is consistent: calling again returns the same instance
        sun.text.normalizer.Normalizer2 nfd2 = sun.text.normalizer.Normalizer2.getNFDInstance();
        if (nfd != nfd2) {
            throw new AssertionError("getNFDInstance() did not return a singleton instance");
        }
    }
}
