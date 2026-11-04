/* @test    @summary Test for Collections.CheckedCollection constructor behavior    @run main CollectionsWitupTest */

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testCheckedCollectionWithValidInputs();
        testCheckedCollectionWithNullCollection();
        testCheckedCollectionWithNullType();
    }

    private static void testCheckedCollectionWithValidInputs() {
        Collection<String> coll = new ArrayList<>();
        coll.add("test");
        Class<String> type = String.class;
        Collections.CheckedCollection<String> checkedColl = new Collections.CheckedCollection<>(coll, type);
        assert checkedColl != null : "CheckedCollection should not be null";
        assert checkedColl.contains("test") : "CheckedCollection should contain 'test'";
    }

    private static void testCheckedCollectionWithNullCollection() {
        try {
            Class<String> type = String.class;
            new Collections.CheckedCollection<>(null, type);
            assert false : "Expected NullPointerException for null collection";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("c") : "Exception message should be 'c'";
        }
    }

    private static void testCheckedCollectionWithNullType() {
        Collection<String> coll = new ArrayList<>();
        try {
            new Collections.CheckedCollection<>(coll, null);
            assert false : "Expected NullPointerException for null type";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("type") : "Exception message should be 'type'";
        }
    }
}
