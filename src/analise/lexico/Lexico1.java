/**
 * Objetivo: Essa classe tem como objetivo identificar (as palavas chaves) Tokens e Lexemas
 * Versão: 1.0
 * Autor: Icaro Alencar
 */
package analise.lexico;

import static analise.lexico.Lexico.variavel;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import util.UtilStr;


public class Lexico1 {
    
    //static String caminhoArq;            //= "/Users/icaroalencar/Downloads/Compilador/src/gramatica/gramatica.ica";           // caminho do arquivo
    static BufferedReader linha         = null;
    static int nLinhas                  = 0;
    static int nColunas                 = 1;
    static String verifica              = null;
    static char fita[]                  = null;
    static char fitaAux[]               = null;
    static String variavel              = "";
    static String numero                = "";
    static String frase                 = "";
    static ArrayList<String> tkList     = new ArrayList();
    static ArrayList<String> ErrList    = new ArrayList();
    static Tokens tk                    = new Tokens();
    static boolean isTerminouFrase      = false;
    
    static ArrayList<Tokens> TokenList     = new ArrayList();
    
    static HashMap<String, Tokens> hashMapTk = new HashMap<>();

    
    // Função Principal
    public static ArrayList Iniciar(String caminhoArq, Boolean isListatokens) {
        System.out.println("Iniciando Analise Lexica...");
        
        LerArq(caminhoArq);
        LerLinha();
        LerToken();

        if(isListatokens){
        
            if(!tkList.isEmpty() && ErrList.isEmpty()){
                UtilStr str = new UtilSrt();
                String cabToken     = str.PadRight("Token", 20, ' ');
                String cabLexema    = str.PadRight("Lexema",20, ' ');
                String cabLinha     = str.PadRight("Linha", 5, ' ');
                String cabColuna    = str.PadRight("Coluna",6, ' ');
                System.out.println(cabToken+" | " + cabLexema + " | " + cabLinha + " | " + cabColuna + " | ");
                imprimirToken();
            }
            if(!ErrList.isEmpty()){
                System.out.println("Foram encontrados "+ErrList.size()+" Erros...");
                imprimirErros();
                TokenList.clear();
            }else{
                System.out.println("Sucess..: Não houve erros");
            }
        }

        return TokenList;    
    } 
    
    // Abrir o arquivo
    private static void LerArq(String caminho){
        FileInputStream arq;
        try {
            arq = new FileInputStream(caminho);                                 // Arquivo
            InputStreamReader gramatica = new InputStreamReader(arq);               // Gramatica
            linha = new BufferedReader(gramatica);
        } catch (FileNotFoundException ex) {
            System.out.println("Erro..: Não foi possivel encontrar o arquivo");
        }
    }
    
    // Ler Linha
    private static void LerLinha(){
        try {
            
            //Lê a linha 
            verifica = linha.readLine(); 
            
            // Verifica se tem conteudo na linha
            if(verifica != null){ 
                
                // Ajustando colunas, quando tiver tabs na linha adiciona 4 espaços.
                verifica = verifica.replace("\t", "    ");
                
                // Passa a linha para dentro de um vetor
                fita = verifica.toCharArray();
                   
                nLinhas += 1;
                nColunas = 1;
            }else{
                try {
                        linha.close();
                    } catch (IOException ex){
                        System.out.println("Erro..: Arquivo Vazio!");
                    }
            }
        } catch (IOException ex) {
            System.out.println("Erro..: Não foi possivel ler o conteudo do arquivo!");
        }
    }
    
    // Identificar os Tokens
    private static void LerToken(){
        
        //Estado inicial.
        int state = 0;
        int stateOld;
        
        // Ficará em Loop Até o final do arquivo
        while(verifica != null){
            stateOld = state;
            
            /*  
                Verifica se chegou ao final da linha
                Exemplo..: GO{ => [ 'G' , 'O' , '{' ] 
                Se Coluna = Tamanho da fita chegou ao final da linha.
            */
            if((nColunas-1) < fita.length){
                state = proxState(state, fita[nColunas-1]);
                nColunas += 1;
            }else{
                // Se a linha atual acabar carrega a proxima linha.
                LerLinha();
            }
        }
        ConstroiToken("$", "$", nLinhas, nColunas);
    }
    
    // 
    private static int proxState(int state, char c){
        
        switch(state){
            
            case 0: 
                return S0(c);
            
            case 1:
                return S1(c);

            case 2:
                return S2(c); 
                
            case 3:
                return S3(c);
                
            case 4:
                return S4(c);
                
            case 5:
                return S5(c);
                
            case 6:
                return S6(c);
            
            case 7:
                return S7(c);
             
            case 8:
                return S8(c);    
             
            case 9:
                return S9(c);  
                
            case 10:
                return S10(c);
            
            case 11:
                return S11(c);
             
            case 12:
                return S12(c); 
                
            case 13:
                return S13(c);
                
            case 14:
                return S14(c);
                
            case 15:
                return S15(c);
                
            case 16:
                return S16(c);
            
            case 17:
                return S17(c);
             
            case 18:
                return S18(c);    
             
            case 19:
                return S19(c);  
               
            case 20:
                return S20(c);
                
            case 21:
                return S21(c);
                
            case 22:
                return S22(c);
                
            case 23:
                return S23(c);
                
            case 24:
                return S24(c);
        }
        
        // ToDo:
        return -1;
    }

    private static int S0(char c){
        switch(c){

            //'GO{'
            case 'G':
                return 1;
            
            // INT    
            case 'I': 
                return 3;
                
            // LE    
            case 'L':
                return 6;
            
            // SE
            case 'S':
                return 8;
            
            //~SE
            case '~':
                return 11;
                
            // BIS
            case 'B':
                return 9;
            
            // ESC
            case 'E':
                return 13;   
                
            // Adição
            case '+':
                ConstroiToken("+", "ADICAO", nLinhas, nColunas);
                return 0;
                
            // Subtração
            case '-':
                ConstroiToken("-", "SUBTRACAO", nLinhas, nColunas);
                return 0;
             
            // Multiplicação
            case '*':
                ConstroiToken("*", "MULTIPLICACAO", nLinhas, nColunas);
                return 0;
                
            //Divisão
            case '/':
                ConstroiToken("/", "DIVISAO", nLinhas, nColunas);
                return 0;
                
            //Abre Chaves
            case '{':
                ConstroiToken("{", "ABRE_CHAVES", nLinhas, nColunas);
                return 0;  
                
             // Fecha Chaves / Fim Função / Fim
            case '}':
                if(fita[nColunas] == 'E'){
                   return 13; 
                }else if(fita[nColunas] == ';'){
                    return 19;
                }else{
                    ConstroiToken("}", "FECHA_CHAVES", nLinhas, nColunas);
                    return 0;  
                }
             
            // Abre Parenteses
            case '(':
                ConstroiToken("(", "ABRE_PARENTESES", nLinhas, nColunas);
                return 0;
                
            // Fecha Parenteses
            case ')':
                ConstroiToken(")", "FECHA_PARENTESES", nLinhas, nColunas);
                return 0;
            
            //Fim Linha    
            case ';':
                ConstroiToken(";", "FIM_LINHA", nLinhas, nColunas);
                return 0;
                
            // Menor / Atribuição
            case '<':
                return 17;
                
            // Variavel    
            case '&':
                if((int)c >= 48 && (int)c <= 57){
                    variavel += c;
                    return 0;
                }
                return 20;
                
            // Espaço    
            case ' ':
                return 0;   
                       
            // Fim da Linha
            case '\n':   
                return 0;
              
            // Enter
            case '\r':   
                return 0;
              
            // Frase
            case '"':
                if(!frase.isEmpty()){
                    isTerminouFrase = true;
                }
                return S21(c);
                
            // Diferente    
            case '!':    
                return 23;
                
             // Maior    
            case '>':    
                return 24;    

            default:
                if((int)c >= 48 && (int)c <= 57){
                    numero += c;
                    return 22;
                }else{
                    ErrList.add("Erro Lexico.: '"+c+"' Linha.: "+nLinhas+" Coluna.: "+nColunas);
                }
        }
        
        // ToDo
        return 0;
    }
    
    
    private static int S1(char c){

         switch(c){
           case 'O':
               return 2;

           default:
               ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
       }

        // ToDo:
        return 0;
    }
     
    private static int S2(char c){
         
        switch(c){
            //Recolheceu o Token Iniciar Lexema GO{
            case '{':
                ConstroiToken("GO{", "INICIO", nLinhas, nColunas);
               // hashMapTk.put("INICIO", tk);
                return 0;
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }
         
         // ToDo:
        return 0;
    }

     
    private static int S3(char c){
        switch(c){
            case 'N':
                return 4;
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }
        
        //ToDo:
        return 0;

    }
    
    
    private static int S4(char c){
        switch(c){
            case 'T':
                return 5;
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }
        
        //ToDo:
        return 0;
    }
    
    private static int S5(char c){
        switch(c){
            
            // Reconheceu o Token Inteiro Lexema INT
            case ' ':
                ConstroiToken("INT", "TIPO_ATRIB", nLinhas, nColunas);
                return 0;
            
            case ';':
                ConstroiToken("INT", "TIPO_ATRIB", nLinhas, nColunas);
                ConstroiToken(";", "FIM_LINHA", nLinhas, nColunas);
                return 0;
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }
        
        //ToDo:
        return 0;
    }
    
    
    private static int S6(char c){
        switch(c){
            case 'E':
                return 7;
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }
        
        //ToDo:
        return 0;
    }
    
    
    private static int S7(char c){
        switch(c){
            // Reconheceu o token Leia lexema LE
            case '(':
                ConstroiToken("LE", "LEIA", nLinhas, nColunas-1);
                ConstroiToken("(", "ABRE_PARENTESES", nLinhas, nColunas);
                return 0;
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }
        
        //ToDo:
        return 0;
    }
             
    
     private static int S8(char c){
        switch(c){ 
            
            case 'E':
                // Reconhece o Token de Condição SE
                if(fita[nColunas] == '('){
                    ConstroiToken("SE", "CONDICAO", nLinhas, nColunas);
                    return 0;
                }
                
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);

        }
        
        //ToDo:
        return 0;
    }

    private static int S9(char c){
       switch(c){
           case 'I':
              return 10;

           default:
               ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
       }


       // ToDo:
       return 0;
    }
                

    private static int S10(char c){
        switch(c){ 
            case 'S':
                // Reconheceu o Token de Repetição BIS
                if(fita[nColunas] == '('){
                    ConstroiToken("BIS", "REPITA", nLinhas, nColunas);
                    return 0;
                }
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }
        // ToDo:
        return 0;
    }
    
    
    private static int S11(char c){
        switch(c){
            case 'S':
                return 12;
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }
        // ToDo:
        return 0;
    }
    

    private static int S12(char c){
        
        switch(c){ 
            case 'E':
                
                // Reconheceu o Token de Ñ Condição ~SE
                if(fita[nColunas] == '{'){
                    ConstroiToken("~SE", "NAOCONDICAO", nLinhas, nColunas);
                    return 0;
                }
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }  
        
        // ToDo:
        return 0;
    }
    
               
    private static int S13(char c){
         switch(c){ 
            case 'S':
                return 14;

            case 'E':
                return 15;
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }
        
        //ToDo:
        return 0;
    }
                
    private static int S14(char c){
        switch(c){ 
            case 'C':
                
                // Reconheceu o Token de Escreva ESC
                if(fita[nColunas] == '('){
                    ConstroiToken("ESC", "ESCREVA", nLinhas, nColunas);
                    return 0;
                }
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }
        
        //ToDo:
        return 0;
    }
                
    
    private static int S15(char c){
        switch(c){ 
            case 'N':
                return 16;
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);
        }
        //ToDo:
        return 0;
    }
    
    
    private static int S16(char c){
        switch(c){
            
            //Reconheceu o Token Fim.
            case 'D':
                ConstroiToken("}END", "FIM", nLinhas, nColunas);
                return 0; 
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas); 
         }

        //ToDo:
        return 0;
    }
    
    private static int S17(char c){
        switch(c){
            case ' ':
                ConstroiToken("<", "MENOR", nLinhas, nColunas);
                return 0; 
                
            case '=':
                return 18;
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);    
        }

        //ToDo:
        return 0;
    }
    
    private static int S18(char c){
        switch(c){
            
            // Reconheceu Atribuição
            case '=':
                ConstroiToken("<==", "ATRIBUICAO", nLinhas, nColunas);
                return 0;
                   
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);    
        }
        
        //ToDo:
        return 0;
    }
     

    private static int S19(char c){
        switch(c){
            
            // Reconheceu o token FimFunção
            case ';':
                ConstroiToken("}", "FECHA_CHAVES", nLinhas, nColunas);
                ConstroiToken(";", "FIM_LINHA", nLinhas, nColunas);
                return 0;
                   
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);    
        }
        
        //ToDo:
        return 0;
    }
    
    private static int S20(char c){
        
        switch(c){
            case ' ':
                ConstroiToken(variavel, "NOME_ATRIB", nLinhas, nColunas);
                variavel = "";
                return 0;
                
            case ',':  
                ConstroiToken(variavel, "NOME_ATRIB", nLinhas, nColunas);
                variavel = "";
                ConstroiToken(",", "VIRGULA", nLinhas, nColunas);
                return 0;
                
            case ';': 
                ConstroiToken(variavel, "NOME_ATRIB", nLinhas, nColunas);
                variavel = "";
                ConstroiToken(";", "FIM_LINHA", nLinhas, nColunas);
                return 0;
                
            case ')': 
               ConstroiToken(variavel, "NOME_ATRIB", nLinhas, nColunas);
               variavel = "";
               ConstroiToken(")", "FECHA_PARENTESES", nLinhas, nColunas);
               return 0;
                
            default:
                variavel += c;
                return 20;
        }
    }
    
    private static int S21(char c){
        frase += c;
        if(isTerminouFrase){
            ConstroiToken(frase, "FRASE", nLinhas, nColunas);
            isTerminouFrase = false;
            frase = "";
            return 0;
        }else if(fita[nColunas] == '"'){
            return 0;
        }else{
            return 21;
        }
    }
    
    private static int S22(char c){
        
        switch(c){
            case ' ':
                ConstroiToken(numero, "NUMERO", nLinhas, nColunas);
                numero = "";
                return 0;
                
            case ';': 
                ConstroiToken(numero, "NUMERO", nLinhas, nColunas);
                numero = "";
                ConstroiToken(";", "FIM_LINHA", nLinhas, nColunas);
                return 0;
                
            case ')': 
               ConstroiToken(numero, "NUMERO", nLinhas, nColunas);
               numero = "";
               ConstroiToken(")", "FECHA_PARENTESES", nLinhas, nColunas);
               return 0;
        }
           
        return 0;
    }
    
    
    private static int S23(char c){
        switch(c){
            case '=':
                ConstroiToken("!=", "DIFERENTE", nLinhas, nColunas);
                return 0;
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);  
        }
        
        return 0;
    }
    
    
    private static int S24(char c){
        switch(c){
            case '=':
                ConstroiToken(">=", "MAIOR_IGUAL", nLinhas, nColunas);
                return 0;
            case ' ':
                ConstroiToken(">", "MAIOR", nLinhas, nColunas);
                return 0;    
                
            default:
                ErrList.add("Erro Lexico: "+c+" Linha "+nLinhas+" Coluna: "+nColunas);  
        }
        
        return 0;
    }
    
    private static void imprimirToken() {
        for(int i = 0; i < tkList.size(); i++){
            System.out.println(tkList.get(i));
        }
    }
    
    private static void imprimirErros() {
        for(int i = 0; i < ErrList.size(); i++){
            System.out.println(ErrList.get(i));
        }
    }
    
    private static void ConstroiToken(String Lexema, String Token, int Linha, int Coluna){
        tk = new Tokens();
        tk.setLexemas(Lexema);
        tk.setTokens(Token);
        tk.setLinha(Linha);
        tk.setColuna(Coluna);
        tkList.add(tk.toString());
        TokenList.add(tk);

    }
    
}