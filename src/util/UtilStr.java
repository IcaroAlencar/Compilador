package util;

import java.util.Stack;

/**
 *
 * @author icaroalencar
 */
public class UtilStr {
    
    public String PadRight(String texto, int tamanho, char separador){
        String textoAux    = "";
        int count          = 0;
        
        while(count < tamanho){
            if(count < texto.length()){
                textoAux += texto.charAt(count);
            }else{
                textoAux += separador;
            }
            count++;
        }
        return textoAux;
    }
    
    public String InFixToPosFix(String InFix){
        StringBuilder postfix  = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char ch : InFix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                postfix.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(" ");
                    postfix.append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() != '(') {
                    // Expressão inválida
                    return null;
                } else {
                    stack.pop(); // Remover '('
                }
            } else {
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    postfix.append(" ");
                    postfix.append(stack.pop());
                }
                postfix.append(" ");
                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                // Expressão inválida
                return null;
            }
            postfix.append(" ");
            postfix.append(stack.pop());
        }

        return postfix.toString();

    }
    
    private static int precedence(char ch) {
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
    
}
