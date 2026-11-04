/*
 * @test
 * @summary Verify UnicodeSet.clear() returns this on a mutable set and throws UnsupportedOperationException on a frozen set
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */
import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        UnicodeSet set = new UnicodeSet(0x41, 0x5A);
        if (set.clear() != set) {
            throw new RuntimeException("clear() should return this for a mutable set");
        }

        UnicodeSet frozen = new UnicodeSet(0x41, 0x5A);
        frozen.freeze();
        boolean thrown = false;
        try {
            frozen.clear();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("clear() on a frozen set should throw UnsupportedOperationException");
        }
    }
}
