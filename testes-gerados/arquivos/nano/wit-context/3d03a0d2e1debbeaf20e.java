/* @test    @summary Test setText with null CharacterIterator to verify IllegalArgumentException is thrown    @run main NormalizerBaseWitupTest */

import java.text.CharacterIterator;
import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testSetTextNull();
    }

    public static void testSetTextNull() {
        NormalizerBase nb = new NormalizerBase();
        try {
            nb.setText((CharacterIterator) null);
            System.err.println("Failed: Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException was thrown as expected.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
            System.exit(1);
        }
    }
}
