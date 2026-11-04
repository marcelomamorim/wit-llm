/*
 * @test
 * @summary Test sun.security.x509.GeneralSubtrees.add(GeneralSubtree) for null argument and normal add
 * @run main GeneralSubtreesWitupTest
 */

import sun.security.x509.GeneralSubtrees;
import sun.security.x509.GeneralSubtree;

public class GeneralSubtreesWitupTest {
    public static void main(String[] args) throws Exception {
        GeneralSubtrees gs = new GeneralSubtrees();

        // Test that add(null) throws NullPointerException
        boolean npeThrown = false;
        try {
            gs.add(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when adding null");
        }

        // Test normal add and contains
        GeneralSubtree dummy = new GeneralSubtree();
        gs.add(dummy);
        if (!gs.contains(dummy)) {
            throw new RuntimeException("Added GeneralSubtree not found in GeneralSubtrees");
        }

        // Test size increments
        if (gs.size() != 1) {
            throw new RuntimeException("Size should be 1 after adding one GeneralSubtree");
        }

        // Test get returns the added element
        if (gs.get(0) != dummy) {
            throw new RuntimeException("get(0) did not return the added GeneralSubtree");
        }

        // Test remove removes the element
        gs.remove(0);
        if (gs.size() != 0) {
            throw new RuntimeException("Size should be 0 after removing the GeneralSubtree");
        }
    }
}
