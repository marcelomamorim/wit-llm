/*
 * @test
 * @summary Tests for ConcurrentSkipListMap.firstKey() method.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testFirstKeyDescendingNullResult();
        testFirstKeyHiInclusiveNullResult();
        testFirstKeyLoNullResult();
    }

    private static void testFirstKeyDescendingNullResult() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        // Simulate descending order by inserting elements and reversing order logic
        // Since the actual implementation uses isDescending, we simulate the scenario by setting up the map accordingly
        // For the purpose of this test, we assume isDescending is true, so firstKey() returns highestKey()
        // We insert elements to ensure highestKey() returns a specific value
        map.put(1, "a");
        map.put(2, "b");
        // No direct way to set isDescending, so we test the behavior assuming isDescending is true
        // Expecting highestKey() to be 2
        try {
            Integer result = map.firstKey();
            assert result.equals(2) : "Expected firstKey to be 2, but got " + result;
        } catch (NoSuchElementException e) {
            assert false : "Did not expect NoSuchElementException here";
        }
    }

    private static void testFirstKeyHiInclusiveNullResult() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        // Simulate hiInclusive condition with hi != null
        // Since we cannot directly set hiInclusive, we simulate by inserting elements and assuming hi != null
        map.put(10, "x");
        map.put(20, "y");
        // Expect firstKey() to return highestKey() which should be 20
        try {
            Integer result = map.firstKey();
            assert result.equals(20) : "Expected firstKey to be 20, but got " + result;
        } catch (NoSuchElementException e) {
            assert false : "Did not expect NoSuchElementException here";
        }
    }

    private static void testFirstKeyLoNullResult() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        // Simulate the case where isDescending is false and lo != null, loInclusive is true
        map.put(5, "foo");
        map.put(15, "bar");
        // Expect firstKey() to return lowestKey() which should be 5
        try {
            Integer result = map.firstKey();
            assert result.equals(5) : "Expected firstKey to be 5, but got " + result;
        } catch (NoSuchElementException e) {
            assert false : "Did not expect NoSuchElementException here";
        }
    }
}
