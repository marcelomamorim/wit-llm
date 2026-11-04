/*
 * @test
 * @summary ThreadLocalRandom.doubles(long) rejects negative sizes, returns empty stream for zero, and produces values in [0.0, Double.MAX_VALUE)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNegativeSizeThrows();
        testNegativeLongMinThrows();
        testZeroSizeIsEmpty();
        testPositiveSizeCountAndRange();
        testStreamIsSequential();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeSizeThrows() {
        boolean thrown = false;
        try {
            ThreadLocalRandom.current().doubles(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "doubles(-1) должен выбросить IllegalArgumentException");
    }

    static void testNegativeLongMinThrows() {
        boolean thrown = false;
        try {
            ThreadLocalRandom.current().doubles(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "doubles(Long.MIN_VALUE) должен выбросить IllegalArgumentException");
    }

    static void testZeroSizeIsEmpty() {
        long count = ThreadLocalRandom.current().doubles(0L).count();
        check(count == 0L, "doubles(0) должен produzir поток без элементов, получено " + count);
    }

    static void testPositiveSizeCountAndRange() {
        double[] values = ThreadLocalRandom.current().doubles(1000L).toArray();
        check(values.length == 1000, "ожидалось 1000 элементов, получено " + values.length);
        for (int i = 0; i < values.length; i++) {
            double v = values[i];
            check(!Double.isNaN(v), "значение NaN на индексе " + i);
            check(v >= 0.0, "значение меньше 0.0 на индексе " + i + ": " + v);
            check(v < Double.MAX_VALUE, "значение >= Double.MAX_VALUE на индексе " + i + ": " + v);
        }
    }

    static void testStreamIsSequential() {
        DoubleStream s = ThreadLocalRandom.current().doubles(10L);
        check(!s.isParallel(), "поток de doubles não deve ser paralelo por padrão");
        s.close();
    }
}
