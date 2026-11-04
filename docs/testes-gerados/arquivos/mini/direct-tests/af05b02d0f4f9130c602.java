/*
 * @test
 * @summary Test sun.security.x509.GeneralSubtrees.add method behavior
 * @run main GeneralSubtreesWitupTest
 */

import sun.security.x509.GeneralSubtrees;
import sun.security.x509.GeneralSubtree;

public class GeneralSubtreesWitupTest {
    public static void main(String[] args) throws Exception {
        GeneralSubtrees gs = new GeneralSubtrees();

        // Test adding a valid GeneralSubtree instance
        GeneralSubtree subtree = new GeneralSubtree();
        gs.add(subtree);
        if (gs.size() != 1) {
            throw new RuntimeException("Failed to add GeneralSubtree: size mismatch");
        }
        if (!gs.contains(subtree)) {
            throw new RuntimeException("Added GeneralSubtree not found in collection");
        }

        // Test adding null throws NullPointerException
        boolean npeThrown = false;
        try {
            gs.add(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when adding null");
        }
    }
}
