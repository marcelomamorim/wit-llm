/* @test    @summary Test computeIfAbsent method in ImmutableCollections to ensure it throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.*;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testComputeIfAbsentThrows();
    }

    static void testComputeIfAbsentThrows() {
        class DummyFunction implements java.util.function.Function<Object, Object> {
            @Override
            public Object apply(Object t) {
                return null;
            }
        }
        ImmutableCollections ic = new ImmutableCollections();
        try {
            ic.computeIfAbsent("key", new DummyFunction());
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
