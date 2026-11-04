/*
 * @test
 * @summary Verify Textifier.visitMaxs accepts boundary and repeated calls without throwing
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier t = new Textifier();
        int[][] values = {
            {0, 0},
            {-1, -1},
            {Integer.MIN_VALUE, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, Integer.MIN_VALUE},
            {1, 2}
        };
        for (int[] v : values) {
            t.visitMaxs(v[0], v[1]);
        }
        for (int i = 0; i < 3; i++) {
            t.visitMaxs(i, -i);
        }
    }
}
