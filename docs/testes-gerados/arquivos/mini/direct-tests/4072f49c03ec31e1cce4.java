/*
 * @test
 * @summary Test constructor of UnresolvedPermissionCollection
 * @run main UnresolvedPermissionCollectionWitupTest
 */

import java.security.UnresolvedPermissionCollection;

public class UnresolvedPermissionCollectionWitupTest {
    public static void main(String[] args) {
        UnresolvedPermissionCollection upc = new UnresolvedPermissionCollection();
        if (upc == null) {
            throw new RuntimeException("Constructor returned null");
        }
    }
}
