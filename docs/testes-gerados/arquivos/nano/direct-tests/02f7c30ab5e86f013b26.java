/*
 * @test
 * @summary Test JrtPath.toFile() method for UnsupportedOperationException
 * @run main JrtPathWitupTest
 */
import java.io.File;
import jdk.internal.jrtfs.JrtPath;
public class JrtPathWitupTest {
    public static void main(String[] args) {
        JrtPath path = new JrtPath(null, "");
        try {
            path.toFile();
            System.out.println("Test failed: no exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException caught");
        } catch (Throwable t) {
            System.out.println("Test failed: unexpected exception: " + t);
        }
    }
}
