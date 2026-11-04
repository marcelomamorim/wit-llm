/*
 * @test
 * @summary Tests for java.util.EnumSet.of(E e) method.
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class EnumSetWitupTest {

    public static void main(String[] args) {
        testOfWithSingleEnum();
        testOfWithNull();
        testOfWithUnsupportedOperationException();
    }

    static void testOfWithSingleEnum() {
        // Test normal behavior with a valid enum constant
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.VALUE1);
        assert set.contains(SampleEnum.VALUE1) : "Set should contain VALUE1";
        assert set.size() == 1 : "Set size should be 1";
    }

    static void testOfWithNull() {
        // Test behavior when passing null, expecting NullPointerException
        try {
            EnumSet.of(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testOfWithUnsupportedOperationException() {
        // Test behavior when add is unsupported, simulate by subclassing
        class UnsupportedAddEnumSet<E extends Enum<E>> extends EnumSet<E> {
            protected UnsupportedAddEnumSet(Class<E> elementType) {
                super(elementType);
            }
            @Override
            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }
        }
        try {
            EnumSet<SampleEnum> set = new UnsupportedAddEnumSet<>(SampleEnum.class);
            set.add(SampleEnum.VALUE1);
            assert false : "Expected UnsupportedOperationException";
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }

    enum SampleEnum {
        VALUE1, VALUE2, VALUE3
    }
}
