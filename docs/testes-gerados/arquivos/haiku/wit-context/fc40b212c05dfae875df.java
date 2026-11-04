/*
 * @test
 * @summary Witup-generated checks for File(File parent, String child)
 * @run main FileWitupTest
 */

import java.io.File;

public class FileWitupTest {

    public static void main(String[] args) {
        testNullChildThrowsNPE();
        testNullChildWithNonNullParentThrowsNPE();
        testNullParentUsesNormalizedChild();
        testParentResolvesChild();
        testChildIsNormalized();
        testEmptyParentResolvesAgainstDefaultParent();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullChildThrowsNPE() {
        boolean thrown = false;
        try {
            new File((File) null, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null parent and null child");
    }

    static void testNullChildWithNonNullParentThrowsNPE() {
        boolean thrown = false;
        try {
            new File(new File("dir"), null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null child");
    }

    static void testNullParentUsesNormalizedChild() {
        File f = new File((File) null, "child");
        check(f.getPath().equals(new File("child").getPath()),
              "null parent should yield the child path, got: " + f.getPath());
    }

    static void testParentResolvesChild() {
        File f = new File(new File("dir"), "file.txt");
        String expected = new File("dir" + File.separator + "file.txt").getPath();
        check(f.getPath().equals(expected),
              "expected " + expected + " but got " + f.getPath());
        check(f.getParent() != null && f.getParent().equals(new File("dir").getPath()),
              "unexpected parent: " + f.getParent());
    }

    static void testChildIsNormalized() {
        File f = new File(new File("a"), "b//c/");
        String expected = new File("a" + File.separator + "b" + File.separator + "c").getPath();
        check(f.getPath().equals(expected),
              "expected normalized path " + expected + " but got " + f.getPath());
    }

    static void testEmptyParentResolvesAgainstDefaultParent() {
        File f = new File(new File(""), "x");
        String expected = new File(File.separator + "x").getPath();
        check(f.getPath().equals(expected),
              "expected " + expected + " but got " + f.getPath());
    }
}
