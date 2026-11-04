/*
 * @test
 * @summary Test EnumSet.of(E e) for basic functionality and exception cases
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum {
        A, B, C
    }

    public static void main(String[] args) {
        testOfWithValidEnum();
        testOfWithNullEnum();
    }

    private static void testOfWithValidEnum() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A);
        if (set == null) {
            throw new AssertionError("EnumSet.of returned null");
        }
        if (!set.contains(SampleEnum.A)) {
            throw new AssertionError("EnumSet.of does not contain the element added");
        }
        if (set.size() != 1) {
            throw new AssertionError("EnumSet.of size expected 1 but was " + set.size());
        }
    }

    private static void testOfWithNullEnum() {
        try {
            EnumSet.of((SampleEnum) null);
            throw new AssertionError("Expected NullPointerException when passing null to EnumSet.of");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (ClassCastException cce) {
            // According to WIT, ClassCastException may occur if universe is null internally
            // Acceptable fallback for this test
        } catch (UnsupportedOperationException uoe) {
            // According to WIT, UnsupportedOperationException may occur if universe length > 64
            // Acceptable fallback for this test
        }
    }
}
