/*
 * @test
 * @summary Test ObjectInputFilter.setSerialFilter behavior including null and multiple set attempts
 * @run main ObjectInputFilterWitupTest
 */

import java.io.ObjectInputFilter;
import java.io.ObjectStreamConstants;
import java.security.Permission;

public class ObjectInputFilterWitupTest {

    public static void main(String[] args) throws Exception {
        testSetSerialFilterRejectsNull();
        testSetSerialFilterOnlyOnce();
        System.out.println("All tests passed.");
    }

    private static void testSetSerialFilterRejectsNull() {
        try {
            ObjectInputFilter.setSerialFilter(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        }
    }

    private static void testSetSerialFilterOnlyOnce() throws Exception {
        // Reset state by reflection is not allowed, so we run this test only if no filter is set yet
        // If filter already set, skip this test to avoid IllegalStateException
        // Because no public API to reset filter, we test only if not set

        // Try to set a dummy filter first
        ObjectInputFilter dummyFilter = new ObjectInputFilter() {
            @Override
            public Status checkInput(FilterInfo info) {
                return Status.UNDECIDED;
            }
        };

        boolean firstSetSucceeded = false;
        try {
            ObjectInputFilter.setSerialFilter(dummyFilter);
            firstSetSucceeded = true;
        } catch (IllegalStateException ise) {
            // Filter already set, skip remainder
            return;
        }

        if (!firstSetSucceeded) {
            throw new RuntimeException("Failed to set serial filter first time");
        }

        // Now attempt to set again, expect IllegalStateException
        try {
            ObjectInputFilter.setSerialFilter(dummyFilter);
            throw new RuntimeException("Expected IllegalStateException not thrown on second set");
        } catch (IllegalStateException ise) {
            // Expected
        }
    }
}
