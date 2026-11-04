/*
 * @test
 * @summary Test sun.security.x509.GeneralSubtrees.contains(GeneralSubtree) method
 * @run main GeneralSubtreesWitupTest
 */

import sun.security.x509.GeneralSubtrees;
import sun.security.x509.GeneralSubtree;

public class GeneralSubtreesWitupTest {
    public static void main(String[] args) throws Exception {
        GeneralSubtrees gs = new GeneralSubtrees();

        // Test contains(null) throws NullPointerException
        boolean npeThrown = false;
        try {
            gs.contains(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when calling contains(null)");
        }

        // Test contains with a GeneralSubtree not added returns false
        GeneralSubtree dummy = new GeneralSubtree();
        if (gs.contains(dummy)) {
            throw new RuntimeException("contains returned true for a subtree not added");
        }

        // Test contains with a GeneralSubtree added returns true
        gs.add(dummy);
        if (!gs.contains(dummy)) {
            throw new RuntimeException("contains returned false for a subtree that was added");
        }

        System.out.println("Test passed.");
    }
}
