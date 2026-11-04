/* @test    @summary Test ObjectInputFilter.setSerialFilter method for correct behavior and exception handling    @run main ObjectInputFilterWitupTest */

import java.io.ObjectInputFilter;
import java.security.Permission;

public class ObjectInputFilterWitupTest {

    public static void main(String[] args) {
        testSetSerialFilterNull();
        testSetSerialFilterOnce();
        testSetSerialFilterTwice();
    }

    private static void testSetSerialFilterNull() {
        boolean exceptionThrown = false;
        try {
            ObjectInputFilter.setSerialFilter(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when setting null filter");
        }
    }

    private static void testSetSerialFilterOnce() {
        ObjectInputFilter filter = new ObjectInputFilter() {
            @Override
            public Status checkInput(FilterInfo info) {
                return Status.ALLOWED;
            }
        };
        // Resetting serialFilter for test purposes (simulate initial state)
        resetSerialFilter();
        ObjectInputFilter.setSerialFilter(filter);
        // Verify that serialFilter is set (via reflection or internal check)
        // Since internal state is not accessible, test that setting again throws exception
        boolean exceptionThrown = false;
        try {
            ObjectInputFilter.setSerialFilter(filter);
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when setting filter twice");
        }
    }

    private static void testSetSerialFilterTwice() {
        ObjectInputFilter filter = new ObjectInputFilter() {
            @Override
            public Status checkInput(FilterInfo info) {
                return Status.ALLOWED;
            }
        };
        // Reset serialFilter for test
        resetSerialFilter();
        ObjectInputFilter.setSerialFilter(filter);
        boolean exceptionThrown = false;
        try {
            ObjectInputFilter.setSerialFilter(filter);
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when setting filter twice");
        }
    }

    // Helper method to reset serialFilter to null for testing purposes
    private static void resetSerialFilter() {
        try {
            java.lang.reflect.Field field = ObjectInputFilter.class.getDeclaredField("serialFilter");
            field.setAccessible(true);
            field.set(null, null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to reset serialFilter", e);
        }
    }
}
