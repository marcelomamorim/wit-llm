/*
 * @test
 * @summary Verify ObjectInputFilter.Config.setSerialFilter rejects null and allows only one successful set
 * @run main ObjectInputFilterWitupTest
 */

import java.io.ObjectInputFilter;

public class ObjectInputFilterWitupTest {
    public static void main(String[] args) throws Exception {
        try {
            ObjectInputFilter.Config.setSerialFilter(null);
            throw new AssertionError("Expected NullPointerException for null filter");
        } catch (NullPointerException expected) {
        }

        if (ObjectInputFilter.Config.getSerialFilter() != null) {
            throw new AssertionError("Serial filter must remain unset after null rejection");
        }

        ObjectInputFilter first = info -> ObjectInputFilter.Status.UNDECIDED;
        ObjectInputFilter.Config.setSerialFilter(first);

        if (ObjectInputFilter.Config.getSerialFilter() != first) {
            throw new AssertionError("Serial filter was not set to the provided filter");
        }

        ObjectInputFilter second = info -> ObjectInputFilter.Status.ALLOWED;
        try {
            ObjectInputFilter.Config.setSerialFilter(second);
            throw new AssertionError("Expected IllegalStateException on second set");
        } catch (IllegalStateException expected) {
        }

        if (ObjectInputFilter.Config.getSerialFilter() != first) {
            throw new AssertionError("Serial filter changed after rejected second set");
        }
    }
}
