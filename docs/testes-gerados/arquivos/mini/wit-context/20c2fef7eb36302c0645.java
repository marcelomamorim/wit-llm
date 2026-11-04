/*
 * @test
 * @summary Test EnumSet.of(E e1, E e2, E e3, E e4) for normal and edge cases
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum { A, B, C, D, E }

    public static void main(String[] args) {
        testNormalUsage();
        testNullElementThrowsNPE();
    }

    private static void testNormalUsage() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C, SampleEnum.D);
        if (!set.contains(SampleEnum.A) || !set.contains(SampleEnum.B) || !set.contains(SampleEnum.C) || !set.contains(SampleEnum.D)) {
            throw new RuntimeException("EnumSet.of did not contain all added elements");
        }
        if (set.size() != 4) {
            throw new RuntimeException("EnumSet.of size incorrect, expected 4 but was " + set.size());
        }
    }

    private static void testNullElementThrowsNPE() {
        try {
            EnumSet.of(null, SampleEnum.B, SampleEnum.C, SampleEnum.D);
            throw new RuntimeException("Expected NullPointerException for null first element");
        } catch (NullPointerException expected) {
        }
        try {
            EnumSet.of(SampleEnum.A, null, SampleEnum.C, SampleEnum.D);
            throw new RuntimeException("Expected NullPointerException for null second element");
        } catch (NullPointerException expected) {
        }
        try {
            EnumSet.of(SampleEnum.A, SampleEnum.B, null, SampleEnum.D);
            throw new RuntimeException("Expected NullPointerException for null third element");
        } catch (NullPointerException expected) {
        }
        try {
            EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C, null);
            throw new RuntimeException("Expected NullPointerException for null fourth element");
        } catch (NullPointerException expected) {
        }
    }
}
