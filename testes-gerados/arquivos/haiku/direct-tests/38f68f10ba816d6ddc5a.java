/*
 * @test
 * @summary Verifies observable behavior of UnicodeSet.add(int) for single code points, return value, supplementary code points and frozen sets
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) throws Exception {
        testAddReturnsSameSetAndContainsCodePoint();
        testAddSupplementaryCodePoint();
        testAddExistingCodePointKeepsMembership();
        testAddOnFrozenSetThrows();
    }

    static void testAddReturnsSameSetAndContainsCodePoint() {
        UnicodeSet set = new UnicodeSet(0x41, 0x41);
        UnicodeSet result = set.add(0x42);
        if (result != set) {
            throw new RuntimeException("add(int) должен возвращать тот же объект");
        }
        if (!set.contains(0x42)) {
            throw new RuntimeException("código U+0042 deveria estar no conjunto após add");
        }
        if (!set.contains(0x41)) {
            throw new RuntimeException("elemento original deveria permanecer no conjunto");
        }
    }

    static void testAddSupplementaryCodePoint() {
        UnicodeSet set = new UnicodeSet(0x41, 0x41);
        set.add(0x1F600);
        if (!set.contains(0x1F600)) {
            throw new RuntimeException("code point suplementar deveria estar no conjunto após add");
        }
        if (set.contains(0x1F601)) {
            throw new RuntimeException("code point não adicionado não deveria estar no conjunto");
        }
    }

    static void testAddExistingCodePointKeepsMembership() {
        UnicodeSet set = new UnicodeSet(0x61, 0x61);
        set.add(0x61);
        if (!set.contains(0x61)) {
            throw new RuntimeException("add de elemento já presente não deveria removê-lo");
        }
    }

    static void testAddOnFrozenSetThrows() {
        UnicodeSet set = new UnicodeSet(0x41, 0x41);
        set.freeze();
        boolean thrown = false;
        try {
            set.add(0x42);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("add(int) em conjunto congelado deveria lançar UnsupportedOperationException");
        }
        if (set.contains(0x42)) {
            throw new RuntimeException("conjunto congelado não deveria ser modificado");
        }
    }
}
