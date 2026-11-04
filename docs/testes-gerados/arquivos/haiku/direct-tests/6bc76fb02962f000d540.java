/*
 * @test
 * @summary Tests ZipEntry(String) constructor: null rejection, name length limit and name retention
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) throws Exception {
        retainsName();
        acceptsEmptyName();
        acceptsMaxLengthName();
        rejectsTooLongName();
        rejectsNullName();
    }

    static void retainsName() {
        ZipEntry e = new ZipEntry("a/b.txt");
        check("a/b.txt".equals(e.getName()), "name must be retained");
    }

    static void acceptsEmptyName() {
        ZipEntry e = new ZipEntry("");
        check("".equals(e.getName()), "empty name must be retained");
    }

    static void acceptsMaxLengthName() {
        String max = repeat('x', 0xFFFF);
        ZipEntry e = new ZipEntry(max);
        check(max.equals(e.getName()), "name of length 0xFFFF must be accepted");
    }

    static void rejectsTooLongName() {
        String tooLong = repeat('x', 0xFFFF + 1);
        try {
            new ZipEntry(tooLong);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for name longer than 0xFFFF");
    }

    static void rejectsNullName() {
        try {
            new ZipEntry((String) null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null name");
    }

    static String repeat(char c, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
