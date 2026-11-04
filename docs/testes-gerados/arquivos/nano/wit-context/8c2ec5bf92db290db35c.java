/*
 * @test
 * @summary Test for MergeCollation.getItemAt(int)
 * @run main MergeCollationWitupTest
 */

import java.text.MergeCollation;
import java.text.PatternEntry;

public class MergeCollationWitupTest {

    public static void main(String[] args) throws Exception {
        testGetItemAtValidIndex();
        testGetItemAtNegativeIndex();
        testGetItemAtIndexEqualToSize();
    }

    private static void testGetItemAtValidIndex() throws Exception {
        MergeCollation mc = new MergeCollation("a/b/c");
        PatternEntry entry = mc.getItemAt(0);
        if (entry == null) {
            throw new AssertionError("Expected non-null PatternEntry at index 0");
        }
    }

    private static void testGetItemAtNegativeIndex() {
        try {
            MergeCollation mc = new MergeCollation("a/b/c");
            mc.getItemAt(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetItemAtIndexEqualToSize() throws Exception {
        MergeCollation mc = new MergeCollation("a/b/c");
        int size = mc.patterns.size();
        try {
            mc.getItemAt(size);
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
