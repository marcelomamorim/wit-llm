/*
 * @test
 * @summary Test MergeCollation.getItemAt(int) for valid and invalid indices
 * @run main MergeCollationWitupTest
 */

import java.text.MergeCollation;
import java.text.ParseException;
import java.text.PatternEntry;
import java.lang.IndexOutOfBoundsException;

public class MergeCollationWitupTest {
    public static void main(String[] args) throws Exception {
        // Construct with a simple valid pattern
        MergeCollation mc = new MergeCollation("a < b < c");

        // Test valid indices
        PatternEntry first = mc.getItemAt(0);
        if (first == null) {
            throw new RuntimeException("getItemAt(0) returned null");
        }

        PatternEntry last = mc.getItemAt(mc.patterns.size() - 1);
        if (last == null) {
            throw new RuntimeException("getItemAt(last) returned null");
        }

        // Test invalid index: negative
        try {
            mc.getItemAt(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test invalid index: equal to size
        try {
            mc.getItemAt(mc.patterns.size());
            throw new RuntimeException("Expected IndexOutOfBoundsException for index == size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test invalid index: greater than size
        try {
            mc.getItemAt(mc.patterns.size() + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
