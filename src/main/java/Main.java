import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import tests.*;

public class Main {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BlankTests.class, AccountTests.class, EmailTests.class, LoginTests.class, PasswordTests.class, TasksTests.class);

        System.out.println("Tests failed: " + result.getFailureCount() + " / " + result.getRunCount());
        System.out.println("Tests passed: " + (result.getRunCount() - result.getFailureCount()) + " / " + result.getRunCount());

        System.out.println();
        if (result.getFailures().size() > 0) {
            System.out.println("Tests failed:");
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        } else {
            System.out.println("All tests passed successfully!");
        }
    }
}
