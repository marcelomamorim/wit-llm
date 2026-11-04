/*
 * @test
 * @summary Verifies that UnicodeSet.cloneAsThawed() returns an unfrozen copy with the same members and leaves the source set frozen
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */
import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        UnicodeSet frozen = new UnicodeSet(0x41, 0x5A);
        frozen.freeze();
        UnicodeSet thawed = frozen.cloneAsThawed();
        if (thawed == null) {
            fail("cloneAsThawed returned null");
        }
        if (thawed.isFrozen()) {
            fail("clone of a frozen set must not be frozen");
        }
        if (!frozen.isFrozen()) {
            fail("source set must remain frozen after cloneAsThawed");
        }
        if (thawed == frozen) {
            fail("cloneAsThawed must return a new object");
        }
        if (!thawed.contains(0x41) || !thawed.contains(0x5A)) {
            fail("clone must contain the members of the source set");
        }
        if (thawed.contains(0x61)) {
            fail("clone must not contain code points outside the source set");
        }

        UnicodeSet open = new UnicodeSet(0x41, 0x5A);
        UnicodeSet openClone = open.cloneAsThawed();
        if (openClone == null || openClone == open) {
            fail("cloneAsThawed of an unfrozen set must return a distinct object");
        }
        if (openClone.isFrozen()) {
            fail("clone of an unfrozen set must not be frozen");
        }
        if (!openClone.contains(0x41) || !openClone.contains(0x5A)) {
            fail("clone of an unfrozen set must keep its members");
        }
    }

    private static void fail(String msg) {
        throw new RuntimeException(msg);
    }
}
