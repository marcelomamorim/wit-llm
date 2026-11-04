/*
 * @test
 * @summary Verifies that Console.format returns the same Console instance when a console is available
 * @run main ConsoleWitupTest
 */

public class ConsoleWitupTest {
    public static void main(String[] args) {
        Console c = System.console();
        if (c == null) {
            System.out.println("No console available; format behavior not exercised");
            return;
        }
        Console r1 = c.format("plain");
        if (r1 != c) {
            throw new RuntimeException("format(String) did not return this Console");
        }
        Console r2 = c.format("%s-%d", "a", 1);
        if (r2 != c) {
            throw new RuntimeException("format(String, Object...) did not return this Console");
        }
        Console r3 = c.format("%s", (Object) null);
        if (r3 != c) {
            throw new RuntimeException("format with null argument did not return this Console");
        }
    }
}
