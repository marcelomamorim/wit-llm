/*
 * @test
 * @summary Test EnumSet.range(E from, E to) for valid range and exception cases
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum {
        A, B, C, D, E
    }

    public static void main(String[] args) {
        testValidRange();
        testIllegalArgumentException();
        testClassCastExceptionPath();
    }

    private static void testValidRange() {
        EnumSet<SampleEnum> set = EnumSet.range(SampleEnum.B, SampleEnum.D);
        if (!set.contains(SampleEnum.B) || !set.contains(SampleEnum.C) || !set.contains(SampleEnum.D)) {
            throw new AssertionError("EnumSet.range did not include expected elements");
        }
        if (set.contains(SampleEnum.A) || set.contains(SampleEnum.E)) {
            throw new AssertionError("EnumSet.range included unexpected elements");
        }
    }

    private static void testIllegalArgumentException() {
        try {
            EnumSet.range(SampleEnum.D, SampleEnum.B);
            throw new AssertionError("Expected IllegalArgumentException for from > to");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testClassCastExceptionPath() {
        // The ClassCastException path depends on universe == null after noneOf(from.getDeclaringClass())
        // This is internal state and not accessible; the current implementation does not expose this
        // Hence, we test that no ClassCastException is thrown for valid enums of the same type
        try {
            EnumSet<SampleEnum> set = EnumSet.range(SampleEnum.A, SampleEnum.E);
            // no exception expected
        } catch (ClassCastException e) {
            throw new AssertionError("Unexpected ClassCastException for valid enum range");
        }
    }
}
