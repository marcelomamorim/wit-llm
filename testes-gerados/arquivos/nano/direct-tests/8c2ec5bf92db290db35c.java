/* @test    @summary Test getItemAt method of MergeCollation class    @run main MergeCollationWitupTest */

import java.text.MergeCollation;
import java.text.PatternEntry;

public class MergeCollationWitupTest {

    public static void main(String[] args) {
        testGetItemAtReturnsCorrectPatternEntry();
        testGetItemAtWithInvalidIndex();
    }

    private static void testGetItemAtReturnsCorrectPatternEntry() {
        try {
            MergeCollation mc = new MergeCollation("a=b;c=d");
            PatternEntry entry = mc.getItemAt(0);
            if (entry == null) {
                throw new AssertionError("Expected non-null PatternEntry at index 0");
            }
            PatternEntry secondEntry = mc.getItemAt(1);
            if (secondEntry == null) {
                throw new AssertionError("Expected non-null PatternEntry at index 1");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetItemAtReturnsCorrectPatternEntry: " + e);
        }
    }

    private static void testGetItemAtWithInvalidIndex() {
        try {
            MergeCollation mc = new MergeCollation("a=b;c=d");
            mc.getItemAt(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative index: " + e);
        }
        try {
            MergeCollation mc = new MergeCollation("a=b;c=d");
            mc.getItemAt(100);
            throw new AssertionError("Expected IndexOutOfBoundsException for out-of-bounds index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for out-of-bounds index: " + e);
        }
    }
}
