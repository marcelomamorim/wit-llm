/* @test
   @summary Tests for EnumSet.range method, including exception paths.
   @run main EnumSetWitupTest_P364ad1034295
 */
import java.util.EnumSet;
import java.lang.IllegalArgumentException;
import java.lang.ClassCastException;

public class EnumSetWitupTest_P364ad1034295 {
    public static void main(String[] args) {
        testRangeValid();
        testRangeInvalid();
        testRangeClassCast();
    }

    static void testRangeValid() {
        // Valid case: from.compareTo(to) <= 0
        enum Sample { A, B, C }
        try {
            EnumSet<Sample> set = EnumSet.range(Sample.A, Sample.C);
            if (set.isEmpty()) {
                throw new AssertionError("Expected non-empty EnumSet");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid range", e);
        }
    }

    static void testRangeInvalid() {
        // Invalid case: from.compareTo(to) > 0, should throw IllegalArgumentException
        enum Sample { A, B, C }
        try {
            EnumSet.range(Sample.C, Sample.A);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static void testRangeClassCast() {
        // Simulate ClassCastException by passing incompatible enum types
        // Since method is static and generic, we test with incompatible class via reflection
        // but as reflection is discouraged, we test with a cast scenario
        // Here, we simulate by calling with different enum types via raw types
        try {
            Object from = SampleA;
            Object to = SampleB;
            // cast to Enum to compile, but at runtime, types differ
            Enum<?> eFrom = (Enum<?>) from;
            Enum<?> eTo = (Enum<?>) to;
            EnumSet<?> result = EnumSet.range(eFrom, eTo); // should throw ClassCastException
            throw new AssertionError("Expected ClassCastException not thrown");
        } catch (ClassCastException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    // Dummy enums for testing
    enum SampleA { A, B, C }
    enum SampleB { X, Y, Z }
}
