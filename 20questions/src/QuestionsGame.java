// This is a starter file for QuestionsGame.
//
// You should delete this comment and replace it with your class
// header comment.

import java.io.PrintStream;
import java.util.*;

public class QuestionsGame {

    private Scanner console;

    private QuestionNode root;

    public QuestionsGame(){
        console = new Scanner(System.in);
        root = new QuestionNode("Computer");
    }

    public void write(PrintStream output){
        writeHelper(root, output);
    }

    private void writeHelper(QuestionNode root, PrintStream output){
        if (isAnswerNode(root)){
            output.println("A:");
            output.println(root.data);
        } else{
            output.println("Q:");
            output.println(root.data);
            writeHelper(root.left, output);
            writeHelper(root.right, output);
        }
    }

    public void read(Scanner input){
        root = generateTree(input);
    }

    private QuestionNode generateTree(Scanner input){
        if (input.nextLine().equals("A:")){
            return new QuestionNode(input.nextLine());
        }
        return new QuestionNode(input.nextLine(), generateTree(input), generateTree(input));

    }

    public void askQuestions(){
        root = askQuestions(root);
    }

    private QuestionNode askQuestions(QuestionNode root){
        if (isAnswerNode(root)){
            if (yesTo("Would your object happen to be " + root.data + "?")){
                System.out.println("Great, I got it right!");
            } else{
                System.out.println("What is the name of your object?");
                QuestionNode answerNode = new QuestionNode(console.nextLine());
                System.out.println("Please give me a yes/no question that\n" +
                        "distinguishes between your object\n" + "and mine--> ");
                String response = console.nextLine();
                if (yesTo("And what is your answer for your object?")){
                    root = new QuestionNode(response, answerNode, root);
                } else{
                    root = new QuestionNode(response, root, answerNode);
                }
            }
        } else{
            if (yesTo(root.data)){
                root.left = askQuestions(root.left);
            }else {
                root.right = askQuestions(root.right);
            }
        }
        return root;
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

        public QuestionNode (String data){
            this(data, null, null);
        }

        public QuestionNode (String data, QuestionNode left, QuestionNode right){
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    private boolean isAnswerNode(QuestionNode node){
        return node.left == null && node.right == null;
    }
}
