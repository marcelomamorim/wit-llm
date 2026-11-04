/*
 * @test
 * @summary Test getItemAt method of MergeCollation for valid and invalid indices
 * @run main MergeCollationWitupTest
 */

import java.text.MergeCollation;
import java.text.ParseException;
import java.text.PatternEntry;

public class MergeCollationWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a MergeCollation with a simple pattern
        MergeCollation mc = new MergeCollation("a < b < c");

        // Test getItemAt with valid indices
        PatternEntry first = mc.getItemAt(0);
        if (first == null) {
            throw new RuntimeException("getItemAt(0) returned null");
        }

        PatternEntry second = mc.getItemAt(1);
        if (second == null) {
            throw new RuntimeException("getItemAt(1) returned null");
        }

        PatternEntry third = mc.getItemAt(2);
        if (third == null) {
            throw new RuntimeException("getItemAt(2) returned null");
        }

        // Test that the entries are distinct
        if (first == second || second == third || first == third) {
            throw new RuntimeException("getItemAt returned duplicate PatternEntry instances");
        }

        // Test getItemAt with invalid negative index - expect IndexOutOfBoundsException
        try {
            mc.getItemAt(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for getItemAt(-1)");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test getItemAt with index equal to size - expect IndexOutOfBoundsException
        int size = 3; // from pattern "a < b < c" we expect 3 entries
        try {
            mc.getItemAt(size);
            throw new RuntimeException("Expected IndexOutOfBoundsException for getItemAt(size)");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        System.out.println("PASS");
    }
}
