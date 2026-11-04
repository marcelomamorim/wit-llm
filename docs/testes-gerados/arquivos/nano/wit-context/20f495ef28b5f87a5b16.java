/*
 * @test
 * @summary Test for ObjectInputFilter.setSerialFilter with null argument
 * @run main ObjectInputFilterWitupTest
 */

import java.io.ObjectInputFilter;

public class ObjectInputFilterWitupTest {

    public static void main(String[] args) {
        testSetSerialFilterNull();
        testSetSerialFilterValid();
        testSetSerialFilterOnce();
    }

    private static void testSetSerialFilterNull() {
        boolean threwNPE = false;
        try {
            ObjectInputFilter.setSerialFilter(null);
        } catch (NullPointerException e) {
            threwNPE = true;
        }
        if (!threwNPE) {
            throw new AssertionError("Expected NullPointerException when passing null to setSerialFilter");
        }
    }

    private static void testSetSerialFilterValid() {
        ObjectInputFilter filter = new ObjectInputFilter() {
            @Override
            public Status checkInput(FilterInfo info) {
                return Status.ALLOWED;
            }
        };
        // Reset the serial filter to null before test
        // Note: In actual implementation, this might require reflection or prior state reset
        // but here we assume initial state is null.
        ObjectInputFilter.setSerialFilter(filter);
        // No exception means success
    }

    private static void testSetSerialFilterOnce() {
        ObjectInputFilter filter1 = new ObjectInputFilter() {
            @Override
            public Status checkInput(FilterInfo info) {
                return Status.ALLOWED;
            }
        };
        ObjectInputFilter filter2 = new ObjectInputFilter() {
            @Override
            public Status checkInput(FilterInfo info) {
                return Status.REJECTED;
            }
        };
        // Set first filter
        ObjectInputFilter.setSerialFilter(filter1);
        boolean threwIllegalState = false;
        try {
            // Attempt to set second filter should throw IllegalStateException
            ObjectInputFilter.setSerialFilter(filter2);
        } catch (IllegalStateException e) {
            threwIllegalState = true;
        }
        if (!threwIllegalState) {
            throw new AssertionError("Expected IllegalStateException when setting filter twice");
        }
    }
}
