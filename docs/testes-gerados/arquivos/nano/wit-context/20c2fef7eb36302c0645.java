/* @test    @summary Basic tests for EnumSet.of(E, E, E, E) method    @run main EnumSetWitupTest */

import java.util.EnumSet;
import java.util.Collections;

public class EnumSetWitupTest {

    public static void main(String[] args) {
        testEnumSetOfFour();
        testEnumSetOfFourWithNull();
        testEnumSetOfFourWithInvalid();
    }

    private static void testEnumSetOfFour() {
        // Define a sample enum for testing
        enum SampleEnum {A, B, C, D}
        try {
            EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C, SampleEnum.D);
            if (set.size() != 4) {
                throw new AssertionError("Expected set size 4, got " + set.size());
            }
            if (!set.contains(SampleEnum.A) || !set.contains(SampleEnum.D)) {
                throw new AssertionError("Set does not contain expected elements");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testEnumSetOfFour", e);
        }
    }

    private static void testEnumSetOfFourWithNull() {
        enum SampleEnum {A, B, C, D}
        try {
            EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, null, SampleEnum.C, SampleEnum.D);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testEnumSetOfFourWithNull", e);
        }
    }

    private static void testEnumSetOfFourWithInvalid() {
        enum SampleEnum {A, B, C, D}
        try {
            // Create an enum constant from a different enum class to simulate invalid input
            class OtherEnum {X, Y}
            SampleEnum invalidEnum = (SampleEnum) (Object) OtherEnum.X;
            EnumSet<SampleEnum> set = EnumSet.of(invalidEnum, SampleEnum.B, SampleEnum.C, SampleEnum.D);
            throw new AssertionError("Expected ClassCastException not thrown");
        } catch (ClassCastException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testEnumSetOfFourWithInvalid", e);
        }
    }
}
