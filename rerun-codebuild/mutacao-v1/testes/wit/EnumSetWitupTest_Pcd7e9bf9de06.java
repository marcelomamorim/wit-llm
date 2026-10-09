/* @test    @summary Test EnumSet.range for valid and invalid inputs    @run main EnumSetWitupTest_Pcd7e9bf9de06 */

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class EnumSetWitupTest_Pcd7e9bf9de06 {

    public static void main(String[] args) {
        testRangeValid();
        testRangeInvalid();
        testRangeNull();
    }

    private static void testRangeValid() {
        try {
            // Using a simple enum for test
            TestEnum from = TestEnum.A;
            TestEnum to = TestEnum.C;
            EnumSet<TestEnum> result = EnumSet.range(from, to);
            if (result == null) {
                throw new AssertionError("Result should not be null for valid range");
            }
            if (!result.contains(TestEnum.A) || !result.contains(TestEnum.B) || !result.contains(TestEnum.C)) {
                throw new AssertionError("Result does not contain expected elements");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRangeValid: " + e);
        }
    }

    private static void testRangeInvalid() {
        try {
            TestEnum from = TestEnum.C;
            TestEnum to = TestEnum.A;
            EnumSet.range(from, to);
            throw new AssertionError("Expected IllegalArgumentException for invalid range");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRangeInvalid: " + e);
        }
    }

    private static void testRangeNull() {
        try {
            EnumSet.range(null, TestEnum.C);
            throw new AssertionError("Expected NullPointerException for null 'from'");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null 'from': " + e);
        }
        try {
            EnumSet.range(TestEnum.A, null);
            throw new AssertionError("Expected NullPointerException for null 'to'");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null 'to': " + e);
        }
    }

    private enum TestEnum {
        A, B, C;
    }
}
