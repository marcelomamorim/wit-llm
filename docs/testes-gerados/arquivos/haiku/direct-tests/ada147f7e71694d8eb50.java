/*
 * @test
 * @summary ImageLocation public constructor rejects null arguments with NullPointerException
 * @modules java.base/jdk.internal.jimage
 * @run main ImageLocationWitupTest
 */

import jdk.internal.jimage.ImageLocation;

public class ImageLocationWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static void expectNpe(String label, Action action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        } catch (Exception e) {
            throw new RuntimeException(label + ": unexpected exception " + e, e);
        }
        throw new RuntimeException(label + ": expected NullPointerException");
    }

    public static void main(String[] args) {
        expectNpe("null attributes and null strings",
                  () -> new ImageLocation(null, null));
        expectNpe("null strings",
                  () -> new ImageLocation(new long[ImageLocation.ATTRIBUTE_COUNT], null));
        expectNpe("null attributes",
                  () -> new ImageLocation(null, null));
    }
}
