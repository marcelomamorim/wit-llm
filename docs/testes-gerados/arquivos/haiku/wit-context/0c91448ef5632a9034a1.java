/*
 * @test
 * @summary Checks parallel toArray over SIZED spliterators whose element count disagrees with the reported size
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

public class NodesWitupTest {

    static final class FixedSpliterator implements Spliterator<Integer> {
        private final int reported;
        private final int actual;
        private int produced;

        FixedSpliterator(int reported, int actual) {
            this.reported = reported;
            this.actual = actual;
        }

        public boolean tryAdvance(Consumer<? super Integer> action) {
            if (produced >= actual) {
                return false;
            }
            action.accept(produced);
            produced++;
            return true;
        }

        public Spliterator<Integer> trySplit() {
            return null;
        }

        public long estimateSize() {
            return reported;
        }

        public int characteristics() {
            return SIZED | SUBSIZED | ORDERED;
        }
    }

    public static void main(String[] args) {
        Integer[] ok = StreamSupport.stream(new FixedSpliterator(3, 3), true)
                .toArray(Integer[]::new);
        if (!Arrays.equals(ok, new Integer[] {0, 1, 2})) {
            throw new RuntimeException("unexpected result: " + Arrays.toString(ok));
        }

        boolean thrown = false;
        try {
            StreamSupport.stream(new FixedSpliterator(2, 3), true)
                    .toArray(Integer[]::new);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IndexOutOfBoundsException expected when SIZED stream overproduces");
        }
    }
}
