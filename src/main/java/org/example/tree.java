package org.example;

/**
 *importa la clase Stack para crear el arbol
 */
import java.util.Stack;

class ExpressionTree {
    static class TreeNode {
        char data;
        TreeNode left, right;

        public TreeNode(char data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private static Stack<TreeNode> stack;

    public ExpressionTree() {
        stack = new Stack<>();
    }
    /**
     * empuja los nodos en el arbol
     */
    private void push(TreeNode ptr) {
        stack.push(ptr);
    }
    /**
     * quita los nodos del arbol
     */
    private TreeNode pop() {
        return stack.pop();
    }
    /**
     * checkea si lo que ingresa el cliente es un digito
     */
    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
    /**
     * checkea si lo que ingresa el cliente es un operador
     */
    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^';
    }
    /**
     * construye el arbol usando la notacion postfija
     */
    public void buildTreeFromInfix(String infix) {
        String postfix = infixToPostfix(infix);
        buildTree(postfix);
    }
    /**
     * pasa la operacion ingresada por el usuario en infijo a postfijo
     */
    private String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        for (char ch : infix.toCharArray()) {
            if (isDigit(ch)) {
                postfix.append(ch);
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfix.append(operators.pop());
                }
                operators.pop(); // Remove '(' from the stack
            } else { // Operator encountered
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    postfix.append(operators.pop());
                }
                operators.push(ch);
            }
        }

        while (!operators.isEmpty()) {
            postfix.append(operators.pop());
        }

        return postfix.toString();
    }
    /**
     * define orden de operaciones
     */
    private int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }
    /**
     * construye el arbol usando la notacion postfija
     */
    public void buildTree(String postfix) {
        for (char ch : postfix.toCharArray()) {
            if (isDigit(ch)) {
                TreeNode node = new TreeNode(ch);
                push(node);
            } else if (isOperator(ch)) {
                TreeNode node = new TreeNode(ch);
                node.right = pop();
                node.left = pop();
                push(node);
            }
        }
    }
    /**
     * convierte el problema en postfijo para imprimirlo
     */
    public void postfix() {
        postOrder(stack.peek());
    }

    private void postOrder(TreeNode ptr) {
        if (ptr != null) {
            postOrder(ptr.left);
            postOrder(ptr.right);
            System.out.print(ptr.data);
        }
    }

    public void infix() {
        inOrder(stack.peek());
    }

    private void inOrder(TreeNode ptr) {
        if (ptr != null) {
            inOrder(ptr.left);
            System.out.print(ptr.data);
            inOrder(ptr.right);
        }
    }
    /**
     * El problema insertado es impreso como prefijo
     */
    public void prefix() {
        preOrder(stack.peek());
    }

    private void preOrder(TreeNode ptr) {
        if (ptr != null) {
            System.out.print(ptr.data);
            preOrder(ptr.left);
            preOrder(ptr.right);
        }
    }
    /**
     * retorna la operacion usando evaluateAlgebraic
     */
    public double evaluateAlgebraic() {
        return evaluateAlgebraic(stack.peek());
    }
    /**
     * Hace las operaciones correspondientes con al arbol Algebraico
     */
    private double evaluateAlgebraic(TreeNode ptr) {
        if (ptr.left == null && ptr.right == null)
            return toDigit(ptr.data);
        else {
            double result = 0.0;
            double left = evaluateAlgebraic(ptr.left);
            double right = evaluateAlgebraic(ptr.right);
            char operator = ptr.data;

            switch (operator) {
                case '+':
                    result = left + right;
                    break;
                case '-':
                    result = left - right;
                    break;
                case '*':
                    result = left * right;
                    break;
                case '/':
                    result = left / right;
                    break;
                case '%':
                    result = left % right;
                    break;
                case '^':
                    result = Math.pow(left, right);
                    break;
                default:
                    result = left + right;
                    break;
            }
            return result;
        }
    }

    private int toDigit(char ch) {
        return ch - '0';
    }
}
/**
 * Crea una instancia del arbol a base de la expresion recibida
 */
public class tree {
    public static void main(String[] args) {
        System.out.println("Expression Tree Test");
        ExpressionTree et = new ExpressionTree();
        et.buildTreeFromInfix("5*3/8+(95%5-10)");
        System.out.print("\nPrefix  : ");
        et.prefix();
        System.out.print("\n\nInfix   : ");
        et.infix();
        System.out.print("\n\nPostfix : ");
        et.postfix();
        System.out.println("\n\nEvaluated Result : " + et.evaluateAlgebraic());
    }
}
