/*
 * @test
 * @summary Verify Hashtable.contains(Object) behavior for null values, present values, absent values and non-empty tables
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNullValueThrowsNullPointerException();
        testContainsReturnsTrueForPresentValue();
        testContainsReturnsFalseForAbsentValue();
        testContainsReturnsFalseOnEmptyTable();
        testContainsAfterRemoveReturnsFalse();
        testContainsWithEqualButDistinctValue();
    }

    static void testNullValueThrowsNullPointerException() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        boolean thrown = false;
        try {
            table.contains(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null value");
        }
    }

    static void testContainsReturnsTrueForPresentValue() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        table.put("two", 2);
        if (!table.contains(2)) {
            throw new RuntimeException("contains should return true for present value");
        }
    }

    static void testContainsReturnsFalseForAbsentValue() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        if (table.contains(3)) {
            throw new RuntimeException("contains should return false for absent value");
        }
    }

    static void testContainsReturnsFalseOnEmptyTable() {
        Hashtable<String, Integer> table = new Hashtable<>();
        if (table.contains(1)) {
            throw new RuntimeException("contains should return false on empty table");
        }
    }

    static void testContainsAfterRemoveReturnsFalse() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        table.remove("one");
        if (table.contains(1)) {
            throw new RuntimeException("contains should return false after removal");
        }
    }

    static void testContainsWithEqualButDistinctValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", new String("value"));
        if (!table.contains(new String("value"))) {
            throw new RuntimeException("contains should use equals semantics");
        }
    }
}
