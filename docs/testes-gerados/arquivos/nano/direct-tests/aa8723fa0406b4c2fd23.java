/* @test    @summary Test ForkJoinPool constructor with byte parameter for common pool only    @run main ForkJoinPoolWitupTest */

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) {
        testConstructorWithByte();
    }

    public static void testConstructorWithByte() {
        ForkJoinPool pool = new ForkJoinPool((byte) 1);
        assert pool != null : "Pool should be instantiated";
        // Additional observable checks could include thread count or state if accessible
        System.out.println("ForkJoinPool instantiated with byte parameter successfully.");
    }
}
