/* @test    @summary Test for Norm2AllModes.normalize(CharSequence, Appendable) handling dest == src case    @run main Norm2AllModesWitupTest */

import sun.text.normalizer.Norm2AllModes;
import java.io.IOException;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) {
        testNormalizeWithSameSrcAndDest();
    }

    private static void testNormalizeWithSameSrcAndDest() {
        StringBuilder sb = new StringBuilder();
        String input = "test string";
        try {
            // Using the same object for src and dest to trigger IllegalArgumentException
            Norm2AllModes.Normalizer2 instance = new Norm2AllModes.NoopNormalizer2();
            // Since the method is static, we simulate the call via an instance method
            // but in actual code, it would be called on an object; here, we invoke directly
            // Note: the method is not static, so we need an instance
            Norm2AllModes.Norm2AllModes.normalize(input, input); // This line is illustrative; actual call requires an instance
            // Instead, create an instance of Norm2AllModes with a dummy impl to call normalize
            // but as the method is static, we call directly
            // For the test, we simulate the call as per the method signature
            // Since the method is not static, instantiate a dummy class to call it
            // but in the provided code, normalize is an instance method, so we need an instance
            // Let's create a dummy subclass to call the method
            class Dummy extends Norm2AllModes {
                public Appendable normalize(CharSequence src, Appendable dest) {
                    return super.normalize(src, dest);
                }
            }
            Dummy dummy = new Dummy();
            try {
                dummy.normalize(input, input); // should throw IllegalArgumentException
                System.err.println("Test failed: Expected IllegalArgumentException was not thrown.");
            } catch (IllegalArgumentException e) {
                System.out.println("Test passed: IllegalArgumentException thrown as expected.");
            }
        } catch (Exception e) {
            System.err.println("Test failed with unexpected exception: " + e);
        }
    }
}
