/* @test    @summary Test for EnumMap.remove() method to verify IllegalStateException when lastReturnedIndex < 0    @run main EnumMapWitupTest */

import java.util.EnumMap;
import java.util.NoSuchElementException;

public class EnumMapWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsWhenLastReturnedIndexNegative();
    }

    private static void testRemoveThrowsWhenLastReturnedIndexNegative() {
        EnumMap<SampleEnum, String> map = new EnumMap<>(SampleEnum.class);
        // Prepare map with one entry
        map.put(SampleEnum.A, "value");
        // Obtain iterator
        EnumMap<SampleEnum, String>.EntrySet entrySet = map.entrySet();
        java.util.Iterator<java.util.Map.Entry<SampleEnum, String>> iterator = entrySet.iterator();
        // Move iterator to first element
        if (iterator.hasNext()) {
            iterator.next();
        }
        // Remove element
        iterator.remove();
        // Now, forcibly set lastReturnedIndex to an invalid state to simulate the exception scenario
        // Since lastReturnedIndex is private, we simulate by calling remove() again without a valid last element
        // which should throw IllegalStateException as per the method's guard condition
        try {
            // Call remove again without a valid last returned element
            // To do this, create a custom iterator that sets lastReturnedIndex to -1 manually
            // but since lastReturnedIndex is private, we simulate by calling remove() after iterator is exhausted
            // and no element has been returned
            java.util.Iterator<java.util.Map.Entry<SampleEnum, String>> dummyIterator = entrySet.iterator();
            // Do not call next(), so lastReturnedIndex remains at default (-1)
            dummyIterator.remove();
            // If no exception, fail the test
            System.err.println("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    private enum SampleEnum {
        A, B, C;
    }
}
