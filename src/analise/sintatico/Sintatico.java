package analise.sintatico;

import analise.lexico.Tokens;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author icaroalencar
 */
public class Sintatico {
    
    static Stack<String> ProducaoPilha  = new Stack();
    static ArrayList<Tokens> Tokens;
    static int Posicao                  = 0;
    static Log Logs                     = new Log();
    static ArrayList<String> ListLog    = new ArrayList();
    static HashMapSintatico MeuHash     = new HashMapSintatico();
    static boolean isError              = false;
    
    public static boolean Iniciar(ArrayList<Tokens> tk, Boolean isLog){
        ListLog.add("##### Log Sintatico #####");
        MeuHash.MeuHash();
        System.out.println("Iniciando Analise Sintatica...");
        ProducaoPilha.push(MeuHash.get$());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getBase());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        Tokens = new ArrayList<>(tk);
        
        while(!"$".equals(ProducaoPilha.peek())){
            getProducao(ProducaoPilha.peek(), Tokens.get(Posicao));
            Valida(ProducaoPilha.peek(), Tokens.get(Posicao));
            if (isError) {
                break;
            }
        }

        if(!isError){
            while(!ProducaoPilha.isEmpty()){
                System.out.println("Producao..: "+ProducaoPilha.pop());
            }

            for(int i = 0; i < Tokens.size(); i++){
                System.out.println("Tokens....: "+Tokens.get(i).getTokens());
            }
            
            System.out.println("Fim da Analise Sintatica.....");
        }
        
        ListLog.add("##### Log Sintatico #####");
        if(isLog){
            ListLog.forEach((t) -> {
                System.out.println(t);
            });
        }
        
        return isError;
    }
    
    private static void getProducao(String NaoTerminal, Tokens TokenAtual){
        
        // Não Terminais
        switch(NaoTerminal){
            case "BASE":
                BASE(TokenAtual.getTokens());
                break;
                
            case "ATRIBUTOS":  
                ATRIBUTOS(TokenAtual.getTokens());
                break;
                
            case "LISTA_ATRIB":
                LISTA_ATRIBUTOS(TokenAtual.getTokens());
                break;
            
            case "CODIGO":
                CODIGO(TokenAtual.getTokens());
                break;
                
            case "INSTRUCOES":
                INSTRUCOES(TokenAtual.getTokens());
                break;
                
            case "LISTA_INSTRUCOES":
                LISTA_INSTRUCOES(TokenAtual.getTokens());
                break;
                
            case "ATRIBUTO_SAIDA":
                ATRIBUTO_SAIDA(TokenAtual.getTokens());
                break;
                
            case "EXPRESSAO_MATEMATICA":
                EXPRESSAO_MATEMATICA(TokenAtual.getTokens());
                break;
                
            case "OPERACAO_MATEMATICA":
                OPERACAO_MATEMATICA(TokenAtual.getTokens());
                break;
                
            case "OPERANDO":
                OPERANDO(TokenAtual.getTokens());
                break;
                
            case "NCONDICAO":
                NCONDICAO(TokenAtual.getTokens());
                break;
              
            case "OPERADOR_LOGICO":
               OPERADOR_LOGICO(TokenAtual.getTokens());
               break;
               
            case "OPERADOR":
                OPERADOR(TokenAtual.getTokens());
                break;
                
            case "CONDICAO":
                CONDICAO(TokenAtual.getTokens());
                break;
        }
        
    }
    
    private static void BASE(String Token){
        switch(Token){
            case "INICIO":
                P0();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
        }
        
    }
    
    private static void ATRIBUTOS(String Token){
        switch(Token){
            case "TIPO_ATRIB":
                P1();
                break;
                
            case "NOME_ATRIB":
                PÎ();
                break;
                
            case "LEIA":
                PÎ();
                break;
            
            case "ESCREVA":
                PÎ();
                break;
            
            case "CONDICAO":
                PÎ();
                break;
                
            case "REPITA":
                PÎ();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
        }
    }
    
    private static void LISTA_ATRIBUTOS(String Token){        
        
        switch(Token){
            case "VIRGULA":
                P3();
                break;
                
            case "FIM_LINHA":
                PÎ();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
                
        }
    }
    
    private static void CODIGO(String Token){
        switch(Token){
            case "ESCREVA":
                P5();
                break;
                
            case "LEIA":
                P5();
                break;  
                
            case "NOME_ATRIB":
                P5();
                break; 
                
            case "REPITA":
                P5();
                break;
                
            case "CONDICAO":
                P5();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
        }
    }
    
    
    private static void INSTRUCOES(String Token){
        switch(Token){
            case "ESCREVA":
                P9();
                break;
                
            case "LEIA":
                P8();
                break;
                
            case "NOME_ATRIB":
                P13();
                break; 
                
            case "REPITA":
                P24();
                break;
                             
            case "CONDICAO":
                P14();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
        }
    }
    
    private static void LISTA_INSTRUCOES(String Token){
        switch(Token){
            case "LEIA":
                P6();
                break;
            
            case "ESCREVA":
                P6();
                break;
            
            case "NOME_ATRIB":
                P6();
                break;
                
            case "REPITA":
                P6();
                break;
                
            case "FECHA_CHAVES":
                PÎ();
                break;
                
            case "FIM":
                PÎ();
                break;
                
            case "CONDICAO":
                P6();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
        }
    }
    
    private static void ATRIBUTO_SAIDA(String Token){
        switch(Token){
            case "FRASE":
                P12();
                break;
                
            case "NUMERO":
                P11();
                break;
                
            case "NOME_ATRIB":
                P10();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
        }
    }
    
    private static void EXPRESSAO_MATEMATICA(String Token){
        switch(Token){
            case "NUMERO":
                P25();
                break;
            
            case "NOME_ATRIB": 
                P25();
                break;
                
            case "ABRE_PARENTESES": 
                P26();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
        }
    }
    
    private static void OPERACAO_MATEMATICA(String Token){
        switch(Token){
            case "FIM_LINHA":
                PÎ();
                break;
            
            case "DIFERENTE":
                PÎ();
                break;
                
             case "MAIOR":
                PÎ();
                break;
                
             case "MENOR":
                PÎ();
                break;
                
             case "IGUAL":
                PÎ();
                break;
                
             case "MAIOR_IGUAL":
                PÎ();
                break;
                
             case "MENOR_IGUAL":
                PÎ();
                break;
            
            case "FECHA_PARENTESES":
                PÎ();
                break;
                
            case "MULTIPLICACAO":
                P27();
                break;
                
            case "SUBTRACAO":
                P27();
                break;
                
            case "ADICAO":
                P27();
                break;
                
            case "DIVISAO":
                P27();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
        }
    }
    
    private static void OPERANDO(String Token){
        switch(Token){
            case "NUMERO":
                P30();
                break;
            
            case "NOME_ATRIB":
                P29();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
                
        }
    }
    
    private static void CONDICAO(String Token){
        switch(Token){
            case "ABRE_PARENTESES":
                P15();
                break;
            
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break; 
        }
    }
    
    private static void NCONDICAO(String Token){
        switch(Token){
            
            case "NAOCONDICAO":
                P22();
                break;
                
            case "NOME_ATRIB":
                PÎ();
                break;
            
            case "FECHA_CHAVES":
                PÎ();
                break; 
               
            case "CONDICAO":
                PÎ();
                break;     
                
            case "LEIA":
                PÎ();
                break; 
                
            case "ESCREVA":
                PÎ();
                break;
                
            case "REPITA":
                PÎ();
                break;
                
            case "FIM":
                PÎ();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
        }
    }
    
    private static void OPERADOR_LOGICO(String Token){
        switch(Token){
            case "MAIOR_IGUAL":
                P21();
                break;
            
            case "MENOR_IGUAL":
                P20();
                break;
            
            case "DIFERENTE":
                P19();
                break;
            
            case "MAIOR":
                P18();
                break;
             
            case "MENOR":
                P17();
                break;    
            
            case "IGUAL":
                P16();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
        }
    }
    
    private static void OPERADOR(String Token){
        switch(Token){
            case "DIVISAO":
                P34();
                break;
                
            case "MULTIPLICACAO":
                P33();
                break;
                
            case "SUBTRACAO":
                P32();
                break;
                
            case "ADICAO":
                P31();
                break;
                
            default:
                System.out.println("Erro Sintatico..: "+Tokens.get(Posicao).getTokens()+""
                        + " Inesperado, Linha.: "+Tokens.get(Posicao).getLinha()
                        + " Coluna.: "+Tokens.get(Posicao).getColuna());  
                isError = true;
                break;
        }
    }
    
    private static void Valida(String Top, Tokens TokenAtual){
        switch(Top){
            case "INICIO":
                if(TokenAtual.getTokens().equals(MeuHash.getInicio())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: "
                            + "Era Esperado INICIO (GO{)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
                
            case "TIPO_ATRIB":
                if(TokenAtual.getTokens().equals(MeuHash.getTipo_Atrib())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado TIPO_ATRIB (INT)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;}
                break;
            
            case "NOME_ATRIB":
                if(TokenAtual.getTokens().equals(MeuHash.getNome_Atrib())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado NOME_ATRIB (&nome)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;}
                break; 

            case "VIRGULA":
                if(TokenAtual.getTokens().equals(MeuHash.getVirgula())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado VIRGULA (,)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;}
                break;  
                
            case "FIM_LINHA":
                if(TokenAtual.getTokens().equals(MeuHash.getFim_Linha())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado FIM_LINHA (;)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
                
            case "ESCREVA":
                if(TokenAtual.getTokens().equals(MeuHash.getEscreva())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado ESCREVA (ESC)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;}
                break;
            
            case "ABRE_PARENTESES":
                if(TokenAtual.getTokens().equals(MeuHash.getAbre_Parenteses())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado ABRE_PARENTESES ('(')"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;  
            
            case "FECHA_PARENTESES":
                if(TokenAtual.getTokens().equals(MeuHash.getFecha_Parenteses())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado FECHA_PARENTESES (')')"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;  
                
            case "FRASE":
                if(TokenAtual.getTokens().equals(MeuHash.getFrase())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado FRASE (''texto'')"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break; 
                
            case "LEIA":
                if(TokenAtual.getTokens().equals(MeuHash.getLeia())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado LEIA (LE)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break; 
                
            case "ATRIBUICAO":
                if(TokenAtual.getTokens().equals(MeuHash.getAtribuicao())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado ATRIBUICAO (<==)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break; 
                
            case "REPITA":
                if(TokenAtual.getTokens().equals(MeuHash.getRepita())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado REPITA (BIS)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
                
            case "CONDICAO":
                if(TokenAtual.getTokens().equals(MeuHash.getCondicao())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado CONDICAO (SE)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
                
            case "NAOCONDICAO":
                if(TokenAtual.getTokens().equals(MeuHash.getNaoCondicao())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado NAOCONDICAO (~SE)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
          
            case "DIFERENTE":
               if(TokenAtual.getTokens().equals(MeuHash.getDiferente())){
                   MATCH();
               }else{
                   System.out.println("Erro Sintatico..: Era Esperado DIFERENTE (!=)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
               }
               break; 
               
            case "ABRE_CHAVES":
               if(TokenAtual.getTokens().equals(MeuHash.getAbreChaves())){
                   MATCH();
               }else{
                   System.out.println("Erro Sintatico..: Era Esperado ABRE_CHAVES ({)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
               }
               break; 
               
            case "FECHA_CHAVES":
               if(TokenAtual.getTokens().equals(MeuHash.getFechaChaves())){
                   MATCH();
               }else{
                   System.out.println("Erro Sintatico..: Era Esperado FECHA_CHAVES (})"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                   isError = true;
                   break;
               }
               break; 
                
            case "FIM":
                if(TokenAtual.getTokens().equals(MeuHash.getFim())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado FIM (}END)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
            
            case "MULTIPLICACAO":
                if(TokenAtual.getTokens().equals(MeuHash.getMultiplicacao())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado MULTIPLICACAO (*)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
                
            case "SUBTRACAO":
                if(TokenAtual.getTokens().equals(MeuHash.getSubtracao())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado SUBSTRACAO (-)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
                
            case "ADICAO":
                if(TokenAtual.getTokens().equals(MeuHash.getAdicao())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado ADICAO (+)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
                
            case "DIVISAO":
                if(TokenAtual.getTokens().equals(MeuHash.getDivisao())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado DIVISAO (/)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
                
            case "MAIOR":
                if(TokenAtual.getTokens().equals(MeuHash.getMaior())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado MAIOR (>)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break; 
                
            case "MENOR":
                if(TokenAtual.getTokens().equals(MeuHash.getMenor())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado MENOR (<)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break; 
                
            case "MENOR_IGUAL":
                if(TokenAtual.getTokens().equals(MeuHash.getMenor_Igual())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado MENOR_IGUAL (<=)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break; 
            
            case "NUMERO":
                if(TokenAtual.getTokens().equals(MeuHash.getNumero())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: Era Esperado NUMERO (123)"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
                
            case "VAZIO":
                GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "");
                ProducaoPilha.pop();
                break;
                
            case "IGUAL":
                if(TokenAtual.getTokens().equals(MeuHash.getIgual())){
                    MATCH();
                }else{
                    System.out.println("Erro Sintatico..: "
                            + "Era Esperado IGUAL ( == )"
                            + " Linha..: "+Tokens.get(Posicao).getLinha()
                            + " Coluna.:"+Tokens.get(Posicao).getColuna());  
                    isError = true;
                    break;
                }
                break;
        }
    }
    
    private static void MATCH(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "");
        ListLog.add(Logs.toStringDeuMatch());
        ProducaoPilha.pop();
        Tokens.remove(Posicao);
    }
    
    
    
    
    // Produções
    private static void P0(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P0");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getFim());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getCodigo());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getAtributos());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getInicio());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P1(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P1");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getFim_Linha());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getLista_Atrib());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getNome_Atrib());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getTipo_Atrib());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P3(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P3");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getLista_Atrib());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getNome_Atrib());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getVirgula());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P5(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P5");
        ProducaoPilha.pop();
       
        ProducaoPilha.push(MeuHash.getLista_Instrucoes());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getInstrucoes());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P6(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P6");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getCodigo());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P8(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P8");
       ProducaoPilha.pop();
       
       ProducaoPilha.push(MeuHash.getFim_Linha());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
       ProducaoPilha.push(MeuHash.getFecha_Parenteses());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
       ProducaoPilha.push(MeuHash.getNome_Atrib());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
       ProducaoPilha.push(MeuHash.getAbre_Parenteses());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
       ProducaoPilha.push(MeuHash.getLeia());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P9(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P9");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getFim_Linha());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getFecha_Parenteses());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getAtributo_Saida());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getAbre_Parenteses());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getEscreva());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P10(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P10");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getNome_Atrib());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P11(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P11");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getNumero());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P12(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P12");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getFrase());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P13(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P13");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getFim_Linha());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getExpressao_Matematica());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getAtribuicao());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getNome_Atrib());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P14(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P14");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getNCondicao());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getFim_Linha());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getFechaChaves());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getCodigo());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getAbreChaves());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getCondicao());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getCondicao());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    } 
    
    private static void P15(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P15");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getFecha_Parenteses());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getExpressao_Matematica());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getOperador_Logico());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getExpressao_Matematica());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getAbre_Parenteses());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P16(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P16");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getIgual());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
    }
    
    private static void P17(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P17");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getMenor());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
    }
    
    private static void P18(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P18");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getMaior());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
    }
    
    private static void P19(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P19");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getDiferente());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P20(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P20");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getMenor_Igual());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
    }
    
    private static void P21(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P21");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getMaior_Igual());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
    }
    
    private static void P22(){
//        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P22");
//        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getFim_Linha());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getFechaChaves());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getCodigo());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getAbreChaves());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getNaoCondicao());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
    }
    
    private static void P24(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P24");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getFim_Linha());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getFechaChaves());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getCodigo());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getAbreChaves());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getCondicao());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getRepita());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P25(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P25");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getOperacao_Matematica());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getOperando());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P26(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P26");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getOperacao_Matematica());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getFecha_Parenteses());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getExpressao_Matematica());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getAbre_Parenteses());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P27(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P27");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getExpressao_Matematica());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
        
        ProducaoPilha.push(MeuHash.getOperador());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P29(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P29");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getNome_Atrib());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P30(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P30");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getNumero());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P31(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P31");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getAdicao());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P32(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P32");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getSubtracao());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void P33(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P33");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getMultiplicacao());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    private static void P34(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "P34");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getDivisao());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void PÎ(){
        GravaLog(MeuHash.getDesempilhar(), ProducaoPilha.peek(), "PÎ");
        ProducaoPilha.pop();
        
        ProducaoPilha.push(MeuHash.getVazio());
        GravaLog(MeuHash.getEmpilha(), ProducaoPilha.peek(), "");
    }
    
    private static void GravaLog(String Operacao, String NaoTerminal, String Producao){
        
        Logs.setOperacao(Operacao);
        Logs.setNaoTerminal(NaoTerminal);
        Logs.setProducao(Producao);
        
        if(Operacao.equals(MeuHash.getEmpilha())){
            if(Producao.isEmpty()){
                ListLog.add(Logs.toStringEOpNt());
            }else{
                ListLog.add(Logs.toStringEOpNtPr());
            }
        }else{
            if(Producao.isEmpty()){
                ListLog.add(Logs.toStringDOpNt());
            }else{
                ListLog.add(Logs.toStringDOpNtPr());
            }
        }
        
    }
}

