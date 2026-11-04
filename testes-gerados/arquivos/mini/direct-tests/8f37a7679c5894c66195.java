/*
 * @test
 * @summary Test EnumSet.range method behavior
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum {
        A, B, C, D, E
    }

    public static void main(String[] args) {
        testRangeValidOrder();
        testRangeSameElement();
        testRangeInvalidOrder();
    }

    private static void testRangeValidOrder() {
        EnumSet<SampleEnum> set = EnumSet.range(SampleEnum.B, SampleEnum.D);
        if (!set.contains(SampleEnum.B) || !set.contains(SampleEnum.C) || !set.contains(SampleEnum.D)) {
            throw new RuntimeException("EnumSet.range did not include expected elements");
        }
        if (set.contains(SampleEnum.A) || set.contains(SampleEnum.E)) {
            throw new RuntimeException("EnumSet.range included unexpected elements");
        }
    }

    private static void testRangeSameElement() {
        EnumSet<SampleEnum> set = EnumSet.range(SampleEnum.C, SampleEnum.C);
        if (set.size() != 1 || !set.contains(SampleEnum.C)) {
            throw new RuntimeException("EnumSet.range with same from and to should contain exactly that element");
        }
    }

    private static void testRangeInvalidOrder() {
        try {
            EnumSet.range(SampleEnum.D, SampleEnum.B);
            throw new RuntimeException("EnumSet.range did not throw IllegalArgumentException for from > to");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
