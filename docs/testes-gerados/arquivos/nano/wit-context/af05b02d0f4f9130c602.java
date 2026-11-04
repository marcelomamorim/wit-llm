/* @test    @summary Test for sun.security.x509.GeneralSubtrees.add method handling null input    @run main GeneralSubtreesWitupTest */

import sun.security.x509.GeneralSubtrees;
import sun.security.x509.GeneralSubtree;

public class GeneralSubtreesWitupTest {

    public static void main(String[] args) {
        testAddNull();
        testAddValid();
    }

    private static void testAddNull() {
        GeneralSubtrees gst = new GeneralSubtrees();
        boolean exceptionThrown = false;
        try {
            gst.add(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when adding null GeneralSubtree");
        }
    }

    private static void testAddValid() {
        GeneralSubtrees gst = new GeneralSubtrees();
        GeneralSubtree gstItem = new GeneralSubtree();
        gst.add(gstItem);
        if (gst.size() != 1) {
            throw new AssertionError("GeneralSubtrees size should be 1 after adding one item");
        }
        if (!gst.contains(gstItem)) {
            throw new AssertionError("GeneralSubtrees should contain the added item");
        }
    }
}
