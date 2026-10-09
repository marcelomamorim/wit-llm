/* @test
   @summary Verifies that the longs() method returns a LongStream that produces a sequence of long values.
   @run main RandomWitupTest_P7d6ef7f7edc8
 */
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import java.util.Random;
public class RandomWitupTest_P7d6ef7f7edc8 {
    public static void main(String[] args) {
        Random random = new Random(12345L);
        LongStream stream = random.longs();
        long[] values = stream.limit(5).toArray();
        long[] expected = { -1155484573019327328L,  7640891576956012808L,  1352713924983579352L,  6129484611666145827L,  1260499278207360297L };
        for (int i = 0; i < 5; i++) {
            if (values[i] != expected[i]) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + expected[i] + " but got " + values[i]);
            }
        }
    }
}
