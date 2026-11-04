/*
 * @test
 * @summary Test sun.text.normalizer.Norm2AllModes.getNFCInstance() returns non-null singleton instance
 * @run main Norm2AllModesWitupTest
 */

public class Norm2AllModesWitupTest {
    public static void main(String[] args) {
        sun.text.normalizer.Norm2AllModes instance1 = sun.text.normalizer.Norm2AllModes.getNFCInstance();
        if (instance1 == null) {
            throw new AssertionError("getNFCInstance() returned null");
        }
        sun.text.normalizer.Norm2AllModes instance2 = sun.text.normalizer.Norm2AllModes.getNFCInstance();
        if (instance1 != instance2) {
            throw new AssertionError("getNFCInstance() did not return the same singleton instance on repeated calls");
        }
    }
}
