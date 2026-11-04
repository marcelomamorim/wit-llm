/* @test    @summary Test computeIfPresent method in ImmutableCollections to ensure it throws UnsupportedOperationException.    @run main ImmutableCollectionsWitupTest */

import java.util.*;
import java.util.function.BiFunction;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testComputeIfPresentThrows();
    }

    static void testComputeIfPresentThrows() {
        ImmutableCollections ic = new ImmutableCollections();
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        try {
            // Since computeIfPresent is overridden to throw uoe(), calling it should throw UnsupportedOperationException
            @SuppressWarnings("unchecked")
            V result = (V) ic.computeIfPresent("key1", (k, v) -> v + "_updated");
            // If no exception, fail the test
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
