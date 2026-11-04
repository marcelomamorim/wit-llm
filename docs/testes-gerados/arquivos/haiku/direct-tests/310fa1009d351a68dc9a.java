/*
 * @test
 * @summary Witup-derived tests for UnicodeSet.cloneAsThawed
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */
import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        UnicodeSet original = new UnicodeSet(0x61, 0x7A);
        UnicodeSet clone = original.cloneAsThawed();
        if (clone == null) {
            throw new RuntimeException("cloneAsThawed returned null");
        }
        if (clone == original) {
            throw new RuntimeException("cloneAsThawed returned the same instance");
        }
        if (clone.isFrozen()) {
            throw new RuntimeException("clone must not be frozen");
        }

        UnicodeSet fromPattern = new UnicodeSet("[a-z]");
        UnicodeSet patternClone = fromPattern.cloneAsThawed();
        if (patternClone == null || patternClone == fromPattern) {
            throw new RuntimeException("invalid clone for pattern-based set");
        }
        if (patternClone.isFrozen()) {
            throw new RuntimeException("pattern-based clone must not be frozen");
        }

        UnicodeSet clone2 = original.cloneAsThawed();
        if (clone2 == clone) {
            throw new RuntimeException("successive clones must be distinct instances");
        }
        if (clone2.isFrozen()) {
            throw new RuntimeException("second clone must not be frozen");
        }
    }
}
