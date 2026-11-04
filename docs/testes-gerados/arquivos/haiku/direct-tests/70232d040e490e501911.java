/*
 * @test
 * @summary Verifies that Console.format returns the same Console instance when a console is available
 * @run main ConsoleWitupTest
 */

import java.io.Console;

public class ConsoleWitupTest {
    public static void main(String[] args) {
        Console c = System.console();
        if (c == null) {
            System.out.println("No console available; format() contract not exercised");
            return;
        }
        Console r = c.format("%s-%d%n", "a", 1);
        if (r != c) {
            throw new RuntimeException("format() must return this Console");
        }
        Console r2 = c.format("plain text%n");
        if (r2 != c) {
            throw new RuntimeException("format() without arguments must return this Console");
        }
    }
}
