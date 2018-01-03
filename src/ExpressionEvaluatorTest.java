public class ExpressionEvaluatorTest {

    public static void main(String[] args) {

        String expression = "(4 + 2) * 8";

        double result = ExpressionEvaluator.evaluate(expression);

        System.out.println(expression + " = " + result);
    }

}
