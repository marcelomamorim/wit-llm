/* @test    @summary Test EnumSet.range with valid and invalid inputs    @run main EnumSetWitupTest */

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class EnumSetWitupTest {

    public static void main(String[] args) {
        testRangeValid();
        testRangeInvalid();
        testRangeNull();
    }

    static void testRangeValid() {
        try {
            // Using a simple enum for test
            TestEnum from = TestEnum.A;
            TestEnum to = TestEnum.C;
            EnumSet<TestEnum> set = EnumSet.range(from, to);
            if (!set.contains(TestEnum.A) || !set.contains(TestEnum.B) || !set.contains(TestEnum.C)) {
                throw new AssertionError("Valid range did not include expected elements");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRangeValid", e);
        }
    }

    static void testRangeInvalid() {
        try {
            TestEnum from = TestEnum.C;
            TestEnum to = TestEnum.A;
            EnumSet.range(from, to);
            throw new AssertionError("Expected IllegalArgumentException not thrown for invalid range");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRangeInvalid", e);
        }
    }

    static void testRangeNull() {
        try {
            EnumSet.range(null, TestEnum.C);
            throw new AssertionError("Expected NullPointerException not thrown for null 'from'");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null 'from'", e);
        }
        try {
            EnumSet.range(TestEnum.A, null);
            throw new AssertionError("Expected NullPointerException not thrown for null 'to'");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null 'to'", e);
        }
    }

    enum TestEnum {
        A, B, C
    }
}
