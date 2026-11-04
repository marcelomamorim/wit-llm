/* @test    @summary Test for JumboEnumSet.remove() method to verify correct exception and state changes    @run main JumboEnumSetWitupTest */

import java.util.*;

public class JumboEnumSetWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutLastReturned();
        testRemoveWithLastReturned();
    }

    static enum SampleEnum { A, B, C }

    static class DummyJumboEnumSet extends JumboEnumSet<SampleEnum> {
        DummyJumboEnumSet(Class<SampleEnum> elementType, SampleEnum[] universe) {
            super(elementType, universe);
        }
        // Expose remove() for testing
        public void callRemove() {
            remove();
        }
    }

    static void testRemoveWithoutLastReturned() {
        SampleEnum[] universe = {SampleEnum.A, SampleEnum.B, SampleEnum.C};
        DummyJumboEnumSet set = new DummyJumboEnumSet(SampleEnum.class, universe);
        // Not setting lastReturned to 0, should throw IllegalStateException
        try {
            set.callRemove();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    static void testRemoveWithLastReturned() {
        SampleEnum[] universe = {SampleEnum.A, SampleEnum.B, SampleEnum.C};
        DummyJumboEnumSet set = new DummyJumboEnumSet(SampleEnum.class, universe);
        // Set lastReturned to a non-zero value to simulate valid state
        set.lastReturned = 1;
        set.lastReturnedIndex = 0;
        // Initialize elements array with a value that will be cleared
        set.elements = new long[] {1L, 0L};
        set.size = 2;
        set.callRemove();
        if (set.lastReturned != 0) {
            throw new AssertionError("lastReturned not reset to 0 after remove()");
        }
        if (set.elements[set.lastReturnedIndex] != (~1L)) {
            throw new AssertionError("elements array not updated correctly after remove()");
        }
        if (set.size != 1) {
            throw new AssertionError("size not decremented correctly after remove()");
        }
    }
}
