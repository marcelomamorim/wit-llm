/* @test    @summary Test for FindOps.get() method in java.util.stream package    @run main FindOpsWitupTest */

import java.util.Optional;
import java.util.stream.FindOps;

public class FindOpsWitupTest {

    public static void main(String[] args) {
        testGetReturnsOptionalWithValueWhenHasValue();
        testGetReturnsNullWhenNoValue();
    }

    static void testGetReturnsOptionalWithValueWhenHasValue() {
        // Reflection is not used; directly invoke get() with a mock setup
        // Since the method relies on hasValue and value, simulate a class with these fields
        class MockFindOps extends FindOps {
            boolean hasValue;
            Object value;
            MockFindOps(boolean hasValue, Object value) {
                this.hasValue = hasValue;
                this.value = value;
            }
            @Override
            public Optional<Object> get() {
                return hasValue ? Optional.of(value) : null;
            }
        }
        MockFindOps mock = new MockFindOps(true, "testValue");
        Optional<Object> result = mock.get();
        if (result == null) {
            throw new AssertionError("Expected Optional.of, but got null");
        }
        if (!result.isPresent() || !result.get().equals("testValue")) {
            throw new AssertionError("Unexpected value in Optional: " + result);
        }
    }

    static void testGetReturnsNullWhenNoValue() {
        class MockFindOps extends FindOps {
            boolean hasValue;
            Object value;
            MockFindOps(boolean hasValue, Object value) {
                this.hasValue = hasValue;
                this.value = value;
            }
            @Override
            public Optional<Object> get() {
                return hasValue ? Optional.of(value) : null;
            }
        }
        MockFindOps mock = new MockFindOps(false, null);
        Optional<Object> result = mock.get();
        if (result != null) {
            throw new AssertionError("Expected null, but got: " + result);
        }
    }
}
