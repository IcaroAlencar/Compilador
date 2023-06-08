package analise.semantica;

import analise.lexico.Tokens;
import analise.lexico.UtilSrt;
import java.util.ArrayList;


/**
 *
 * @author icaroalencar
 */
public class Semantico {
    
    //static ArrayList<Tokens> tokens = new ArrayList();
    
    static ArrayList<String> declaração_vars    = new ArrayList();
    static boolean isDeclarando                 = false;
    static boolean isValidaDivPorZero           = false;
    static boolean isErro                       = false;
    static ArrayList<String> ListaLog           = new ArrayList<String>();          
    
    public static ArrayList<String> Iniciar(ArrayList<Tokens> tk, boolean isLog) {
        ListaLog.add("##### Log Semantico #####");
        UtilSrt str  = new UtilSrt();
        declaração_vars.clear();
        tk.forEach((elements) -> {
            
            if(elements.getTokens().equals("TIPO_ATRIB")){
                isDeclarando = true;
                ListaLog.add("Achou o Tipo de Variavel");
                
            }
            if(isDeclarando == true && elements.getTokens().equals("FIM_LINHA")){
                isDeclarando = false;
                ListaLog.add("Terminou de Declarar as Variaveis");
            }
            
            if(isValidaDivPorZero == true && elements.getTokens().equals("FIM_LINHA")){
                isValidaDivPorZero = false;
            }
            
            if(elements.getTokens().equals("NOME_ATRIB")){
                // Verificação das variaveis declaradas
                VerificaVarsDeclaradas(elements);
            }
            
            if(elements.getLexemas().equals("/")){
                isValidaDivPorZero = true;
                ListaLog.add("Encontrou uma divisao");
            }
            
            if(isValidaDivPorZero == true){
                if(elements.getLexemas().equals("0")){
                    System.out.println("Erro.: Não é possivel efetuar divisao por zero");
                    isErro = true;
                    ListaLog.add("Encontrou uma divisao por zero");
                }else{
                    ListaLog.add("Nao foi possivel determinar se a divisao era por zero");
                }
            }
        });
        
        if(isErro){
            declaração_vars.clear();
        }
        ListaLog.add("##### Fim do Log Semantico #####");
        if(isLog){
            ListaLog.forEach((t) -> {
                System.out.println(t);
            });
        }
        return declaração_vars;
    }

    private static void VerificaVarsDeclaradas(Tokens element) {
        
        // Verificando se houve declaracao duplicada de variaveis.
        if(isDeclarando){
            ListaLog.add("Verificando se houve declaracao duplicada de variaveis.");
        }
        
        if(!isDeclarando){
            ListaLog.add("verificando se a variavel.:"+element.getLexemas()+" foi declarada.");
        }
        
        if(declaração_vars.contains(element.getLexemas()) && isDeclarando == true){
            System.out.println("Variavel Declarada mais de uma vez.: "
                    +element.getLexemas()+ " Linha.: "+element.getLinha()+
                    " Coluna.: "+element.getColuna());
            isErro = true;
            ListaLog.add("Foi encontrada uma Variavel que ja foi declarada");
        }
        // verificando se a variavel chamada foi declarada.
        else if(isDeclarando == false && !declaração_vars.contains(element.getLexemas())){
            System.out.println("Variavel não declarada.: "+element.getLexemas()
            +" Linha.: "+element.getLinha() + " Coluna.: "+element.getColuna());
            isErro = true;
            ListaLog.add("Foi encontrada uma variavel nao declarada");
        }else{
            // Apenas para nao duplicar a variavel na lista
            if(!declaração_vars.contains(element.getLexemas())){
                ListaLog.add("Variavel Declarada.: "+element.getLexemas());
                declaração_vars.add(element.getLexemas());
            }
        }
    }
    
}
