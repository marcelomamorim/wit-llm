/*
 * @test
 * @summary Verifies AclEntry.Builder.setPermissions returns the builder and rejects null permission elements with NullPointerException
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;

public class AclEntryWitupTest {

    public static void main(String[] args) {
        testEmptyVarargsReturnsBuilder();
        testValidPermissionsReturnsBuilder();
        testNullElementThrowsNPE();
        testNullElementAmongValidThrowsNPE();
    }

    static void testEmptyVarargsReturnsBuilder() {
        AclEntry.Builder b = AclEntry.newBuilder();
        AclEntry.Builder r = b.setPermissions();
        if (r != b) {
            throw new RuntimeException("setPermissions() must return the same builder");
        }
    }

    static void testValidPermissionsReturnsBuilder() {
        AclEntry.Builder b = AclEntry.newBuilder();
        AclEntry.Builder r = b.setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.WRITE_DATA);
        if (r != b) {
            throw new RuntimeException("setPermissions(...) must return the same builder");
        }
    }

    static void testNullElementThrowsNPE() {
        AclEntry.Builder b = AclEntry.newBuilder();
        boolean thrown = false;
        try {
            b.setPermissions((AclEntryPermission) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null permission element");
        }
    }

    static void testNullElementAmongValidThrowsNPE() {
        AclEntry.Builder b = AclEntry.newBuilder();
        boolean thrown = false;
        try {
            b.setPermissions(AclEntryPermission.READ_DATA, null, AclEntryPermission.WRITE_DATA);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException when a null element is mixed with valid permissions");
        }
    }
}
