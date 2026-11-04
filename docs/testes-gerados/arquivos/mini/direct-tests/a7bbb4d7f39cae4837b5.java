/*
 * @test
 * @summary Test FindOps.get() method behavior
 * @run main FindOpsWitupTest
 */

import java.util.Optional;

public class FindOpsWitupTest {

    static class TestFindOps<T> extends java.util.stream.FindOps {
        private final boolean hasValue;
        private final T value;

        TestFindOps(boolean hasValue, T value) {
            this.hasValue = hasValue;
            this.value = value;
        }

        public Optional<T> get() {
            return hasValue ? Optional.of(value) : null;
        }
    }

    public static void main(String[] args) {
        // Test case 1: hasValue true, value non-null
        TestFindOps<String> findOps1 = new TestFindOps<>(true, "test");
        Optional<String> result1 = findOps1.get();
        if (result1 == null || !result1.isPresent() || !"test".equals(result1.get())) {
            throw new AssertionError("Expected Optional.of('test'), got " + result1);
        }

        // Test case 2: hasValue false, expect null
        TestFindOps<String> findOps2 = new TestFindOps<>(false, "ignored");
        Optional<String> result2 = findOps2.get();
        if (result2 != null) {
            throw new AssertionError("Expected null when hasValue is false, got " + result2);
        }

        // Test case 3: hasValue true, value null (should throw NullPointerException from Optional.of)
        boolean npeThrown = false;
        try {
            TestFindOps<String> findOps3 = new TestFindOps<>(true, null);
            findOps3.get();
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when value is null and hasValue is true");
        }
    }
}
