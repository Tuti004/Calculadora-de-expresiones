import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Stack;

// Server class
class Server {
    public static void main(String[] args)
    {
        ServerSocket server = null;

        try {

            // server is listening on port 5000
            server = new ServerSocket(5000);
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {

                // socket object to receive incoming client
                // requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected"
                        + client.getInetAddress()
                        .getHostAddress());

                // create a new thread object
                ClientHandler clientSock
                        = new ClientHandler(client);

                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        // Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }

        public void run()
        {
            PrintWriter out = null;
            BufferedReader in = null;
            try {

                // get the outputstream of client
                out = new PrintWriter(
                        clientSocket.getOutputStream(), true);

                // get the inputstream of client
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {

                    // writing the received message from
                    // client
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
                            return ch == '+' || ch == '-' || ch == '*' || ch == '/';
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
                                    return 2;
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
                    System.out.println("Expression Tree Test");

                    ExpressionTree et = new ExpressionTree();

                    System.out.println("\nEnter equation in infix form");
                    et.buildTreeFromInfix(line);

                    System.out.print("\nPrefix  : ");
                    et.prefix();
                    System.out.print("\n\nInfix   : ");
                    et.infix();
                    System.out.print("\n\nPostfix : ");
                    et.postfix();
                    System.out.println("\n\nEvaluated Result : " + et.evaluate());



                    System.out.printf(
                            " Sent from the client: %s\n",
                            line);
                    out.println(line);

                //asd
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
