/* @test    @summary Test BasicPermissionCollection constructor with valid class argument    @run main BasicPermissionWitupTest */

import java.security.BasicPermission;
import java.security.BasicPermissionCollection;

public class BasicPermissionWitupTest {

    public static void main(String[] args) {
        testBasicPermissionCollectionWithClass();
    }

    public static void testBasicPermissionCollectionWithClass() {
        try {
            BasicPermissionCollection collection = new BasicPermissionCollection(BasicPermission.class);
            if (collection == null) {
                throw new AssertionError("BasicPermissionCollection instance is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
