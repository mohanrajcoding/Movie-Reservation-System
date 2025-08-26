import java.util.function.Predicate;

public class TestPredicate {
    public static void main(String[] args) {
        Predicate<String> isEmpty = s -> s.isEmpty();
        System.out.println(isEmpty.test(""));
    }
}
