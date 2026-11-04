/* @test    @summary Test for ImmutableCollections.replace method to verify it throws UnsupportedOperationException.    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceThrowsUOE();
    }

    public static void testReplaceThrowsUOE() {
        ImmutableCollections.AbstractImmutableMap<String, String> map = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override
            public String get(Object key) {
                return null;
            }
        };
        try {
            map.replace("key", "value");
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: replace() threw UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
