/*
 * @test
 * @summary Test default constructor of UnresolvedPermissionCollection
 * @run main UnresolvedPermissionCollectionWitupTest
 */

import java.security.UnresolvedPermissionCollection;

public class UnresolvedPermissionCollectionWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
    }

    public static void testDefaultConstructor() {
        UnresolvedPermissionCollection upc = new UnresolvedPermissionCollection();
        if (upc == null) {
            throw new AssertionError("Constructor returned null");
        }
        // Additional observable checks can be added if the class exposes state or behavior
        System.out.println("testDefaultConstructor passed");
    }
}
