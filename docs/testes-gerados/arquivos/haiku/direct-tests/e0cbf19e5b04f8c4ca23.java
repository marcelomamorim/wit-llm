/*
 * @test
 * @summary Verifies UnicodeSet.set(UnicodeSet) copies contents, returns this, and rejects frozen targets
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */
import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        UnicodeSet src = new UnicodeSet("[A-Z]");
        UnicodeSet target = new UnicodeSet("[x]");
        UnicodeSet ret = target.set(src);

        check(ret == target, "set must return this");
        check(target.contains('A'), "A must be copied");
        check(target.contains('Z'), "Z must be copied");
        check(!target.contains('x'), "previous content of target must be replaced");
        check(!target.contains('a'), "a must not be present");
        check(src.contains('A') && src.contains('Z'), "source must remain unchanged");

        UnicodeSet self = new UnicodeSet("[0-9]");
        UnicodeSet selfRet = self.set(self);
        check(selfRet == self, "self set must return this");
        check(self.contains('5'), "self set must keep contents");
        check(!self.contains('a'), "self set must not add foreign code points");

        UnicodeSet frozenSrc = new UnicodeSet("[a-c]");
        frozenSrc.freeze();
        UnicodeSet t2 = new UnicodeSet("[z]");
        t2.set(frozenSrc);
        check(t2.contains('b'), "copy from frozen source must contain b");
        check(!t2.contains('z'), "copy from frozen source must replace old content");

        UnicodeSet frozenTarget = new UnicodeSet("[q]");
        frozenTarget.freeze();
        boolean thrown = false;
        try {
            frozenTarget.set(src);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "set on frozen target must throw UnsupportedOperationException");
        check(frozenTarget.contains('q'), "frozen target must keep its content after failed set");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
