/* @test    @summary Test EnumSet.of(E e) method for various enum constants    @run main EnumSetWitupTest */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum {A, B, C}

    public static void main(String[] args) {
        testOfWithSingleElement();
        testOfWithMultipleElements();
        testOfWithNull();
    }

    static void testOfWithSingleElement() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A);
        assert set.size() == 1 : "Size should be 1";
        assert set.contains(SampleEnum.A) : "Set should contain A";
        assert !set.contains(SampleEnum.B) : "Set should not contain B";
        assert !set.contains(SampleEnum.C) : "Set should not contain C";
    }

    static void testOfWithMultipleElements() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C);
        assert set.size() == 3 : "Size should be 3";
        assert set.contains(SampleEnum.A) : "Set should contain A";
        assert set.contains(SampleEnum.B) : "Set should contain B";
        assert set.contains(SampleEnum.C) : "Set should contain C";
    }

    static void testOfWithNull() {
        try {
            EnumSet.of((SampleEnum) null);
            assert false : "NullPointerException expected";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
