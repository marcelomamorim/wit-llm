/*
 * @test
 * @summary Tests File(File parent, String child) constructor: null child, null parent, parent resolution and child normalization
 * @run main FileWitupTest
 */

import java.io.File;

public class FileWitupTest {

    public static void main(String[] args) throws Exception {
        testNullChildThrowsNPE();
        testNullParentUsesChildOnly();
        testParentResolution();
        testChildNormalization();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullChildThrowsNPE() {
        try {
            new File(new File("dir"), null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullParentUsesChildOnly() {
        File f = new File((File) null, "a");
        check(f.getPath().equals(new File("a").getPath()),
              "null parent should yield normalized child path, got: " + f.getPath());
    }

    static void testParentResolution() {
        File parent = new File("dir");
        File f = new File(parent, "file.txt");
        check(f.getPath().equals("dir" + File.separator + "file.txt"),
              "unexpected path: " + f.getPath());
        check("dir".equals(f.getParent()),
              "unexpected parent: " + f.getParent());
    }

    static void testChildNormalization() {
        File a = new File(new File("dir"), "x//y/");
        File b = new File(new File("dir"), "x" + File.separator + "y");
        check(a.getPath().equals(b.getPath()),
              "child should be normalized: " + a.getPath() + " vs " + b.getPath());
    }
}
