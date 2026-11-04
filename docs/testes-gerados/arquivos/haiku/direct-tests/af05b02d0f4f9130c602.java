/*
 * @test
 * @summary GeneralSubtrees.add rejects null and leaves the collection unchanged
 * @modules java.base/sun.security.x509
 * @run main GeneralSubtreesWitupTest
 */

import java.util.Iterator;
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
            throw new RuntimeException("expected NullPointerException from add(null)");
        }
        if (subtrees.size() != 0) {
            throw new RuntimeException("size changed after rejected add: " + subtrees.size());
        }
        Iterator<?> it = subtrees.iterator();
        if (it.hasNext()) {
            throw new RuntimeException("iterator not empty after rejected add");
        }
        if (!subtrees.trees().isEmpty()) {
            throw new RuntimeException("trees() not empty after rejected add");
        }
    }
}
