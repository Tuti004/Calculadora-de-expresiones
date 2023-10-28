package main.java.org.example;

import java.util.Scanner;
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

    private void push(TreeNode ptr) {
        stack.push(ptr);
    }

    private TreeNode pop() {
        return stack.pop();
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^';
    }

    public void buildTreeFromInfix(String infix) {
        String postfix = infixToPostfix(infix);
        buildTree(postfix);
    }

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

    public double evaluate() {
        return evaluate(stack.peek());
    }

    private double evaluate(TreeNode ptr) {
        if (ptr.left == null && ptr.right == null)
            return toDigit(ptr.data);
        else {
            double result = 0.0;
            double left = evaluate(ptr.left);
            double right = evaluate(ptr.right);
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
        System.out.println("\n\nEvaluated Result : " + et.evaluate());
    }
}
