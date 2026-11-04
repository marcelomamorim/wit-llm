/*
 * @test
 * @summary Test EnumSet.of(E e) factory method
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum {
        A, B, C
    }

    public static void main(String[] args) {
        // Create EnumSet with one element
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.B);

        // Check that the set contains exactly the element added
        if (!set.contains(SampleEnum.B)) {
            throw new RuntimeException("EnumSet does not contain the expected element");
        }

        if (set.size() != 1) {
            throw new RuntimeException("EnumSet size is not 1 as expected");
        }

        // Check that other elements are not contained
        if (set.contains(SampleEnum.A) || set.contains(SampleEnum.C)) {
            throw new RuntimeException("EnumSet contains unexpected elements");
        }

        // Check that the EnumSet's element type is SampleEnum
        Class<?> elementType = set.iterator().next().getDeclaringClass();
        if (elementType != SampleEnum.class) {
            throw new RuntimeException("EnumSet element type is not SampleEnum");
        }
    }
}
