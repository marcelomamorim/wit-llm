/* @test    @summary Test EnumSet.range for various enum constants.    @run main EnumSetWitupTest_P5b460f73d038 */

import java.util.EnumSet;

public class EnumSetWitupTest_P5b460f73d038 {

    enum MyEnum {A, B, C, D}

    public static void main(String[] args) {
        testRangeNormal();
        testRangeInvalid();
    }

    static void testRangeNormal() {
        EnumSet<MyEnum> set = EnumSet.range(MyEnum.A, MyEnum.C);
        assert set.contains(MyEnum.A) : "Should contain A";
        assert set.contains(MyEnum.B) : "Should contain B";
        assert set.contains(MyEnum.C) : "Should contain C";
        assert !set.contains(MyEnum.D) : "Should not contain D";
        assert set.size() == 3 : "Size should be 3";
    }

    static void testRangeInvalid() {
        try {
            EnumSet.range(MyEnum.C, MyEnum.A);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
