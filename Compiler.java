/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mycompiler;
import java.util.Scanner;

public class Compiler {

    public static void main(String[] args) {
        System.out.print("Do you want writ to Lexer analyzer or Syntax analyzer\n ");
        String Answerr;
        Scanner scann = new Scanner(System.in);
        Answerr = scann.nextLine();
        SyntaxAnalyzer compiler = new SyntaxAnalyzer();
        if (Answerr.equalsIgnoreCase("Lexer analyzer")) {
            Lexer.initializeLists();
            Lexer.scanFile();
        }

        else {
            compiler.opinionsListMethod();

        }
    }
}



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mycompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Lexer {

    static String identifierRegularExpression = "[a-zA-Z][a-zA-Z0-9]*";
    static String digit = "[0-9]+";
    static String comment = "/\\*.*?\\*/";
    static String output = "'[^']*'";

    static String token;
    static Scanner inputCode;
    static String filename = "C:\\Users\\deema\\VisualStudio\\myJavaProjects\\src\\MyCompiler\\review.txt";

    static List<String> keywords = new ArrayList<>();
    static List<String> symbols = new ArrayList<>();
    static List<String> operations = new ArrayList<>();
    static List<String> logicalOps = new ArrayList<>();

    public static void initializeLists() {
        keywords.add("if");
        keywords.add("else");
        keywords.add("elseif");
        keywords.add("for");
        keywords.add("while");
        keywords.add("do");
        keywords.add("return");
        keywords.add("break");
        keywords.add("continue");
        keywords.add("byte");
        keywords.add("class");
        keywords.add("default");
        keywords.add("extends");
        keywords.add("implements");
        keywords.add("instanceof");
        keywords.add("interface");
        keywords.add("native");
        keywords.add("short");
        keywords.add("strictfp");
        keywords.add("super");
        keywords.add("const");
        keywords.add("goto");
        keywords.add("switch");
        keywords.add("synchronized");
        keywords.add("throw");
        keywords.add("throws");
        keywords.add("volatile");
        keywords.add("transient");
        keywords.add("final");
        keywords.add("import");
        keywords.add("finally");
        keywords.add("public");
        keywords.add("private");
        keywords.add("protected");
        keywords.add("package");
        keywords.add("abstract");
        keywords.add("assert");
        keywords.add("case");
        keywords.add("new");
        keywords.add("int");
        keywords.add("double");
        keywords.add("float");
        keywords.add("static");
        keywords.add("char");
        keywords.add("boolean");
        keywords.add("long");
        keywords.add("void");
        keywords.add("this");
        keywords.add("try");
        keywords.add("catch");
        keywords.add("true");
        keywords.add("false");
        keywords.add("enum");

        symbols.add("!");
        symbols.add("&");
        symbols.add("^");
        symbols.add("%");
        symbols.add("$");
        symbols.add("#");
        symbols.add("@");
        symbols.add("?");
        symbols.add("~");
        symbols.add("("); // begin the condition
        symbols.add(")"); // end the condition
        symbols.add("{"); // begin the loop
        symbols.add("}"); // end for loop
        symbols.add(";"); // end statement
        symbols.add("'"); // use to print

        operations.add("+"); // addition
        operations.add("-"); // subtraction
        operations.add("*"); // multiplication
        operations.add("/"); // division
        operations.add("="); // assignment
        operations.add("%"); // mod

        logicalOps.add("||");
        logicalOps.add("&&");
        logicalOps.add("==");
        logicalOps.add("<=");
        logicalOps.add(">=");
        logicalOps.add(">");
        logicalOps.add("<");
    }

    public static void scanFile() {
        System.out.println("- - - - - - - - - - - - - - - - - - -");
        System.out.printf("| %-10s | %-10s | %-5s |%n", "Lexeme", "Token", "Index");
        System.out.println("- - - - - - - - - - - - - - - - - - -");

        try {
            File file = new File(filename);

            inputCode = new Scanner(file);
            StringBuilder content = new StringBuilder();
            while (inputCode.hasNextLine()) {
                content.append(inputCode.nextLine()).append("\n");
            }

            // Preprocessing the input to add spaces around operators and semicolons
            String processedInput = content.toString().replaceAll("([=;+-])", " $1 ");

            Scanner scanner = new Scanner(processedInput);
            int index = 1;
            while (scanner.hasNext()) {
                token = scanner.next();
                String tokenCategory;
                if (keywords.contains(token)) {
                    tokenCategory = "Keyword";
                } else if (symbols.contains(token)) {
                    tokenCategory = "Symbol";
                } else if (operations.contains(token)) {
                    tokenCategory = "Operation";
                } else if (logicalOps.contains(token)) {
                    tokenCategory = "LogicalOp";
                } else if (Pattern.matches(identifierRegularExpression, token)) {
                    tokenCategory = "Identifier";
                } else if (Pattern.matches(comment, token)) {
                    tokenCategory = "Comment";
                } else if (Pattern.matches(digit, token)) {
                    tokenCategory = "Digit";
                } else if (Pattern.matches(output, token)) {
                    tokenCategory = "Output";
                } else {
                    tokenCategory = "Error";
                }
                if (!tokenCategory.equals("Error")) {
                    System.out.printf("| %-10s | %-10s | %-5d |%n", token, tokenCategory, index++);
                } else {
                    System.out.printf("| %-10s | %-10s | %-5s |%n", token, "ERROR !!!", index);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + filename);
        }
        System.out.println("- - - - - - - - - - - - - - - - - - -");
    }

    public static void main(String[] args) {
        initializeLists();
        scanFile();
    }
}



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mycompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class SyntaxAnalyzer {

    private String line;
    private String ans;

    Scanner scanner = new Scanner(System.in);

    /*
     * its an interactive method that act with the user and asked for writing
     * sentence
     * if user write no it will closed the program, if user write yes it will make
     * the user to choose ethir if or for statement
     * then it will store the written statemnt in "review" file.  
     */

    public void opinionsListMethod() {
        System.out.println("Do you want to write your sentence(String)?");
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("YES")) {
            try {
                PrintWriter outputStream = new PrintWriter(new FileOutputStream("review.txt", true));
                System.out.println("*\n");
                Scanner keyboard = new Scanner(System.in);
                System.out.println("\n*\n"
                        + "Before you write your sentence(String), do you want to write If Statements or For Loop?");
                String ans = keyboard.nextLine();
                if (ans.equalsIgnoreCase("if")) {
                    System.out.println("\n*\n" + "Write your sentence(String) in one line:");
                    line = keyboard.nextLine();
                    outputStream.println(line);
                    outputStream.close();
                    displayFileIF();
                } else {
                    System.out.println("\n*\n" + "Write your sentence(String) in one line:");
                    line = keyboard.nextLine();
                    outputStream.println(line);
                    outputStream.close();
                    displayFileFOR();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                System.exit(0);
            }
        } else {
            System.out.println("We are sorry to hear that. :(");
        }
    }

    /*
     * this method will read the sentence from "review" file line by line
     * by using the while loop that helps to store the reading line in "line"
     * variable
     * then calling the IFString() method to check if there are any syntax errors. 
     */

    public void displayFileIF() {
        try {
            File file = new File("review.txt");
            Scanner scanner = new Scanner(file);

            System.out.println("\nsentence(String):\n");
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                System.out.println(line);
                IFString();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    /*
     * this method will read the sentence from "review" file line by line
     * by using the while loop that helps to store the reading line in "line"
     * variable
     * then calling the FORString() method to check if there are any syntax errors. 
     */

    public void displayFileFOR() {
        try {
            File file = new File("review.txt");
            Scanner scanner = new Scanner(file);

            System.out.println("\nsentence(String):\n");
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                System.out.println(line);
                FORString();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    /*
     * in this method we use a stack then we create an Iteration by using for each
     * loop
     * and if statment that check if the paranthisis open type then push it else it
     * will check again
     * by nested if, ethir stack empty or have another paranthisis but != open
     * paranthisis
     * it will pop it and return false 
     *     
     */

    private boolean areParenthesesBalanced(String line) {
        Stack<Character> stack = new Stack<>();
        for (char ch : line.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    /*
     * in this method we use a stack then we create an Iteration by using for each
     * loop
     * and if statment that check if the braces open type then push it else it will
     * check again
     * by nested if, ethir stack empty or have another braces but != open braces
     * it will pop it and return false 
     *     
     */

    private boolean areBracesBalanced(String line) {
        Stack<Character> stack = new Stack<>();
        for (char ch : line.toCharArray()) {
            if (ch == '{') {
                stack.push(ch);
            } else if (ch == '}') {
                if (stack.isEmpty() || stack.pop() != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    /*
     * this method will works as a basic syntax analysis.
     * we use if/else statement to check if statement doesn't contains if keyword
     * it will print error meassage and the same thing with the else keyword.
     * and it will check if the sentenc have balanced paranthisis and barces or not,
     * if not it will print an error message.
     * if the sentence was correct it will print a correction message
     *     
     */

    public void IFString() {
        if (!line.contains("if")) {
            System.out.println("Oops, you are missing the 'if' keyword. Please rewrite the statement correctly.");
        } else if (!line.contains("else")) {
            System.out.println("Oops, you are missing the 'else' keyword. Please rewrite the statement correctly.");
        } else if (!areParenthesesBalanced(line)) {
            System.out.println("Oops, your parentheses are not balanced. Please rewrite the statement correctly.");
        } else if (!areBracesBalanced(line)) {
            System.out.println("Oops, your braces are not balanced. Please rewrite the statement correctly.");
        } else {
            System.out.println("COOL! The syntax analysis was successful.");
        }
    }

    /*
     * this method will works as a basic syntax analysis.
     * we use if/else statement to check if statement doesn't contains for keyword
     * it will print error meassage and the same thing with the int keyword.
     * its also check for variable names that should be letter followed by letters
     * or digits.
     * checks if the statement contains semicolons or not, and checks if there is
     * ncrement or decrement or not
     * and it will check if the sentenc have balanced paranthisis and barces or not
     *     
     */

    public void FORString() {

        if (!line.contains("for")) {
            System.out.println("Oops, you are missing the 'for' keyword. Please rewrite the statement correctly.");
        } else if (!line.contains("int")) {
            System.out.println("Oops, you are missing the 'int' keyword. Please rewrite the statement correctly.");
        } else if (!line.matches(".*\\b[a-zA-Z][a-zA-Z0-9]*\\b.*")) {
            System.out.println("Oops, you are missing a variable. Please rewrite the statement correctly.");
        } else if (!line.contains(";")) {
            System.out.println("Oops, you are missing the semicolon ';'. Please rewrite the statement correctly.");
        } else if (!line.matches(".*[a-zA-Z0-9].*\\s*(\\+\\+|--).*")) {
            System.out.println(
                    "Oops, you are missing the expression that increments/decrements the loop variable by some value. Please rewrite the statement correctly.");
        } else if (!areParenthesesBalanced(line)) {
            System.out.println("Oops, your parentheses are not balanced. Please rewrite the statement correctly.");
        } else if (!areBracesBalanced(line)) {
            System.out.println("Oops, your braces are not balanced. Please rewrite the statement correctly.");
        } else {
            System.out.println("COOL! The syntax analysis was successful.");
        }

    }
}
