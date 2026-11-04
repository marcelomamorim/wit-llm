/*
 * @test
 * @summary Tests for java.util.Collections methods, focusing on CheckedCollection constructor.
 * @run main CollectionsWitupTest
 */

import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testCheckedCollectionWithNullCollection();
        testCheckedCollectionWithNullType();
    }

    public static void testCheckedCollectionWithNullCollection() {
        try {
            new Collections.CheckedCollection<Object>(null, String.class);
            System.out.println("Failed: Expected NullPointerException for null collection");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null collection");
        }
    }

    public static void testCheckedCollectionWithNullType() {
        Collection<Object> coll = java.util.Arrays.asList(1, 2, 3);
        try {
            new Collections.CheckedCollection<Object>(coll, null);
            System.out.println("Failed: Expected NullPointerException for null type");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null type");
        }
    }
}
