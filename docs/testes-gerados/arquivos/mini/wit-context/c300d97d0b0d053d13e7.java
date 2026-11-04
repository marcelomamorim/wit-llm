/*
 * @test
 * @summary Test FixedList.get(int) method for valid and invalid indices
 * @run main FixedListWitupTest
 */

import java.util.List;
import java.util.ArrayList;

public class FixedListWitupTest {

    public static void main(String[] args) {
        testGetValidIndex();
        testGetInvalidIndex();
    }

    private static void testGetValidIndex() {
        // Create FixedList with capacity 3
        FixedList<String> fl = new FixedList<String>(3);
        // Initially all elements are null
        for (int i = 0; i < fl.size(); i++) {
            if (fl.get(i) != null) {
                throw new AssertionError("Expected null at index " + i);
            }
        }
        // Set elements and verify get returns them
        fl.set(0, "zero");
        fl.set(1, "one");
        fl.set(2, "two");
        if (!"zero".equals(fl.get(0))) {
            throw new AssertionError("Expected 'zero' at index 0");
        }
        if (!"one".equals(fl.get(1))) {
            throw new AssertionError("Expected 'one' at index 1");
        }
        if (!"two".equals(fl.get(2))) {
            throw new AssertionError("Expected 'two' at index 2");
        }
    }

    private static void testGetInvalidIndex() {
        FixedList<String> fl = new FixedList<String>(2);
        // Test negative index
        try {
            fl.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // Test index equal to size
        try {
            fl.get(2);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 2");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // Test index greater than size
        try {
            fl.get(100);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 100");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
