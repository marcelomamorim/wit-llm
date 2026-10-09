/*
 * @test
 * @summary Test java.util.Vector.elementAt(int) method behavior
 * @run main VectorWitupTest_Pcc8d84f9def0
 */

import java.util.Vector;

public class VectorWitupTest_Pcc8d84f9def0 {
    public static void main(String[] args) {
        testElementAtValidIndex();
        testElementAtInvalidIndex();
    }

    private static void testElementAtValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("zero");
        v.add("one");
        v.add("two");

        // Test elementAt for valid indices
        String e0 = v.elementAt(0);
        if (!"zero".equals(e0)) {
            throw new RuntimeException("elementAt(0) returned wrong element: " + e0);
        }

        String e1 = v.elementAt(1);
        if (!"one".equals(e1)) {
            throw new RuntimeException("elementAt(1) returned wrong element: " + e1);
        }

        String e2 = v.elementAt(2);
        if (!"two".equals(e2)) {
            throw new RuntimeException("elementAt(2) returned wrong element: " + e2);
        }
    }

    private static void testElementAtInvalidIndex() {
        Vector<String> v = new Vector<>();
        v.add("only");

        try {
            v.elementAt(1); // index == elementCount, should throw
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 1");
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("1 >= 1")) {
                throw new RuntimeException("Exception message incorrect or missing: " + msg);
            }
        }

        try {
            v.elementAt(-1); // negative index, should throw
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // message may vary, just accept exception
        }
    }
}
