/*
 * @test
 * @summary Verifies that the ImageLocation constructor rejects null arguments with NullPointerException
 * @modules java.base/jdk.internal.jimage
 * @run main ImageLocationWitupTest
 */

import jdk.internal.jimage.ImageLocation;

public class ImageLocationWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static int failures = 0;

    static void expectNpe(String name, Action action) {
        try {
            action.run();
            failures++;
            System.err.println("FAIL: no exception thrown for " + name);
        } catch (NullPointerException e) {
            System.out.println("PASS: " + name);
        } catch (Exception e) {
            failures++;
            System.err.println("FAIL: unexpected exception for " + name + ": " + e);
        }
    }

    public static void main(String[] args) {
        expectNpe("null attributes and null strings",
                () -> new ImageLocation((long[]) null, null));

        expectNpe("null attributes with non-null-array-free call",
                () -> new ImageLocation((long[]) null, null));

        expectNpe("non-null attributes with null strings",
                () -> new ImageLocation(new long[ImageLocation.ATTRIBUTE_COUNT], null));

        expectNpe("empty attributes with null strings",
                () -> new ImageLocation(new long[0], null));

        if (failures > 0) {
            throw new RuntimeException(failures + " test(s) failed");
        }
    }
}
