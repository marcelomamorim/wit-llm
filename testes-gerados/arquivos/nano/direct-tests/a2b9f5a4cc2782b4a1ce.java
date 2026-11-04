/* @test    @summary Test PropertyPermissionCollection constructor behavior    @run main PropertyPermissionWitupTest */

import java.util.PropertyPermission;
import java.util.PropertyPermission.PropertyPermissionCollection;

public class PropertyPermissionWitupTest {

    public static void main(String[] args) {
        testPropertyPermissionCollectionConstructor();
    }

    public static void testPropertyPermissionCollectionConstructor() {
        PropertyPermissionCollection collection = new PropertyPermissionCollection();
        if (collection == null) {
            throw new AssertionError("PropertyPermissionCollection constructor returned null");
        }
        System.out.println("PropertyPermissionCollection constructor test passed.");
    }
}
