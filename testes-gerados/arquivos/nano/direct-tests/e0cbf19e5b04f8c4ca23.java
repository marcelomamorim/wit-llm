/* @test    @summary Test UnicodeSet.set method for correct copying behavior    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testSetCopiesAllFields();
        testSetWithEmptyUnicodeSet();
        testSetWithSelf();
    }

    private static void testSetCopiesAllFields() {
        UnicodeSet original = new UnicodeSet(0x0041, 0x005A); // A-Z
        UnicodeSet copy = new UnicodeSet();
        copy.set(original);
        assert copy != null : "Copy should not be null";
        assert copy != original : "Copy should be a different instance";
        assert copy.list != original.list : "list should be a different array";
        assert copy.len == original.len : "len should be equal";
        assert copy.strings.equals(original.strings) : "strings set should be equal";
    }

    private static void testSetWithEmptyUnicodeSet() {
        UnicodeSet empty = new UnicodeSet();
        UnicodeSet target = new UnicodeSet(0x0000, 0x10FFFF);
        target.set(empty);
        assert target.list != null : "list should be initialized";
        assert target.len == 0 : "len should be zero after setting empty";
        assert target.strings.isEmpty() : "strings should be empty";
    }

    private static void testSetWithSelf() {
        UnicodeSet set = new UnicodeSet(0x0061, 0x007A); // a-z
        UnicodeSet before = new UnicodeSet();
        before.set(set);
        set.set(set); // setting to itself
        assert set != null : "Set should not be null";
        assert set.list != null : "list should not be null";
        assert set.len == before.len : "len should remain unchanged";
        assert set.strings.equals(before.strings) : "strings should remain unchanged";
    }
}
