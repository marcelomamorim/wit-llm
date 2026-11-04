/* @test    @summary Test ZipEntry constructor with valid name    @run main ZipEntryWitupTest */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testValidName();
        testNullName();
        testLongName();
    }

    static void testValidName() {
        String name = "testEntry";
        ZipEntry entry = new ZipEntry(name);
        assert entry != null : "ZipEntry should not be null for valid name";
        assert entry.name.equals(name) : "Entry name should match input";
    }

    static void testNullName() {
        boolean thrown = false;
        try {
            new ZipEntry(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        assert thrown : "NullPointerException should be thrown for null name";
    }

    static void testLongName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 0xFFFF + 1; i++) {
            sb.append('a');
        }
        String longName = sb.toString();
        boolean thrown = false;
        try {
            new ZipEntry(longName);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assert thrown : "IllegalArgumentException should be thrown for name longer than 0xFFFF";
    }
}
