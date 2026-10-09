/* @test
   @summary Verifies the behavior of Random.nextInt(int) for various bounds.
   @run main RandomWitupTest_Pf5cadf8bdd7c
 */
import java.util.Random;
public class RandomWitupTest_Pf5cadf8bdd7c {
    public static void main(String[] args) {
        Random rand = new Random(12345L);
        int result1 = rand.nextInt(10);
        int result2 = rand.nextInt(10);
        int result3 = rand.nextInt(1);
        int result4 = rand.nextInt(100);
        int result5 = rand.nextInt(100);
        int result6 = rand.nextInt(1000);
        int result7 = rand.nextInt(1000);
        int result8 = rand.nextInt(2);
        int result9 = rand.nextInt(2);
        int result10 = rand.nextInt(5);
        int result11 = rand.nextInt(5);
        int result12 = rand.nextInt(0); // should throw IllegalArgumentException
        System.out.println("Results:");
        System.out.println("rand.nextInt(10): " + result1);
        System.out.println("rand.nextInt(10): " + result2);
        System.out.println("rand.nextInt(1): " + result3);
        System.out.println("rand.nextInt(100): " + result4);
        System.out.println("rand.nextInt(100): " + result5);
        System.out.println("rand.nextInt(1000): " + result6);
        System.out.println("rand.nextInt(1000): " + result7);
        System.out.println("rand.nextInt(2): " + result8);
        System.out.println("rand.nextInt(2): " + result9);
        System.out.println("rand.nextInt(5): " + result10);
        System.out.println("rand.nextInt(5): " + result11);
        try {
            rand.nextInt(0);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            rand.nextInt(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
