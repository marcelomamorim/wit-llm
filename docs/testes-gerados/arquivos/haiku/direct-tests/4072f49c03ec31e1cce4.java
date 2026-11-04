/*
 * @test
 * @summary Verify the public constructor of the package-private UnresolvedPermissionCollection creates an empty PermissionCollection
 * @run main/othervm --add-opens java.base/java.security=ALL-UNNAMED UnresolvedPermissionCollectionWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;

public class UnresolvedPermissionCollectionWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.security.UnresolvedPermissionCollection");
        Constructor<?> ctor = c.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object o;
        try {
            o = ctor.newInstance();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
        if (o == null) {
            throw new AssertionError("constructor returned null");
        }
        if (!(o instanceof PermissionCollection)) {
            throw new AssertionError("not a PermissionCollection: " + o.getClass());
        }
        PermissionCollection pc = (PermissionCollection) o;

        if (pc.implies(new RuntimePermission("exitVM"))) {
            throw new AssertionError("implies must be false for unresolved collection");
        }

        Enumeration<Permission> en = pc.elements();
        if (en.hasMoreElements()) {
            throw new AssertionError("fresh collection must be empty");
        }

        boolean thrown = false;
        try {
            pc.add(new RuntimePermission("exitVM"));
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("add of non-unresolved permission must throw IllegalArgumentException");
        }
    }
}
