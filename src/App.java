 

import analise.lexico.Lexico;
import analise.lexico.Tokens;
import analise.semantica.Semantico;
import analise.sintatico.Sintatico;
import gerador.Final;
import gerador.Intermediario;
import java.util.ArrayList;

/**
 *
 * @author icaroalencar
 */
public class App {
    
    static ArrayList<Tokens> TokenList      = new ArrayList();
    static ArrayList<String> ListVars       = new ArrayList();
    static ArrayList<String> CodInter       = new ArrayList();
    static String caminho;
    static Boolean isListaTokens            = false;
    static Boolean IsLog                    = false;
    static Boolean isError                  = false;
    
    public static void main(String[] args) {
        // Leitura dos argumentos
        for(int i=0; i < args.length; i++){
            switch(args[i]){
                // Listar tokens
                case "-lt":
                    isListaTokens = true;
                    break;
                    
                // Lista tudo, Tokens e Logs.  
                case "-tudo":
                    isListaTokens   = true;
                    IsLog           = true;
                    break;
                    
                // Lista os Logs    
                case "-lg":
                    isListaTokens = true;
                    break;
                    
                default:
                    caminho = args[i];
                    break;
                   
            }
        }
        
        /*
            Se não tiver argumentos, Apresenta um erro, 
            pois é nescessario pelo menos um argumento
            Caminho do arquico.
        */
        if(args.length == 0 || caminho.isEmpty()){
            System.out.println("Erro nos argumentos...");
            isError = true;
        }
        
        if(!isError){
            // Analise Lexica
            TokenList  = Lexico.Iniciar(caminho, isListaTokens, IsLog);
            
            if(!TokenList.isEmpty()){
                
                // Analise Sintatica
                isError = Sintatico.Iniciar(TokenList, IsLog);
                if(!isError){
                    // Analise Semantica
                    ListVars = Semantico.Iniciar(TokenList, IsLog);
                    if(!ListVars.isEmpty()){
                        // Gedador do Codigo Intermediario
                        CodInter = Intermediario.Iniciar(TokenList,ListVars, IsLog);
                        if(!CodInter.isEmpty()){
                            Final.Iniciar(CodInter);
                        }
                    }
                }
            }
        }
    }
}
