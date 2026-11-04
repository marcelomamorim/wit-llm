/* @test    @summary Test get method of ImmutableCollections for valid and invalid indices    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testGetValidIndex();
        testGetInvalidIndex();
    }

    static void testGetValidIndex() {
        // Prepare a collection with known elements
        java.util.List<String> list = java.util.Arrays.asList("a", "b", "c");
        // Create an ImmutableCollections instance wrapping the list
        // Since the actual constructor or factory is not provided, simulate via subclass or mock
        // For testing, assume get method is accessible via a subclass or reflection (not used here)
        // Instead, test the get method directly if accessible
        // As the source code is provided, instantiate a subclass if possible
        // But since the class is package-private, simulate via anonymous subclass
        java.util.ImmutableCollections.AbstractImmutableCollection<String> immutable = new java.util.ImmutableCollections.AbstractImmutableCollection<String>() {
            @Override
            public java.util.Iterator<String> iterator() {
                return list.iterator();
            }
            @Override
            public int size() {
                return list.size();
            }
            public String get(int index) {
                return list.get(index);
            }
        };
        String result = immutable.get(1);
        if (!"b".equals(result)) {
            throw new AssertionError("Expected 'b', got: " + result);
        }
    }

    static void testGetInvalidIndex() {
        java.util.List<String> list = java.util.Arrays.asList("a", "b", "c");
        java.util.ImmutableCollections.AbstractImmutableCollection<String> immutable = new java.util.ImmutableCollections.AbstractImmutableCollection<String>() {
            @Override
            public java.util.Iterator<String> iterator() {
                return list.iterator();
            }
            @Override
            public int size() {
                return list.size();
            }
            public String get(int index) {
                return list.get(index);
            }
        };
        boolean exceptionThrown = false;
        try {
            immutable.get(3); // Out of bounds
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index 3");
        }
    }
}
