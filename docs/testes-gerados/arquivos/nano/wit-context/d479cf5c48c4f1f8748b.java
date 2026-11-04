/*
 * @test
 * @summary Test for ImmutableCollections.compute method to verify exception throwing behavior.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.*;
import java.util.function.BiFunction;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testComputeThrowsUOE();
    }

    public static void testComputeThrowsUOE() {
        ImmutableCollections instance = new ImmutableCollections();
        Map<String, String> map = new HashMap<>();
        BiFunction<String, String, String> rf = (k, v) -> v;
        try {
            instance.compute("key", rf);
            System.out.println("Test failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("Test failed: Caught unexpected exception: " + e);
        }
    }
}
