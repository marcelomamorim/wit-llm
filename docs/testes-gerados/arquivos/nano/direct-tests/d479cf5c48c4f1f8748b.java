/* @test    @summary Test compute method in ImmutableCollections to ensure it throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.*;
import java.util.function.BiFunction;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testComputeThrows();
    }

    public static void testComputeThrows() {
        try {
            // Instantiate an anonymous subclass to access the compute method
            ImmutableCollections ic = new ImmutableCollections() {
                @Override public <K, V> V compute(K key, BiFunction<? super K, ? super V, ? extends V> rf) {
                    return super.compute(key, rf);
                }
            };
            // Call compute with dummy arguments
            ic.compute("key", (k, v) -> null);
            // If no exception, fail the test
            System.err.println("FAIL: compute did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Expected exception
            System.out.println("PASS: compute threw UnsupportedOperationException as expected");
        } catch (Throwable t) {
            System.err.println("FAIL: compute threw unexpected exception: " + t);
        }
    }
}
