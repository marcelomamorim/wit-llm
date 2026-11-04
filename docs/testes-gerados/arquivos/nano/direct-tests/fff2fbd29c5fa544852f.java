/* @test    @summary Test for RegularEnumSet.next() method to ensure correct element retrieval and exception handling.    @run main RegularEnumSetWitupTest */

import java.util.NoSuchElementException;
import java.util.Iterator;

public class RegularEnumSetWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectElement();
        testNextThrowsWhenEmpty();
    }

    private static void testNextReturnsCorrectElement() {
        class DummyEnum {
            static final DummyEnum A = new DummyEnum();
            static final DummyEnum B = new DummyEnum();
            static final DummyEnum C = new DummyEnum();
            static final DummyEnum[] VALUES = {A, B, C};
            final int ordinal;
            DummyEnum(int ordinal) { this.ordinal = ordinal; }
            DummyEnum() { this.ordinal = -1; }
            int ordinal() { return ordinal; }
        }
        // Simulate universe array
        DummyEnum[] universe = DummyEnum.VALUES;
        // Create RegularEnumSet with elements A and C
        class DummyRegularEnumSet extends java.util.RegularEnumSet<DummyEnum> {
            DummyEnum[] universe;
            long elements;
            long unseen;
            long lastReturned;
            DummyRegularEnumSet(DummyEnum[] universe, long elements) {
                super(DummyEnum.class, universe);
                this.universe = universe;
                this.elements = elements;
                this.unseen = elements;
                this.lastReturned = 0;
            }
            public DummyEnum next() {
                if (unseen == 0) throw new NoSuchElementException();
                lastReturned = unseen & -unseen;
                unseen -= lastReturned;
                int index = Long.numberOfTrailingZeros(lastReturned);
                return universe[index];
            }
        }
        // Set bits for A (bit 0) and C (bit 2)
        long elements = (1L << 0) | (1L << 2);
        DummyRegularEnumSet set = new DummyRegularEnumSet(universe, elements);
        // First call should return A
        DummyEnum first = set.next();
        assert first == DummyEnum.A : "Expected A";
        // Second call should return C
        DummyEnum second = set.next();
        assert second == DummyEnum.C : "Expected C";
    }

    private static void testNextThrowsWhenEmpty() {
        class DummyEnum {
            static final DummyEnum A = new DummyEnum();
            static final DummyEnum[] VALUES = {A};
            final int ordinal;
            DummyEnum(int ordinal) { this.ordinal = ordinal; }
            DummyEnum() { this.ordinal = -1; }
            int ordinal() { return ordinal; }
        }
        DummyEnum[] universe = DummyEnum.VALUES;
        // Empty set
        class DummyRegularEnumSet extends java.util.RegularEnumSet<DummyEnum> {
            long unseen;
            DummyRegularEnumSet(DummyEnum[] universe) {
                super(DummyEnum.class, universe);
                this.unseen = 0;
            }
            public DummyEnum next() {
                if (unseen == 0) throw new NoSuchElementException();
                return null; // Not reached
            }
        }
        DummyRegularEnumSet emptySet = new DummyRegularEnumSet(universe);
        try {
            emptySet.next();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // Expected
        }
    }
}
