/* @test    @summary Test for ImmutableCollections.replace method to observe exception behavior    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceMethodThrowsUOE();
    }

    private static void testReplaceMethodThrowsUOE() {
        ImmutableCollections.AbstractImmutableMap<String, String> instance = new ImmutableCollections.AbstractImmutableMap<String, String>() {
            @Override
            public boolean replace(String key, String oldValue, String newValue) {
                return super.replace(key, oldValue, newValue);
            }
        };
        try {
            boolean result = instance.replace("key", "oldVal", "newVal");
            if (result) {
                System.out.println("Unexpected success: replace did not throw.");
            } else {
                System.out.println("Replace returned false.");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("Caught unexpected exception: " + e);
        }
    }
}
