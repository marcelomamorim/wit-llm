/*
 * @test
 * @summary Test java.util.Collections.nCopies method for valid and invalid inputs
 * @run main CollectionsWitupTest_Pbee65c942b10
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest_Pbee65c942b10 {
    public static void main(String[] args) {
        testNegativeNThrows();
        testZeroCopies();
        testPositiveCopies();
        testCopiesContainSameObject();
    }

    private static void testNegativeNThrows() {
        try {
            Collections.nCopies(-1, "test");
            throw new RuntimeException("Expected IllegalArgumentException for negative n");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!msg.contains("List length = -1")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
    }

    private static void testZeroCopies() {
        List<String> list = Collections.nCopies(0, "test");
        if (list == null) {
            throw new RuntimeException("nCopies returned null for n=0");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("Expected empty list for n=0");
        }
    }

    private static void testPositiveCopies() {
        final int n = 5;
        String element = "element";
        List<String> list = Collections.nCopies(n, element);
        if (list == null) {
            throw new RuntimeException("nCopies returned null for positive n");
        }
        if (list.size() != n) {
            throw new RuntimeException("Expected list size " + n + " but got " + list.size());
        }
        for (String s : list) {
            if (!element.equals(s)) {
                throw new RuntimeException("List element does not match expected object");
            }
        }
    }

    private static void testCopiesContainSameObject() {
        Object obj = new Object();
        List<Object> list = Collections.nCopies(3, obj);
        for (Object o : list) {
            if (o != obj) {
                throw new RuntimeException("List elements are not the same object instance");
            }
        }
    }
}
