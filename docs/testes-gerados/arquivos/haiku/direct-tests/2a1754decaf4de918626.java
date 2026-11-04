/*
 * @test
 * @summary Witup-style tests for File(String pathname) constructor
 * @run main FileWitupTest
 */

public class FileWitupTest {

    public static void main(String[] args) {
        testNullPathnameThrowsNPE();
        testEmptyPathnameKeepsEmptyPath();
        testSimpleNamePreserved();
        testTrailingSeparatorIsRemoved();
        testDuplicateSeparatorsCollapsed();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullPathnameThrowsNPE() {
        try {
            new File((String) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null pathname");
    }

    static void testEmptyPathnameKeepsEmptyPath() {
        File f = new File("");
        check(f.getPath().equals(""), "empty pathname should yield empty path, got: " + f.getPath());
    }

    static void testSimpleNamePreserved() {
        File f = new File("name.txt");
        check(f.getPath().equals("name.txt"), "path changed: " + f.getPath());
        check(f.getName().equals("name.txt"), "name changed: " + f.getName());
    }

    static void testTrailingSeparatorIsRemoved() {
        String sep = String.valueOf(File.separatorChar);
        File f = new File("a" + sep);
        check(f.getPath().equals("a"), "trailing separator not normalized: " + f.getPath());
    }

    static void testDuplicateSeparatorsCollapsed() {
        String sep = String.valueOf(File.separatorChar);
        File f = new File("a" + sep + sep + "b");
        check(f.getPath().equals("a" + sep + "b"),
              "duplicate separators not collapsed: " + f.getPath());
    }
}
