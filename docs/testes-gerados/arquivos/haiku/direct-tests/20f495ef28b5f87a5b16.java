/*
 * @test
 * @summary Verify ObjectInputFilter.Config.setSerialFilter rejects null and can be set only once
 * @run main ObjectInputFilterWitupTest
 */

import java.io.ObjectInputFilter;

public class ObjectInputFilterWitupTest {
    public static void main(String[] args) {
        try {
            ObjectInputFilter.Config.setSerialFilter(null);
            throw new RuntimeException("NullPointerException expected for null filter");
        } catch (NullPointerException expected) {
        }

        if (ObjectInputFilter.Config.getSerialFilter() != null) {
            throw new RuntimeException("Serial filter must not be set before the test");
        }

        ObjectInputFilter first = info -> ObjectInputFilter.Status.UNDECIDED;
        ObjectInputFilter.Config.setSerialFilter(first);
        if (ObjectInputFilter.Config.getSerialFilter() != first) {
            throw new RuntimeException("Serial filter was not stored");
        }

        ObjectInputFilter second = info -> ObjectInputFilter.Status.ALLOWED;
        try {
            ObjectInputFilter.Config.setSerialFilter(second);
            throw new RuntimeException("IllegalStateException expected on second set");
        } catch (IllegalStateException expected) {
        }

        if (ObjectInputFilter.Config.getSerialFilter() != first) {
            throw new RuntimeException("Serial filter changed after rejected second set");
        }
    }
}
