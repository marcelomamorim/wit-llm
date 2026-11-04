/*
 * @test
 * @summary Verifies that the public constructor of UnresolvedPermissionCollection creates new non-null PermissionCollection instances
 * @run main/othervm --add-opens java.base/java.security=ALL-UNNAMED UnresolvedPermissionCollectionWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.PermissionCollection;

public class UnresolvedPermissionCollectionWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.security.UnresolvedPermissionCollection");
        Constructor<?> ctor = c.getConstructor();
        ctor.setAccessible(true);
        Object first;
        Object second;
        try {
            first = ctor.newInstance();
            second = ctor.newInstance();
        } catch (InvocationTargetException e) {
            throw new AssertionError("constructor threw unexpectedly", e.getCause());
        }
        if (first == null || second == null) {
            throw new AssertionError("constructor returned null");
        }
        if (first == second) {
            throw new AssertionError("constructor returned the same instance twice");
        }
        if (first.getClass() != c) {
            throw new AssertionError("unexpected class: " + first.getClass());
        }
        if (!(first instanceof PermissionCollection)) {
            throw new AssertionError("instance is not a PermissionCollection");
        }
    }
}
