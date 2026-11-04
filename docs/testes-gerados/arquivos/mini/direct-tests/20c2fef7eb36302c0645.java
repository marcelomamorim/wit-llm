/*
 * @test
 * @summary Test EnumSet.of(E e1, E e2, E e3, E e4) method
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum { A, B, C, D, E }

    public static void main(String[] args) {
        // Create EnumSet with four elements
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B, SampleEnum.C, SampleEnum.D);

        // Check size
        if (set.size() != 4) {
            throw new RuntimeException("Expected size 4 but got " + set.size());
        }

        // Check contains all elements
        if (!set.contains(SampleEnum.A) || !set.contains(SampleEnum.B) || !set.contains(SampleEnum.C) || !set.contains(SampleEnum.D)) {
            throw new RuntimeException("EnumSet does not contain all expected elements");
        }

        // Check does not contain an element not added
        if (set.contains(SampleEnum.E)) {
            throw new RuntimeException("EnumSet contains an element that was not added");
        }

        // Check that the EnumSet's element type is SampleEnum
        Class<?> elementType = set.iterator().next().getDeclaringClass();
        if (elementType != SampleEnum.class) {
            throw new RuntimeException("EnumSet element type mismatch");
        }
    }
}
