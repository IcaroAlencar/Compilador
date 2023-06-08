package gerador;

import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.shape.Arc;
import util.UtilStr;

/**
 *
 * @author icaroalencar
 */
public class Final {
    static ArrayList<String> CodFinal   = new ArrayList();
    static ArrayList<String> ListaLog   = new ArrayList();
    static ArrayList<String> ListaVar   = new ArrayList();
    static boolean IsLog                = false;
    static boolean isVar                = false;
    static UtilStr str                  = new UtilStr();
    static int tam                      = 15; 
    static int repita                   = 0;
    static ArrayList<String> opLogico   = new ArrayList<String>();
    static String aux                   = "";
    static Stack<String> pilha          = new Stack<String>();
    static String rotuloAux                    = "";
    
    
    public static void Iniciar(ArrayList<String> CodInter) {
        int count = 1;
        opLogico.add("<");
        opLogico.add(">");
        opLogico.add("==");
        opLogico.add("!=");
        opLogico.add(">=");
        opLogico.add("<=");
        ListaLog.add("##### Inicio da Geracao Do Codigo Final (MEPA) #####");
        for(int i = 0; i < CodInter.size(); i++){
            switch(CodInter.get(i).split(" ")[0]){
                
                // Inicio
                case "inicio":
                    CodFinal.add(str.PadRight("MAIN", tam, ' ')+"inicio do programa");
                    break;
                    
                // Declarando as variaveis    
                case "_VAR":
                    while(CodInter.get(i).contains("_VAR")){
                        String[] variavel = {}; 
                        variavel = CodInter.get(i).split(" ");

                        for(int j = 0; j < variavel.length; j++){
                            if(!variavel[j].equals("_VAR")){
                                ListaVar.add(variavel[j]);
                            }
                        } 
                        i++;
                    }
                    alocarVariaveis();
                    i--;
                    break;
                    
                case "escreva": 
                    String[] lista = CodInter.get(i).split(" ");
                    int contador = 1;
                    if(CodInter.get(i).contains("\"")){
                        break;
                    }
                    while(contador < lista.length){
                        if(ListaVar.contains(lista[contador])){
                            String var = lista[contador];
                            carregaValorVariavel(var);
                            CodFinal.add(str.PadRight("PRNT", tam, ' ')
                                    +"imprime em tela o valor da variavel.: "+var);
                        }else{
                            CodFinal.add(str.PadRight("LDCT "+lista[contador], tam, ' ')
                                                + "carregando o valor.: "+lista[contador]);
                            CodFinal.add(str.PadRight("PRNT", tam, ' ') + "imprimindo");
                        
                        }
                        contador++;
                    }
                    
                    break;
                    
                // Lendo o teclado    
                case "leia": 
                    CodFinal.add(str.PadRight("READ", tam, ' ')+"lendo o teclado");
                    String[] leia   = {};
                    String var      = "";
                    leia = CodInter.get(i).split(" ");
                    for(int j = 0; j < leia.length; j++){
                        if(!leia[j].equals("leia")){
                            var = leia[j];
                        }
                    }
                    guardandoValorVariavel(var); 
                    break;
                    
                case "fim_repita":
                    String rotuloAux1 = pilha.pop();
                    CodFinal.add(str.PadRight("JUMP "+pilha.pop(), tam, ' ')+ "fim do laco - fim_repita - bis");
                    CodFinal.add("");
                    
                    CodFinal.add(str.PadRight(rotuloAux1 +": NOOP", tam, ' ')
                                    + "continuacao da repeticao do l"+repita);
                    break;
                
                case "se":
                    rotuloAux = rotulo();
                    pilha.push(rotuloAux);
                    String Operador_Logico = "";
                    String[] listaAux = CodInter.get(i).split(" ");
                    for(int x = 0; x < listaAux.length; x++){
                        if(ListaVar.contains(listaAux[x])){
                            carregaValorVariavel(listaAux[x]);
                        }else if(Character.isDigit(listaAux[x].charAt(0))){
                            CodFinal.add(str.PadRight("LDCT "+listaAux[x], tam, ' ')
                                                + "carregando o valor.: "+listaAux[x]);
                        }else{
                            switch(listaAux[x]){
                                case "+":
                                    CodFinal.add(str.PadRight("ADDD", tam, ' ')
                                    + "soma");
                                    break;
                                case "-":
                                    CodFinal.add(str.PadRight("SUBT", tam, ' ')
                                    + "subtracao");
                                    break;

                                case "/":
                                    CodFinal.add(str.PadRight("DIVI", tam, ' ')
                                    + "divisao");
                                    break;
                                case "*":
                                    CodFinal.add(str.PadRight("MULT", tam, ' ')
                                    + "multiplicacao");
                                    break;
                                    
                                case "<":
                                    Operador_Logico = str.PadRight("LESS", tam, ' ')+"menor";
                                    break;
                                    
                                case ">":
                                    Operador_Logico = str.PadRight("GRTR", tam, ' ')+"maior";
                                    break;
                                    
                                case "<=":
                                    Operador_Logico = str.PadRight("LEQU", tam, ' ')+"menor igual";
                                    break;
                                    
                                case ">=":
                                    Operador_Logico = str.PadRight("GEQU", tam, ' ')+"maior igual";
                                    break;
                                    
                                case "==":
                                    Operador_Logico = str.PadRight("EQUA", tam, ' ')+"igual";
                                    break;
                                    
                                case "!=":
                                    Operador_Logico = str.PadRight("DIFF", tam, ' ')+"diferente";
                                    break;
                            }
                        }
                    }
                    CodFinal.add(Operador_Logico);
                    CodFinal.add(str.PadRight("JMPF "+rotuloAux, tam, ' ') + "pula se for falso(SE)");
                    CodFinal.add("");
                    break;
                    
                case "senao":
                    rotuloAux = rotulo();
                    CodFinal.add(str.PadRight("JUMP "+rotuloAux, tam, ' ') + "fim_se");
                    CodFinal.add("");
                    CodFinal.add(str.PadRight(pilha.pop()+": NOOP", tam, ' ')+ "senao");
                    pilha.push(rotuloAux);
                    break;
                 
                case "fim_se":
                    CodFinal.add("");
                    CodFinal.add(str.PadRight(pilha.pop()+": NOOP", tam, ' ')+ "fimse");
                    
                    break;
                    
                case "repita_ate":
                    rotuloAux = rotulo();
                    pilha.push(rotuloAux);
                    CodFinal.add("");
                    CodFinal.add(str.PadRight(pilha.peek()+": NOOP", tam, ' ')+ "laco - repita - bis");
                    String[] listaRepita = CodInter.get(i).split(" ");
                    String var2 = "";
                    for(int x = 0; x < listaRepita.length; x++){
                        if(ListaVar.contains(listaRepita[x])){
                            carregaValorVariavel(listaRepita[x]);
                            if(!var2.isEmpty()){
                               CodFinal.add(var2);
                               var2 = "";
                               rotuloAux = rotulo();
                               pilha.push(rotuloAux);
                               CodFinal.add(str.PadRight("JMPF "+rotuloAux, tam, ' ') + "fim do repita");
                               
                            }
                        }
                        if(opLogico.contains(listaRepita[x])){
                            switch(listaRepita[x]){
                                case "<":
                                    var2 = str.PadRight("LESS", tam, ' ')+"menor";
                                    break;
                                    
                                case ">":
                                    var2 = str.PadRight("GRTR", tam, ' ')+"maior";
                                    break;
                                    
                                case "<=":
                                    var2 = str.PadRight("LEQU", tam, ' ')+"menor igual";
                                    break;
                                    
                                case ">=":
                                    var2 = str.PadRight("GEQU", tam, ' ')+"maior igual";
                                    break;
                                    
                                case "==":
                                    var2 = str.PadRight("EQUA", tam, ' ')+"igual";
                                    break;
                                    
                                case "!=":
                                    var2 = str.PadRight("DIFF", tam, ' ')+"diferente";
                                    break;
                            }
                        }
                    }
                    break;
                
                // Fim  
                case "fim":
                    CodFinal.add(str.PadRight("DLOC "+ListaVar.size(), tam, ' ')+"limpando a memoria");
                    CodFinal.add(str.PadRight("STOP", tam, ' ')+"para a execusao");
                    CodFinal.add(str.PadRight("END", tam, ' ')+"fim do programa");
                    break;
                    
                default:
                    /*  percorrer a lista de variaveis 
                         
                    */
                    int cont = 0;
                    String[] teste = CodInter.get(i).split(" ");
                    for(int x = 0; x < ListaVar.size(); x++){
                        if(ListaVar.get(x).equals(teste[cont])){
                            String var1 = teste[cont];
                            cont++;
                            if(teste[cont].equals("=")){
                                cont++;
                                if(cont == teste.length-1){
                                    if(ListaVar.contains(teste[cont])){
                                        carregaValorVariavel(teste[cont]);
                                        guardandoValorVariavel(var1);
                                        break;
                                    }else{
                                        CodFinal.add(str.PadRight("LDCT "+teste[cont], tam, ' ')
                                                + "carregando o valor.: "+teste[cont]);
                                        guardandoValorVariavel(var1);
                                        break;
                                    }
                                    
                                }else{
                                    while(cont < teste.length){
                                        if(ListaVar.contains(teste[cont])){
                                            carregaValorVariavel(teste[cont]);
                                        }else if(Character.isDigit(teste[cont].toCharArray()[0])){
                                            CodFinal.add(str.PadRight("LDCT "+teste[cont], tam, ' ')
                                                + "carregando o valor.: "+teste[cont]);
                                        }else{
                                            switch(teste[cont]){
                                                case "+":
                                                    CodFinal.add(str.PadRight("ADDD", tam, ' ')
                                                    + "soma");
                                                    break;
                                                case "-":
                                                    CodFinal.add(str.PadRight("SUBT", tam, ' ')
                                                    + "subtracao");
                                                    break;
                                                    
                                                case "/":
                                                    CodFinal.add(str.PadRight("DIVI", tam, ' ')
                                                    + "divisao");
                                                    break;
                                                case "*":
                                                    CodFinal.add(str.PadRight("MULT", tam, ' ')
                                                    + "multiplicacao");
                                                    break;
                                            }
                                        }
                                        cont++;
                                    }
                                    guardandoValorVariavel(var1);
                                    break;
                                }
                                
                                
                            }
                        }
                    }
                    
            }
        }
        System.out.println("");
        System.out.println("---------Inicio Codigo MEPA---------");
        CodFinal.forEach((linha) -> {
            System.out.println(linha);
        });
        System.out.println("---------Fim Codigo MEPA---------");
    } 

    private static void alocarVariaveis() {
       CodFinal.add(str.PadRight("ALOC "+ListaVar.size(), tam, ' ')+ "alocando o espaço na memoria para "+ListaVar.size()+" variaveis");
       
       //inicializando as variaveis com o valor zero
       for(int x = 0; x < ListaVar.size(); x++){
           CodFinal.add(str.PadRight("LDCT 0", tam, ' ')+"carrega o valor 0");
           CodFinal.add(str.PadRight("STVL 0,"+x, tam, ' ')+"armazena o valor na variavel "+ListaVar.get(x));
       }
    }

    private static void guardandoValorVariavel(String var) {
        CodFinal.add(str.PadRight("STVL 0,"+ListaVar.indexOf(var), tam, ' ')
                +"carrega o valor no endereco de memoria 0,"+ListaVar.indexOf(var)
                + " que corresponde a variavel.: "+var);
    }
    
    private static void carregaValorVariavel(String var) {
        CodFinal.add(str.PadRight("LDVL 0,"+ListaVar.indexOf(var), tam, ' ')
                +"carrega o valor da variavel que esta no endereco 0,"+ListaVar.indexOf(var)
                + " e que corresponde a variavel.: "+var);
    }

    private static String rotulo() {
        repita++;
        return "L"+repita;
    }
}







