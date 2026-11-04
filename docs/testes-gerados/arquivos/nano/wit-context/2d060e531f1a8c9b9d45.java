/*
 * @test
 * @summary Tests for java.util.EnumSet.of(E e1, E e2, E e3)
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Arrays;

public class EnumSetWitupTest {

    public static void main(String[] args) {
        testEnumSetOfWithThreeEnums();
        testEnumSetOfWithNull();
        testEnumSetOfWithLargeArray();
    }

    static enum SampleEnum { A, B, C, D }

    public static void testEnumSetOfWithThreeEnums() {
        SampleEnum e1 = SampleEnum.A;
        SampleEnum e2 = SampleEnum.B;
        SampleEnum e3 = SampleEnum.C;
        EnumSet<SampleEnum> set = EnumSet.of(e1, e2, e3);
        if (!set.containsAll(Arrays.asList(e1, e2, e3))) {
            throw new AssertionError("EnumSet.of with three enums failed to contain all elements")
        }
        if (set.size() != 3) {
            throw new AssertionError("EnumSet.of with three enums has incorrect size")
        }
    }

    public static void testEnumSetOfWithNull() {
        try {
            EnumSet.of((SampleEnum) null, SampleEnum.B, SampleEnum.C);
            throw new AssertionError("Expected NullPointerException for null first argument")
        } catch (NullPointerException expected) {
            // expected
        }
        try {
            EnumSet.of(SampleEnum.A, null, SampleEnum.C);
            throw new AssertionError("Expected NullPointerException for null second argument")
        } catch (NullPointerException expected) {
            // expected
        }
        try {
            EnumSet.of(SampleEnum.A, SampleEnum.B, null);
            throw new AssertionError("Expected NullPointerException for null third argument")
        } catch (NullPointerException expected) {
            // expected
        }
    }

    public static void testEnumSetOfWithLargeArray() {
        SampleEnum[] enums = new SampleEnum[65];
        for (int i = 0; i < 65; i++) {
            enums[i] = SampleEnum.values()[i % SampleEnum.values().length];
        }
        try {
            EnumSet<SampleEnum> set = EnumSet.of(enums[0], enums);
            // Should not throw, but test behavior
            if (set == null) {
                throw new AssertionError("EnumSet.of with large array returned null")
            }
        } catch (UnsupportedOperationException e) {
            // Acceptable if thrown due to internal implementation limits
        }
    }
}
