/*
 * @test
 * @summary Test EnumSet.of(E e1, E e2, E e3) for normal and exceptional cases
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum {
        A, B, C, D
    }

    public static void main(String[] args) {
        testNormalUsage();
        testNullElementThrowsNPE();
    }

    private static void testNormalUsage() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C);
        if (!set.contains(SampleEnum.A) || !set.contains(SampleEnum.B) || !set.contains(SampleEnum.C)) {
            throw new RuntimeException("EnumSet.of did not contain all added elements");
        }
        if (set.size() != 3) {
            throw new RuntimeException("EnumSet.of size incorrect, expected 3 but was " + set.size());
        }
    }

    private static void testNullElementThrowsNPE() {
        try {
            EnumSet.of(SampleEnum.A, null, SampleEnum.C);
            throw new RuntimeException("Expected NullPointerException for null element");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
