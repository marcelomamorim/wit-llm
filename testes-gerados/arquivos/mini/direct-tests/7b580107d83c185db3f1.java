/*
 * @test
 * @summary Test sun.text.normalizer.Norm2AllModes.getNFKCInstance() returns consistent singleton instance
 * @run main Norm2AllModesWitupTest
 */

public class Norm2AllModesWitupTest {
    public static void main(String[] args) {
        sun.text.normalizer.Norm2AllModes instance1 = sun.text.normalizer.Norm2AllModes.getNFKCInstance();
        sun.text.normalizer.Norm2AllModes instance2 = sun.text.normalizer.Norm2AllModes.getNFKCInstance();

        if (instance1 == null) {
            throw new RuntimeException("getNFKCInstance() returned null");
        }

        if (instance1 != instance2) {
            throw new RuntimeException("getNFKCInstance() did not return the same singleton instance on repeated calls");
        }

        // Basic sanity check: the returned instance should be of type Norm2AllModes
        if (!instance1.getClass().getName().startsWith("sun.text.normalizer.Norm2AllModes")) {
            throw new RuntimeException("Returned instance is not of expected type: " + instance1.getClass().getName());
        }
    }
}
