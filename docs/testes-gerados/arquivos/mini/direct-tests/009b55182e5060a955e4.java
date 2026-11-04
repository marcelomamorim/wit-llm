/*
 * @test
 * @summary Test EnumSet.of(E e1, E e2) method
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum {
        A, B, C
    }

    public static void main(String[] args) {
        // Create EnumSet with two elements
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B);

        // Check that both elements are contained
        if (!set.contains(SampleEnum.A)) {
            throw new AssertionError("EnumSet should contain A");
        }
        if (!set.contains(SampleEnum.B)) {
            throw new AssertionError("EnumSet should contain B");
        }

        // Check that size is exactly 2
        if (set.size() != 2) {
            throw new AssertionError("EnumSet size should be 2 but was " + set.size());
        }

        // Check that other enum constants are not contained
        if (set.contains(SampleEnum.C)) {
            throw new AssertionError("EnumSet should not contain C");
        }
    }
}
