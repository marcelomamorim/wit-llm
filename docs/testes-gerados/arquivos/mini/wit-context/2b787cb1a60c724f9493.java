/*
 * @test
 * @summary Test ImmutableCollections.contains(Object) for expected behavior and NPE on null
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        ImmutableCollections.AbstractImmutableList<String> list = new ImmutableCollections.AbstractImmutableList<String>() {
            public int size() { return 3; }
            public String get(int index) {
                switch (index) {
                    case 0: return "a";
                    case 1: return "b";
                    case 2: return "c";
                    default: throw new IndexOutOfBoundsException();
                }
            }
            public int indexOf(Object o) {
                if (o == null) throw new NullPointerException();
                for (int i = 0; i < size(); i++) {
                    if (o.equals(get(i))) return i;
                }
                return -1;
            }
        };

        // Test contains with present element
        if (!list.contains("a")) {
            throw new AssertionError("contains should return true for element 'a'");
        }

        // Test contains with absent element
        if (list.contains("z")) {
            throw new AssertionError("contains should return false for element 'z'");
        }

        // Test contains with null triggers NullPointerException
        boolean npeThrown = false;
        try {
            list.contains(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("contains(null) should throw NullPointerException");
        }

        System.out.println("ImmutableCollections.contains tests passed");
    }
}
