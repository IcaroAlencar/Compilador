package gerador;

import analise.lexico.Tokens;
import java.util.ArrayList;
import java.util.Stack;
import util.UtilStr;

/**
 *
 * @author icaroalencar
 */
public class Intermediario {
    
    static ArrayList<String> CodInt     = new ArrayList();
    static ArrayList<String> CodIntLog  = new ArrayList();
    static ArrayList<String> ls         = new ArrayList();
     
    static String Comando  = "";
    static String Variavel = ""; 
    static String VarAtrib = ""; 
    static String Frase    = "";
    static int contSe      = 0;
    static int contSenao   = 0;
    static int contBis     = 0;
    
    static Stack pilha     = new Stack(); 
    
    static UtilStr posFixa = new UtilStr();
    
    public static ArrayList<String> Iniciar(ArrayList<Tokens> tk, ArrayList<String> ListaVars, boolean isLog) {
        CodIntLog.add("Inicio da geração do codigo intermediario");
        ls = ListaVars;
        pilha.clear();;
         
        // Conversão das produções da gramatica
        Conversao(tk);
        
        CodInt.forEach((t) -> {
            System.out.println(t);
        });
        
        if(isLog){
            CodIntLog.forEach((t) -> {
                System.out.println(t);
            });
        }    
        
        return CodInt;
    }

    // adicionando as variaveis declaradas
    private static void RegistrarVars(ArrayList<String> list) {
        
        list.forEach((variavel) -> {
            CodInt.add("_VAR "+variavel.replace("&", ""));
            CodIntLog.add("Declarando a Variavel.: "+variavel.replaceAll("&", ""));
        });
    }

    private static void Conversao(ArrayList<Tokens> tokensList) {
        int i = 0;
        
        while(i < tokensList.size()){
            Comando = "";
            
            // Inicio do programa (GO{)
            if(tokensList.get(i).getTokens().equals("INICIO")){
                CodInt.add("inicio");
            }
            
            if(tokensList.get(i).getLexemas().equals("INT")){
                RegistrarVars(ls);
                while(!tokensList.get(i).getLexemas().equals(";")){
                    i++;
                }
                i++;
            }
            
            // Fim do programa(}END)
            if(tokensList.get(i).getTokens().equals("FIM")){
                CodInt.add("fim");
            }
            
            // Variavel 
            if(tokensList.get(i).getTokens().equals("NOME_ATRIB")){
                String var = tokensList.get(i).getLexemas().replaceAll("&", ""); // variavel
                String ExprInFixa   = "";
                i++;
                if(tokensList.get(i).getTokens().equals("ATRIBUICAO")){
                    var += " = "; // variavel = 
                    i++;
                    while(!tokensList.get(i).getLexemas().equals(";")){
                        ExprInFixa += tokensList.get(i).getLexemas().replaceAll("&", "");
                        i++;
                    }
                    CodInt.add(var + posFixa.InFixToPosFix(ExprInFixa));
                }
                
            }
            
            // achou o fim de algum codigo (SE, SENAO, BIS)
            if(tokensList.get(i).getLexemas().equals("}")){
                
                
                switch(pilha.pop().toString()){
                    case "se":
                        CodInt.add("fim_se");
                        CodIntLog.add("Reconhecimento do comando do final da condificiona(SE)" );                 
                        break;
                        
                    case "repita":
                        CodInt.add("fim_repita");
                        CodIntLog.add("Reconhecimento do comando do final da repeticao(BIS)" );
                        break;
                }
               
            }
            
            // SENAO(~SE)
            if(tokensList.get(i).getLexemas().equals("~SE")){
                CodInt.add("senao");
                contSenao++;
            }
            
            // SE(SE)
            if(tokensList.get(i).getLexemas().equals("SE")){
                Comando = "se ";
                pilha.push("se");
                pilha.push("senao");
                contSe++;
                i = i+2;
                String expr1   = "";
                String expr2   = "";
                while(!tokensList.get(i).getLexemas().equals("{")){
                    if(tokensList.get(i).getLexemas().equals("==") 
                            || tokensList.get(i).getLexemas().equals("!=") 
                            || tokensList.get(i).getLexemas().equals(">=") 
                            || tokensList.get(i).getLexemas().equals("<=") 
                            || tokensList.get(i).getLexemas().equals("<")
                            || tokensList.get(i).getLexemas().equals(">")){
                        
                        expr1 += posFixa.InFixToPosFix(expr2) + " "+ tokensList.get(i).getLexemas();
                        expr2 = "";
                    }else{
                        expr2 += tokensList.get(i).getLexemas().replaceAll("&", "");
                    }
                    i++;
                }
 
                String[] aux = expr2.split("\\)");
                expr1 += " "+posFixa.InFixToPosFix(aux[0]);
                
                CodInt.add(Comando+expr1);
            }
            
            
            // Laço - Repita - Enquanto - For
            if(tokensList.get(i).getLexemas().equals("BIS")){
                Comando = "repita_ate ";
                contBis++;
                pilha.push("repita");
                //contSe++;
                i = i+2;
                String expr1   = "";
                String expr2   = "";
                while(!tokensList.get(i).getLexemas().equals("{")){
                    if(tokensList.get(i).getLexemas().equals("==") 
                            || tokensList.get(i).getLexemas().equals("!=") 
                            || tokensList.get(i).getLexemas().equals(">=") 
                            || tokensList.get(i).getLexemas().equals("<=") 
                            || tokensList.get(i).getLexemas().equals("<")
                            || tokensList.get(i).getLexemas().equals(">")){
                        
                        expr1 += posFixa.InFixToPosFix(expr2) + " "+ tokensList.get(i).getLexemas();
                        expr2 = "";
                    }else{
                        expr2 += tokensList.get(i).getLexemas().replaceAll("&", "");
                    }
                    i++;
                }
                String[] aux = expr2.split("\\)");
                expr1 += " "+posFixa.InFixToPosFix(aux[0]);
                
                CodInt.add(Comando+expr1);
            }
            
            
            // Leia(LE)
            if(tokensList.get(i).getLexemas().equals("LE")){
                CodInt.add("leia "+tokensList.get(i+2).getLexemas().replaceAll("&", ""));
                i = i + 4;
                
            }
            
            // Escreva(ESC)
            if(tokensList.get(i).getLexemas().equals("ESC")){
                String expr = "";
                Comando = "escreva "; 
                i++;
                while(!tokensList.get(i).getLexemas().equals(";")){
                    expr += tokensList.get(i).getLexemas().replaceAll("&", "");
                    i++;
                }
                
                CodInt.add(Comando+posFixa.InFixToPosFix(expr));
            }
            i++;
        }
    }
}
