/*
 * @test
 * @summary Test EnumSet.of(E e1, E e2, E e3) method
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum {
        A, B, C, D
    }

    public static void main(String[] args) {
        // Create EnumSet with three elements
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C);

        // Check size
        if (set.size() != 3) {
            throw new AssertionError("Expected size 3 but got " + set.size());
        }

        // Check contains each element
        if (!set.contains(SampleEnum.A)) {
            throw new AssertionError("Set does not contain A");
        }
        if (!set.contains(SampleEnum.B)) {
            throw new AssertionError("Set does not contain B");
        }
        if (!set.contains(SampleEnum.C)) {
            throw new AssertionError("Set does not contain C");
        }

        // Check does not contain an element not added
        if (set.contains(SampleEnum.D)) {
            throw new AssertionError("Set should not contain D");
        }

        // Check that all elements are from the same enum type
        Class<?> enumClass = SampleEnum.class;
        for (SampleEnum e : set) {
            if (e.getDeclaringClass() != enumClass) {
                throw new AssertionError("Element " + e + " is not from enum class " + enumClass);
            }
        }
    }
}
