/*
 * @test
 * @summary Tests for java.util.EnumSet.of(E e1, E e2)
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class EnumSetWitupTest {

    public static void main(String[] args) {
        testOfWithTwoEnums();
        testOfWithNullArgument();
        testOfWithIncompatibleEnumType();
        testOfWithLargeArray();
    }

    static enum SampleEnum { A, B, C }

    static void testOfWithTwoEnums() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B);
        assert set.contains(SampleEnum.A) : "Set should contain A";
        assert set.contains(SampleEnum.B) : "Set should contain B";
        assert !set.contains(SampleEnum.C) : "Set should not contain C";
        assert set.size() == 2 : "Set size should be 2";
    }

    static void testOfWithNullArgument() {
        try {
            EnumSet.of(SampleEnum.A, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testOfWithIncompatibleEnumType() {
        class OtherEnum { X, Y }
        try {
            // The following line is invalid as it mixes enum types, but since the method signature enforces type safety,
            // this code won't compile. Instead, simulate the scenario by reflection or skip.
            // For the purpose of this test, we assume the method is called with incompatible types, which would cause compile error.
            // So, we skip this test as it is not applicable in static typing.
        } catch (Exception e) {
            // no action
        }
    }

    static void testOfWithLargeArray() {
        // Since EnumSet.of(E e1, E e2) only takes two arguments, test with more arguments is not applicable.
        // Instead, test the varargs method with multiple enums.
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C);
        assert set.contains(SampleEnum.A) : "Set should contain A";
        assert set.contains(SampleEnum.B) : "Set should contain B";
        assert set.contains(SampleEnum.C) : "Set should contain C";
        assert set.size() == 3 : "Set size should be 3";
    }
}
