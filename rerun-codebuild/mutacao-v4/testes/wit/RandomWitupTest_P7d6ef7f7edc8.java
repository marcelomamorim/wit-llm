/* @test
   @summary Verifica o método longs() da classe java.util.Random.
   @run main RandomWitupTest_P7d6ef7f7edc8
 */
import java.util.*;
public class RandomWitupTest_P7d6ef7f7edc8 {
    public static void main(String[] args) {
        Random rnd = new Random();
        LongStream stream = rnd.longs();
        long[] values = stream.limit(5).toArray();
        if (values.length != 5) {
            throw new AssertionError("Expected 5 elements, got " + values.length);
        }
        for (long v : values) {
            if (v < 0 || v > Long.MAX_VALUE) {
                throw new AssertionError("Value out of range: " + v);
            }
        }
    }
}
