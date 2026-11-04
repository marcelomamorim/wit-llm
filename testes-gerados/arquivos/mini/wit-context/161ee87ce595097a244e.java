/*
 * @test
 * @summary Test Collections.UnmodifiableMap.remove throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // The remove method in UnmodifiableMap always throws UnsupportedOperationException
        // We test that calling remove indeed throws this exception
        try {
            Collections.UnmodifiableMap<String, String> umap = new Collections.UnmodifiableMap<>(new java.util.HashMap<>());
            umap.remove("key");
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception caught, test passes
        }
    }
}
