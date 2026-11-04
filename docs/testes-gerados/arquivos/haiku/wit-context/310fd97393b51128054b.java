/*
 * @test
 * @summary Verifies NoSuchElementException from the enumerator returned by Permissions.elements() when no further elements exist
 * @run main PermissionsWitupTest
 */

import java.security.Permission;
import java.security.Permissions;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class PermissionsWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyNextElementThrows();
        testSingleElementReturnedThenExhausted();
        System.out.println("PASSED");
    }

    static void testEmptyNextElementThrows() {
        Permissions perms = new Permissions();
        Enumeration<Permission> e = perms.elements();
        if (e.hasMoreElements()) {
            throw new RuntimeException("empty Permissions should have no elements");
        }
        try {
            e.nextElement();
            throw new RuntimeException("expected NoSuchElementException on empty enumeration");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testSingleElementReturnedThenExhausted() {
        Permissions perms = new Permissions();
        RuntimePermission rp = new RuntimePermission("exitVM");
        perms.add(rp);
        Enumeration<Permission> e = perms.elements();
        if (!e.hasMoreElements()) {
            throw new RuntimeException("expected one element");
        }
        Permission got = e.nextElement();
        if (!rp.equals(got)) {
            throw new RuntimeException("returned permission differs from added permission");
        }
        if (e.hasMoreElements()) {
            throw new RuntimeException("enumeration should be exhausted");
        }
        try {
            e.nextElement();
            throw new RuntimeException("expected NoSuchElementException after exhaustion");
        } catch (NoSuchElementException expected) {
        }
    }
}
