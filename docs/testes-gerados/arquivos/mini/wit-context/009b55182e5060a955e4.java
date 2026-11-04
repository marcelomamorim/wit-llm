/*
 * @test
 * @summary Test EnumSet.of(E e1, E e2) for normal and exceptional cases
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum { A, B, C }

    public static void main(String[] args) {
        testOfWithValidEnums();
        testOfWithNullFirstEnum();
        testOfWithNullSecondEnum();
    }

    private static void testOfWithValidEnums() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B);
        if (!set.contains(SampleEnum.A) || !set.contains(SampleEnum.B)) {
            throw new AssertionError("EnumSet.of did not contain expected elements");
        }
        if (set.size() != 2) {
            throw new AssertionError("EnumSet.of size incorrect");
        }
    }

    private static void testOfWithNullFirstEnum() {
        try {
            EnumSet.of(null, SampleEnum.B);
            throw new AssertionError("Expected NullPointerException for null first argument");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null first argument: " + e);
        }
    }

    private static void testOfWithNullSecondEnum() {
        try {
            EnumSet.of(SampleEnum.A, null);
            throw new AssertionError("Expected NullPointerException for null second argument");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null second argument: " + e);
        }
    }
}
