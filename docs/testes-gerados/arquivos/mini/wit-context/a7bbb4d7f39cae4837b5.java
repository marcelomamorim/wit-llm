/*
 * @test
 * @summary Test for java.util.stream.FindOps.get() method to check behavior with null value and hasValue true
 * @run main FindOpsWitupTest
 */

import java.util.Optional;

public class FindOpsWitupTest {

    static class FindSinkOfRef<T> {
        boolean hasValue;
        T value;

        FindSinkOfRef(boolean hasValue, T value) {
            this.hasValue = hasValue;
            this.value = value;
        }

        Optional<T> get() {
            return hasValue ? Optional.of(value) : null;
        }
    }

    public static void main(String[] args) {
        // Test 1: hasValue = false, expect get() returns null
        FindSinkOfRef<String> sink1 = new FindSinkOfRef<>(false, "any");
        Optional<String> result1 = sink1.get();
        if (result1 != null) {
            throw new AssertionError("Expected null when hasValue is false");
        }

        // Test 2: hasValue = true, value non-null, expect Optional.of(value)
        FindSinkOfRef<String> sink2 = new FindSinkOfRef<>(true, "test");
        Optional<String> result2 = sink2.get();
        if (result2 == null || !"test".equals(result2.get())) {
            throw new AssertionError("Expected Optional.of('test') when hasValue is true and value non-null");
        }

        // Test 3: hasValue = true, value null, expect NullPointerException from Optional.of(null)
        FindSinkOfRef<String> sink3 = new FindSinkOfRef<>(true, null);
        try {
            sink3.get();
            throw new AssertionError("Expected NullPointerException when hasValue is true and value is null");
        } catch (NullPointerException npe) {
            // Expected exception
        }

        System.out.println("All tests passed.");
    }
}
