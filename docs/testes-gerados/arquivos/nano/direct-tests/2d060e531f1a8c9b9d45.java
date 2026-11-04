/* @test    @summary Test EnumSet.of(E e1, E e2, E e3) method for correctness    @run main EnumSetWitupTest */

import java.util.EnumSet;
import java.util.Set;

public class EnumSetWitupTest {

    enum SampleEnum {A, B, C, D}

    public static void main(String[] args) {
        testEnumSetOfThree();
        testEnumSetOfThreeWithDuplicates();
        testEnumSetOfThreeWithNulls();
    }

    static void testEnumSetOfThree() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C);
        assert set.contains(SampleEnum.A) : "Set should contain A";
        assert set.contains(SampleEnum.B) : "Set should contain B";
        assert set.contains(SampleEnum.C) : "Set should contain C";
        assert !set.contains(SampleEnum.D) : "Set should not contain D";
        assert set.size() == 3 : "Set size should be 3";
    }

    static void testEnumSetOfThreeWithDuplicates() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C);
        // Adding duplicates should not change the set
        set.add(SampleEnum.A);
        set.add(SampleEnum.B);
        set.add(SampleEnum.C);
        assert set.contains(SampleEnum.A) : "Set should contain A";
        assert set.contains(SampleEnum.B) : "Set should contain B";
        assert set.contains(SampleEnum.C) : "Set should contain C";
        assert set.size() == 3 : "Set size should remain 3";
    }

    static void testEnumSetOfThreeWithNulls() {
        try {
            EnumSet.of(null, SampleEnum.B, SampleEnum.C);
            assert false : "NullPointerException expected when first argument is null";
        } catch (NullPointerException e) {
            // expected
        }
        try {
            EnumSet.of(SampleEnum.A, null, SampleEnum.C);
            assert false : "NullPointerException expected when second argument is null";
        } catch (NullPointerException e) {
            // expected
        }
        try {
            EnumSet.of(SampleEnum.A, SampleEnum.B, null);
            assert false : "NullPointerException expected when third argument is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
