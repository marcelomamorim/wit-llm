/*
 * @test
 * @summary Verifies null handling and wrapper delegation of Collections.synchronizedCollection
 * @run main CollectionsWitupTest
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testWrapperDelegatesToBackingCollection();
    }

    static void testNullArgumentThrowsNPE() {
        try {
            Collections.synchronizedCollection(null);
            throw new RuntimeException("Expected NullPointerException for null collection");
        } catch (NullPointerException expected) {
        }
    }

    static void testWrapperDelegatesToBackingCollection() {
        List<String> backing = new ArrayList<>();
        Collection<String> wrapper = Collections.synchronizedCollection(backing);
        if (wrapper == null) {
            throw new RuntimeException("Wrapper must not be null");
        }
        if (!wrapper.isEmpty() || wrapper.size() != 0) {
            throw new RuntimeException("Wrapper of empty list must be empty");
        }
        wrapper.add("a");
        if (backing.size() != 1 || !backing.contains("a")) {
            throw new RuntimeException("Add through wrapper must be visible in backing collection");
        }
        if (wrapper.size() != 1 || !wrapper.contains("a")) {
            throw new RuntimeException("Wrapper must reflect backing collection contents");
        }
        if (!wrapper.toString().equals(backing.toString())) {
            throw new RuntimeException("Wrapper toString must match backing collection");
        }
    }
}
