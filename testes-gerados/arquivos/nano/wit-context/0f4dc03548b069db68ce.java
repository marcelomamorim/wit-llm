/* @test    @summary Test EnumSet.noneOf with valid enum class    @run main EnumSetWitupTest */

import java.util.EnumSet;
import java.util.Random;

public class EnumSetWitupTest {

    public static void main(String[] args) {
        testNoneOfWithEnum();
        testNoneOfWithNull();
        testNoneOfWithNonEnumClass();
    }

    static void testNoneOfWithEnum() {
        try {
            EnumSet<SampleEnum> set = EnumSet.noneOf(SampleEnum.class);
            assert set != null : "EnumSet.noneOf should not return null";
            assert set.isEmpty() : "EnumSet should be empty after creation";
            System.out.println("testNoneOfWithEnum passed");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testNoneOfWithEnum", e);
        }
    }

    static void testNoneOfWithNull() {
        try {
            EnumSet<SampleEnum> set = EnumSet.noneOf(null);
            assert false : "Expected NullPointerException when passing null";
        } catch (NullPointerException e) {
            System.out.println("testNoneOfWithNull passed");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testNoneOfWithNull", e);
        }
    }

    static void testNoneOfWithNonEnumClass() {
        try {
            Class<String> nonEnumClass = String.class;
            EnumSet<String> set = EnumSet.noneOf(nonEnumClass);
            assert false : "Expected ClassCastException when passing non-enum class";
        } catch (ClassCastException e) {
            System.out.println("testNoneOfWithNonEnumClass passed");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testNoneOfWithNonEnumClass", e);
        }
    }

    enum SampleEnum {
        A, B, C
    }
}
