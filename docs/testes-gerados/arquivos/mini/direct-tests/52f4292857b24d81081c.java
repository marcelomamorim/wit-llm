/*
 * @test
 * @summary Test sun.security.x509.GeneralSubtrees.contains method
 * @run main GeneralSubtreesWitupTest
 */

import sun.security.x509.GeneralSubtrees;
import sun.security.x509.GeneralSubtree;

public class GeneralSubtreesWitupTest {
    public static void main(String[] args) throws Exception {
        GeneralSubtrees gs = new GeneralSubtrees();

        // Test contains with null argument throws NullPointerException
        boolean npeThrown = false;
        try {
            gs.contains(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for contains(null)");
        }

        // Test contains returns false when empty
        GeneralSubtree subtree = new GeneralSubtree();
        if (gs.contains(subtree)) {
            throw new RuntimeException("contains returned true for subtree not added");
        }

        // Test contains returns true after add
        gs.add(subtree);
        if (!gs.contains(subtree)) {
            throw new RuntimeException("contains returned false for subtree that was added");
        }

        // Test contains returns false for different instance
        GeneralSubtree other = new GeneralSubtree();
        if (gs.contains(other)) {
            throw new RuntimeException("contains returned true for different subtree instance");
        }

        System.out.println("All tests passed.");
    }
}
