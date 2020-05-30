// Alan Li
// 05/28/2020
// CSE143 BR
// TA: Anthony Tran
// Assignment 20questions
//
// QuestionGame will manage the game of 20 questions

import java.io.PrintStream;
import java.util.Scanner;

public class QuestionsGame {

    private Scanner console;

    private QuestionNode root;

    /**
     * Construct a new question tree starting with the object "computer"
     */
    public QuestionsGame(){
        console = new Scanner(System.in);
        root = new QuestionNode("Computer");
    }

    /**
     * write the current tree to output
     * @param output the file that stores the current tree
     */
    public void write(PrintStream output){
        writeHelper(root, output);
    }

    /**
     * write the current tree to a file
     * @param node the current node that need to be stored in the file
     * @param output the file that stores the current tree
     */
    private void writeHelper(QuestionNode node, PrintStream output){
        if (isAnswerNode(node)){
            output.println("A:");
            output.println(node.data);
        } else{
            output.println("Q:");
            output.println(node.data);
            writeHelper(node.left, output);
            writeHelper(node.right, output);
        }
    }

    /**
     * read tree from another file to replace the current one. Assume the file is valid
     * @param input contain the tree information
     */
    public void read(Scanner input){
        root = generateTree(input);
    }

    /**
     * generate new node using information from input
     * @param input contains the tree information
     * @return the node contains information from input
     */
    private QuestionNode generateTree(Scanner input){
        if (input.nextLine().equals("A:")){
            return new QuestionNode(input.nextLine());
        }
        return new QuestionNode(input.nextLine(), generateTree(input), generateTree(input));
    }

    /**
     * play the game with the player, ask player a series of yes or no question until it guess the
     * right object or fails. Expand the tree if it failed to guess the object and add the object
     * to the tree. If guess correctly it will print out message saying so.
     */
    public void askQuestions(){
        root = askQuestions(root);
    }

    /**
     * play the game with the player, ask player a series of yes or no question until it guess the
     * right object or fails. Expand the tree if it failed to guess the object and add the object
     * to the tree. If guess correctly it will print out message saying so.
     * @param node store the game information
     * @return the tree that contains the new object
     */
    private QuestionNode askQuestions(QuestionNode node){
        if (isAnswerNode(node)){
            if (yesTo("Would your object happen to be " + node.data + "?")){
                System.out.println("Great, I got it right!");
            } else{
                System.out.println("What is the name of your object?");
                QuestionNode answerNode = new QuestionNode(console.nextLine());
                System.out.println("Please give me a yes/no question that");
                System.out.println("distinguishes between your object");
                System.out.print("and mine--> ");
                String response = console.nextLine();
                if (yesTo("And what is your answer for your object?")){
                    node = new QuestionNode(response, answerNode, node);
                } else{
                    node = new QuestionNode(response, node, answerNode);
                }
            }
        } else{
            if (yesTo(node.data)){
                node.left = askQuestions(node.left);
            }else {
                node.right = askQuestions(node.right);
            }
        }
        return node;
    }

    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }

    private static class QuestionNode {
        public String data;

        public QuestionNode left;

        public QuestionNode right;

        /**
         * construct a new node with data provided
         * @param data the name of the object
         */
        public QuestionNode (String data){
            this(data, null, null);
        }

        /**
         * construct a new node with data provided
         * @param data the name of the object
         * @param left the left node of the node
         * @param right the right node of the node
         */
        public QuestionNode (String data, QuestionNode left, QuestionNode right){
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * check if the node is an answer node
     * @param node the node that needs to be checked
     * @return true if the node is a answer node, false if not
     */
    private boolean isAnswerNode(QuestionNode node){
        return node.left == null && node.right == null;
    }
}
