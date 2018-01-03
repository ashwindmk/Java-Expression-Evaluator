import java.util.Stack;

public class ExpressionEvaluator {

    private static boolean hasPrevedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }

        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        } else {
            return true;
        }
    }

    private static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by 0");
                }
                return a / b;
        }
        return 0;
    }

    public static double evaluate(String expression) {
        // Get the tokens
        char[] tokens = expression.toCharArray();

        // Stack of numbers 'values'
        Stack<Double> values = new Stack<Double>();

        // Stack of operaors 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i=0; i<tokens.length; i++) {
            // Token is white space
            if (tokens[i] == ' ') {
                continue;
            }

            // Token is number
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuffer sb = new StringBuffer();
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.')) {
                    sb.append(tokens[i++]);
                }
                values.push( Double.parseDouble(sb.toString()) );
                i--;
            }

            // Token is '('
            else if (tokens[i] == '(') {
                ops.push(tokens[i]);
            }

            // Token is ')'
            else if (tokens[i] == ')') {
                while (!ops.empty() && ops.peek() != '(') {
                    values.push( applyOp(ops.pop(), values.pop(), values.pop()) );
                }
                ops.pop();
            }

            // Token is operator
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                while (!ops.empty() && hasPrevedence(tokens[i], ops.peek())) {
                    values.push( applyOp(ops.pop(), values.pop(), values.pop()) );
                }
                ops.push(tokens[i]);
            }
        }

        // Evaluate
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        // Return result
        return values.pop();
    }

    public static void main(String[] args) {
        try {
            System.out.println(evaluate("(4.2 + 1.8) * 8"));
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

}
