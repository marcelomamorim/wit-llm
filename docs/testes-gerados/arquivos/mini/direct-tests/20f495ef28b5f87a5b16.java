/*
 * @test
 * @summary Test ObjectInputFilter.setSerialFilter behavior
 * @run main ObjectInputFilterWitupTest
 */

import java.io.ObjectInputFilter;
import java.io.ObjectStreamConstants;
import java.security.Permission;

public class ObjectInputFilterWitupTest {

    static class TestSecurityManager extends SecurityManager {
        boolean permissionChecked = false;

        @Override
        public void checkPermission(Permission perm) {
            if (ObjectStreamConstants.SERIAL_FILTER_PERMISSION.equals(perm)) {
                permissionChecked = true;
            } else {
                // Allow other permissions
            }
        }

        @Override
        public void checkPermission(Permission perm, Object context) {
            checkPermission(perm);
        }
    }

    public static void main(String[] args) throws Exception {
        // Reset filter for test isolation by reflection (not public API, but needed for test repeatability)
        // This is a known limitation; test notes explain this
        java.lang.reflect.Field filterField = ObjectInputFilter.class.getDeclaredField("serialFilter");
        filterField.setAccessible(true);
        java.lang.reflect.Field lockField = ObjectInputFilter.class.getDeclaredField("serialFilterLock");
        lockField.setAccessible(true);
        Object lock = lockField.get(null);
        synchronized (lock) {
            filterField.set(null, null);
        }

        // Test 1: Passing null filter throws NullPointerException
        try {
            ObjectInputFilter.setSerialFilter(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 2: Setting filter without SecurityManager succeeds
        ObjectInputFilter filter1 = new ObjectInputFilter() {
            @Override
            public Status checkInput(FilterInfo info) {
                return Status.UNDECIDED;
            }
        };
        ObjectInputFilter.setSerialFilter(filter1);

        // Test 3: Setting filter second time throws IllegalStateException
        try {
            ObjectInputFilter.setSerialFilter(filter1);
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // expected
        }

        // Reset filter for next test
        synchronized (lock) {
            filterField.set(null, null);
        }

        // Test 4: Setting filter with SecurityManager that checks permission
        TestSecurityManager sm = new TestSecurityManager();
        System.setSecurityManager(sm);
        try {
            ObjectInputFilter.setSerialFilter(filter1);
            if (!sm.permissionChecked) {
                throw new RuntimeException("SecurityManager did not check permission");
            }
        } finally {
            System.setSecurityManager(null);
        }

        // Test 5: Setting filter second time with SecurityManager throws IllegalStateException
        System.setSecurityManager(sm);
        try {
            try {
                ObjectInputFilter.setSerialFilter(filter1);
                throw new RuntimeException("Expected IllegalStateException not thrown");
            } catch (IllegalStateException ise) {
                // expected
            }
        } finally {
            System.setSecurityManager(null);
        }
    }
}
