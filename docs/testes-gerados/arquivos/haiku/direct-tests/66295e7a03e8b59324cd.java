/*
 * @test
 * @summary Validates argument checking of Object.wait(long, int) and accepts valid boundary values
 * @run main ObjectWitupTest
 */

public class ObjectWitupTest {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws Exception {
        synchronized (LOCK) {
            expectIllegalArgument(-1L, 0);
            expectIllegalArgument(Long.MIN_VALUE, 0);
            expectIllegalArgument(0L, -1);
            expectIllegalArgument(0L, 1000000);
            expectIllegalArgument(1L, Integer.MIN_VALUE);
            expectIllegalArgument(-5L, 1000000);

            LOCK.wait(1L, 0);
            LOCK.wait(1L, 999999);
            LOCK.wait(1L, 500000);
        }
    }

    private static void expectIllegalArgument(long timeoutMillis, int nanos) {
        try {
            LOCK.wait(timeoutMillis, nanos);
        } catch (IllegalArgumentException e) {
            return;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Expected IllegalArgumentException for timeoutMillis="
                + timeoutMillis + ", nanos=" + nanos);
    }
}
