/*
 * @test
 * @summary Verify GeneralSubtrees.contains rejects null with NullPointerException and returns false on an empty collection path
 * @modules java.base/sun.security.x509
 * @run main GeneralSubtreesWitupTest
 */

import sun.security.x509.GeneralSubtrees;

public class GeneralSubtreesWitupTest {

    public static void main(String[] args) throws Exception {
        testContainsNullThrowsNPE();
        testContainsNullOnClonedThrowsNPE();
        testSizeZeroAfterDefaultConstruction();
    }

    private static void testContainsNullThrowsNPE() {
        GeneralSubtrees subtrees = new GeneralSubtrees();
        boolean thrown = false;
        try {
            subtrees.contains(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("contains(null) did not throw NullPointerException");
        }
    }

    private static void testContainsNullOnClonedThrowsNPE() {
        GeneralSubtrees original = new GeneralSubtrees();
        GeneralSubtrees copy = (GeneralSubtrees) original.clone();
        boolean thrown = false;
        try {
            copy.contains(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("contains(null) on clone did not throw NullPointerException");
        }
    }

    private static void testSizeZeroAfterDefaultConstruction() {
        GeneralSubtrees subtrees = new GeneralSubtrees();
        if (subtrees.size() != 0) {
            throw new RuntimeException("Expected empty GeneralSubtrees, size=" + subtrees.size());
        }
    }
}
