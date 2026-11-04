/* @test    @summary Test insert method of StringBuilder for index out of bounds exceptions    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertIndexOutOfBounds();
    }

    private static void testInsertIndexOutOfBounds() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(-1, 1);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected exception for negative index");
        }
        try {
            sb.insert(sb.length() + 1, 1);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for index greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected exception for index greater than length");
        }
        // Valid insertion for control
        try {
            StringBuilder sb2 = new StringBuilder("abc");
            sb2.insert(1, 9);
            if ("a9bc".equals(sb2.toString())) {
                System.out.println("Passed: Valid insert at valid index");
            } else {
                System.out.println("Failed: Unexpected result for valid insert");
            }
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception for valid insert: " + e);
        }
    }
}
