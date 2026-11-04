/*
 * @test
 * @summary Verify that GeneralSubtrees.add(null) throws NullPointerException and leaves the collection unchanged
 * @modules java.base/sun.security.x509
 * @run main GeneralSubtreesWitupTest
 */

import sun.security.x509.GeneralSubtrees;

public class GeneralSubtreesWitupTest {

    public static void main(String[] args) {
        GeneralSubtrees subtrees = new GeneralSubtrees();
        boolean thrown = false;
        try {
            subtrees.add(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("add(null) did not throw NullPointerException");
        }
        if (subtrees.size() != 0) {
            throw new RuntimeException("size changed after rejected add(null): " + subtrees.size());
        }
    }
}
