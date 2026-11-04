/*
 * @test
 * @summary GeneralSubtrees.contains must reject a null tree with NullPointerException
 * @modules java.base/sun.security.x509
 * @run main GeneralSubtreesWitupTest
 */

import sun.security.x509.GeneralSubtree;
import sun.security.x509.GeneralSubtrees;

public class GeneralSubtreesWitupTest {
    public static void main(String[] args) {
        GeneralSubtrees trees = new GeneralSubtrees();
        GeneralSubtree nullTree = null;
        boolean thrown = false;
        try {
            trees.contains(nullTree);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("contains(null) must throw NullPointerException");
        }
        if (trees.size() != 0) {
            throw new RuntimeException("failed contains(null) must not modify the collection");
        }
    }
}
