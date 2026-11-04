/*
 * @test
 * @summary Test for constructor of jdk.internal.jimage.ImageLocation
 * @run main ImageLocationWitupTest
 */

import java.util.Objects;
import jdk.internal.jimage.ImageLocation;
import jdk.internal.jimage.ImageStrings;

public class ImageLocationWitupTest {

    public static void main(String[] args) {
        testConstructorNullAttributes();
        testConstructorNullStrings();
    }

    public static void testConstructorNullAttributes() {
        try {
            new ImageLocation(null, new ImageStrings());
            System.err.println("Failed: Expected NullPointerException for null attributes");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testConstructorNullStrings() {
        try {
            new ImageLocation(new long[8], null);
            System.err.println("Failed: Expected NullPointerException for null strings");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
