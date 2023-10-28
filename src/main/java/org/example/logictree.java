package org.example;

import java.util.Scanner;
import java.util.Stack;

class ExpressionTree2 {
    class TreeNode {
        char data;
        TreeNode left, right;

        public TreeNode(char data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private static Stack<TreeNode> stack;

    public ExpressionTree2() {
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
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '&' || ch == '|' || ch == '!' || ch == '^';
    }

    private int precedence(char ch) {
        if (ch == '+' || ch == '-') {
            return 1;
        } else if (ch == '*' || ch == '/' || ch == '%') {
            return 2;
        } else if (ch == '&' || ch == '|') {
            return 3;
        } else if (ch == '^') {
            return 4;
        } else if (ch == '!') {
            return 5;
        }
        return -1;
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
            } else if (isOperator(ch)) { // Operator encountered
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

    public boolean evaluate() {
        return evaluateLogical(stack.peek());
    }

    private boolean evaluateLogical(TreeNode ptr) {
        boolean left, right;
        if (ptr.left == null && ptr.right == null) {
            return ptr.data == '1';
        } else {
            left = evaluateLogical(ptr.left);
            right = evaluateLogical(ptr.right);
            char operator = ptr.data;

            switch (operator) {
                case '&':
                    return left && right;
                case '|':
                    return left || right;
                case '^':
                    return left ^ right;
                case '!':
                    return !right;
                default:
                    return false;
            }
        }
    }
}

public class logictree {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Expression Tree Test");

        ExpressionTree2 et = new ExpressionTree2();

        System.out.println("\nEnter equation in infix form");
        et.buildTreeFromInfix(scan.next());

        boolean result = et.evaluate();
        System.out.println("\nEvaluated Result : " + result);
    }
}