/* @test    @summary Test EnumSet.of(E e1, E e2, E e3, E e4) method    @run main EnumSetWitupTest */

import java.util.EnumSet;
import java.util.Set;

public class EnumSetWitupTest {

    enum SampleEnum {A, B, C, D, E}

    public static void main(String[] args) {
        testEnumSetOfFour();
        testEnumSetOfFourDifferent();
        testEnumSetOfFourSame();
        testEnumSetOfFourEmpty();
    }

    static void testEnumSetOfFour() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C, SampleEnum.D);
        assert set.contains(SampleEnum.A) : "Set should contain A";
        assert set.contains(SampleEnum.B) : "Set should contain B";
        assert set.contains(SampleEnum.C) : "Set should contain C";
        assert set.contains(SampleEnum.D) : "Set should contain D";
        assert !set.contains(SampleEnum.E) : "Set should not contain E";
        assert set.size() == 4 : "Set size should be 4";
    }

    static void testEnumSetOfFourDifferent() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.E, SampleEnum.C, SampleEnum.A, SampleEnum.B);
        assert set.contains(SampleEnum.A) : "Set should contain A";
        assert set.contains(SampleEnum.B) : "Set should contain B";
        assert set.contains(SampleEnum.C) : "Set should contain C";
        assert set.contains(SampleEnum.E) : "Set should contain E";
        assert set.size() == 4 : "Set size should be 4";
    }

    static void testEnumSetOfFourSame() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.C, SampleEnum.C, SampleEnum.C, SampleEnum.C);
        assert set.contains(SampleEnum.C) : "Set should contain C";
        assert set.size() == 1 : "Set size should be 1";
    }

    static void testEnumSetOfFourEmpty() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C, SampleEnum.D);
        set.clear();
        assert set.isEmpty() : "Set should be empty after clear";
    }
}
