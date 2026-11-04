/* @test    @summary Test the contains method of GeneralSubtrees for various scenarios    @run main GeneralSubtreesWitupTest */

import sun.security.x509.GeneralSubtrees;
import sun.security.x509.GeneralSubtree;
import java.io.*;

public class GeneralSubtreesWitupTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithEmpty();
        testContainsWithMatching();
        testContainsWithNonMatching();
    }

    static void testContainsWithNull() {
        GeneralSubtrees gst = new GeneralSubtrees();
        try {
            gst.contains(null);
            System.out.println("FAIL: Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testContainsWithEmpty() {
        GeneralSubtrees gst = new GeneralSubtrees();
        GeneralSubtree gs = new GeneralSubtree();
        boolean result = gst.contains(gs);
        if (!result) {
            System.out.println("PASS: contains returns false for empty collection");
        } else {
            System.out.println("FAIL: contains should return false for non-added element");
        }
    }

    static void testContainsWithMatching() {
        GeneralSubtrees gst = new GeneralSubtrees();
        GeneralSubtree gs = new GeneralSubtree();
        gst.add(gs);
        boolean result = gst.contains(gs);
        if (result) {
            System.out.println("PASS: contains returns true for added element");
        } else {
            System.out.println("FAIL: contains should return true for added element");
        }
    }

    static void testContainsWithNonMatching() {
        GeneralSubtrees gst = new GeneralSubtrees();
        GeneralSubtree gs1 = new GeneralSubtree();
        GeneralSubtree gs2 = new GeneralSubtree();
        gst.add(gs1);
        boolean result = gst.contains(gs2);
        if (!result) {
            System.out.println("PASS: contains returns false for non-added element");
        } else {
            System.out.println("FAIL: contains should return false for non-added element");
        }
    }
}
