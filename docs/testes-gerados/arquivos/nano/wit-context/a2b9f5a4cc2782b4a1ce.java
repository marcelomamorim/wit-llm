/* @test    @summary Test PropertyPermission constructor for PropertyPermissionCollection    @run main PropertyPermissionWitupTest */

import java.util.PropertyPermission;

public class PropertyPermissionWitupTest {

    public static void main(String[] args) {
        testPropertyPermissionCollectionConstructor();
    }

    public static void testPropertyPermissionCollectionConstructor() {
        try {
            PropertyPermission.PropertyPermissionCollection collection = new PropertyPermission.PropertyPermissionCollection();
            if (collection == null) {
                throw new AssertionError("Constructor returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Constructor threw an exception: " + e);
        }
    }
}
