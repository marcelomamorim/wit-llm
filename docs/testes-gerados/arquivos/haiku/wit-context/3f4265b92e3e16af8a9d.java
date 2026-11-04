/*
 * @test
 * @summary Deterministic observable checks for Random.longs()
 * @run main RandomWitupTest
 */

public class RandomWitupTest {
    public static void main(String[] args) {
        Random r1 = new Random(42L);
        Random r2 = new Random(42L);
        long[] a = r1.longs().limit(16).toArray();
        long[] b = r2.longs().limit(16).toArray();
        if (a.length != 16) {
            throw new RuntimeException("unexpected length: " + a.length);
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                throw new RuntimeException("same seed produced different value at " + i);
            }
            if (a[i] < 0L) {
                throw new RuntimeException("negative value from longs(): " + a[i]);
            }
        }
        long zero = new Random(42L).longs().limit(0).count();
        if (zero != 0L) {
            throw new RuntimeException("limit(0) produced " + zero + " elements");
        }
        long distinct = new Random(7L).longs().limit(1000).distinct().count();
        if (distinct <= 900L) {
            throw new RuntimeException("too few distinct values: " + distinct);
        }
    }
}
