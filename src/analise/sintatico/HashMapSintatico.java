package analise.sintatico;

import java.util.HashMap;

/**
 *
 * @author icaroalencar
 */

enum Operacao{
    EMPILHAR, DESEMPILHAR
}

enum NaoTerminal{
    BASE, FIM, CODIGO, ATRIBUITOS, INICIO, FIM_LINHA, $, TIPO_ATRIB, LISTA_ATRIB,
    NOME_ATRIB, VAZIO, CONDICAO, NCONDICAO, ABRE_CHAVES, FECHA_CHAVES, ABRE_PARENTESES, FECHA_PARENTESES,
    VIRGULA, LISTA_INSTRUCOES, INSTRUCOES, EXP_MATEMATICA, OPERADOR_LOGICO, OPERACAO_MATEMATICA, OPERANDO,
    DIFERENTE, REPITA, OPERADOR, NUMERO, ADICAO, SUBTRACAO, MULTIPLICACAO, DIVISAO, ATRIBUICAO, FRASE, ATRIBUTO_SAIDA,
    ESCREVA, LEIA, IGUAL, MENOR, MAIOR, MENOR_IGUAL, MAIOR_IGUAL, NAOCONDICAO
}

public class HashMapSintatico {
    HashMap<Operacao, String> meuHashOp     = new HashMap<>();
    HashMap<NaoTerminal, String> meuHashNt  = new HashMap<>();
    
    public void MeuHash(){
        
        // OPERAÇÃO
        meuHashOp.put(Operacao.EMPILHAR,    "EMPILHAR");
        meuHashOp.put(Operacao.DESEMPILHAR, "DESEMPILHAR");
        
        // NÃO TERMINAIS
        meuHashNt.put(NaoTerminal.ATRIBUITOS,           "ATRIBUTOS");
        meuHashNt.put(NaoTerminal.BASE,                 "BASE");
        meuHashNt.put(NaoTerminal.FIM,                  "FIM");
        meuHashNt.put(NaoTerminal.CODIGO,               "CODIGO");
        meuHashNt.put(NaoTerminal.INICIO,               "INICIO");
        meuHashNt.put(NaoTerminal.FIM_LINHA,            "FIM_LINHA");
        meuHashNt.put(NaoTerminal.$,                    "$");
        meuHashNt.put(NaoTerminal.FIM_LINHA,            "FIM_LINHA");
        meuHashNt.put(NaoTerminal.TIPO_ATRIB,           "TIPO_ATRIB");
        meuHashNt.put(NaoTerminal.LISTA_ATRIB,          "LISTA_ATRIB");
        meuHashNt.put(NaoTerminal.NOME_ATRIB,           "NOME_ATRIB");
        meuHashNt.put(NaoTerminal.VAZIO,                "VAZIO");
        meuHashNt.put(NaoTerminal.NCONDICAO,            "NCONDICAO");
        meuHashNt.put(NaoTerminal.FECHA_CHAVES,         "FECHA_CHAVES");
        meuHashNt.put(NaoTerminal.ABRE_CHAVES,          "ABRE_CHAVES");
        meuHashNt.put(NaoTerminal.CODIGO,               "CODIGO");
        meuHashNt.put(NaoTerminal.CONDICAO,             "CONDICAO");
        meuHashNt.put(NaoTerminal.VIRGULA,              "VIRGULA");
        meuHashNt.put(NaoTerminal.INSTRUCOES,           "INSTRUCOES");
        meuHashNt.put(NaoTerminal.LISTA_INSTRUCOES,     "LISTA_INSTRUCOES");
        meuHashNt.put(NaoTerminal.ABRE_PARENTESES,      "ABRE_PARENTESES");
        meuHashNt.put(NaoTerminal.FECHA_PARENTESES,     "FECHA_PARENTESES");
        meuHashNt.put(NaoTerminal.EXP_MATEMATICA,       "EXPRESSAO_MATEMATICA");
        meuHashNt.put(NaoTerminal.OPERADOR_LOGICO,      "OPERADOR_LOGICO");
        meuHashNt.put(NaoTerminal.OPERANDO,             "OPERANDO");
        meuHashNt.put(NaoTerminal.OPERACAO_MATEMATICA,  "OPERACAO_MATEMATICA");
        meuHashNt.put(NaoTerminal.DIFERENTE,            "DIFERENTE");
        meuHashNt.put(NaoTerminal.REPITA,               "REPITA");
        meuHashNt.put(NaoTerminal.OPERADOR,             "OPERADOR");
        meuHashNt.put(NaoTerminal.NUMERO,               "NUMERO");
        meuHashNt.put(NaoTerminal.ADICAO,               "ADICAO");
        meuHashNt.put(NaoTerminal.SUBTRACAO,            "SUBTRACAO");
        meuHashNt.put(NaoTerminal.MULTIPLICACAO,        "MULTIPLICACAO");
        meuHashNt.put(NaoTerminal.DIVISAO,              "DIVISAO");
        meuHashNt.put(NaoTerminal.ATRIBUICAO,           "ATRIBUICAO");
        meuHashNt.put(NaoTerminal.FRASE,                "FRASE");
        meuHashNt.put(NaoTerminal.ATRIBUTO_SAIDA,       "ATRIBUTO_SAIDA");
        meuHashNt.put(NaoTerminal.ESCREVA,              "ESCREVA");
        meuHashNt.put(NaoTerminal.LEIA,                 "LEIA");
        meuHashNt.put(NaoTerminal.IGUAL,                "IGUAL");
        meuHashNt.put(NaoTerminal.MENOR,                "MENOR");
        meuHashNt.put(NaoTerminal.MAIOR,                "MAIOR");
        meuHashNt.put(NaoTerminal.MENOR_IGUAL,          "MENOR_IGUAL");
        meuHashNt.put(NaoTerminal.MAIOR_IGUAL,          "MAIOR_IGUAL");
        meuHashNt.put(NaoTerminal.NAOCONDICAO,          "NAOCONDICAO");
    }
    
    // Produção
    public String getEmpilha(){
        return meuHashOp.get(Operacao.EMPILHAR);
    }
    public String getDesempilhar(){
        return meuHashOp.get(Operacao.DESEMPILHAR);
    }
    
    // Não Terminais
    public String get$(){
        return meuHashNt.get(NaoTerminal.$);
    }
    public String getBase(){
        return meuHashNt.get(NaoTerminal.BASE);
    }
    public String getFim(){
        return meuHashNt.get(NaoTerminal.FIM);
    }
    public String getCodigo(){
        return meuHashNt.get(NaoTerminal.CODIGO);
    }
    public String getAtributos(){
        return meuHashNt.get(NaoTerminal.ATRIBUITOS);
    }
    public String getInicio(){
        return meuHashNt.get(NaoTerminal.INICIO);
    }
    public String getTipo_Atrib(){
        return meuHashNt.get(NaoTerminal.TIPO_ATRIB);
    }
    public String getFim_Linha(){
        return meuHashNt.get(NaoTerminal.FIM_LINHA);
    }
    public String getLista_Atrib(){
        return meuHashNt.get(NaoTerminal.LISTA_ATRIB);
    }
    public String getNome_Atrib(){
        return meuHashNt.get(NaoTerminal.NOME_ATRIB);
    }
    public String getVazio(){
        return meuHashNt.get(NaoTerminal.VAZIO);
    }
    public String getNCondicao(){
        return meuHashNt.get(NaoTerminal.NCONDICAO);
    }
    public String getAbreChaves(){
        return meuHashNt.get(NaoTerminal.ABRE_CHAVES);
    }
    public String getFechaChaves(){
        return meuHashNt.get(NaoTerminal.FECHA_CHAVES);
    }
    public String getCondicao(){
        return meuHashNt.get(NaoTerminal.CONDICAO);
    }
    public String getVirgula(){
        return meuHashNt.get(NaoTerminal.VIRGULA);
    }
    public String getInstrucoes(){
        return meuHashNt.get(NaoTerminal.INSTRUCOES);
    }
    public String getLista_Instrucoes(){
        return meuHashNt.get(NaoTerminal.LISTA_INSTRUCOES);
    }
    public String getAbre_Parenteses(){
        return meuHashNt.get(NaoTerminal.ABRE_PARENTESES);
    }
    public String getFecha_Parenteses(){
        return meuHashNt.get(NaoTerminal.FECHA_PARENTESES);
    }
    public String getExpressao_Matematica(){
        return meuHashNt.get(NaoTerminal.EXP_MATEMATICA);
    } 
    public String getOperador_Logico(){
        return meuHashNt.get(NaoTerminal.OPERADOR_LOGICO);
    } 
    public String getOperando(){
        return meuHashNt.get(NaoTerminal.OPERANDO);
    } 
    public String getOperacao_Matematica(){
        return meuHashNt.get(NaoTerminal.OPERACAO_MATEMATICA);
    } 
    public String getDiferente(){
        return meuHashNt.get(NaoTerminal.DIFERENTE);
    }
    public String getRepita(){
        return meuHashNt.get(NaoTerminal.REPITA);
    } 
    public String getOperador(){
        return meuHashNt.get(NaoTerminal.OPERADOR);
    }
    public String getNumero(){
        return meuHashNt.get(NaoTerminal.NUMERO);
    }
    public String getAdicao(){
        return meuHashNt.get(NaoTerminal.ADICAO);
    }
    public String getSubtracao(){
        return meuHashNt.get(NaoTerminal.SUBTRACAO);
    }
    public String getMultiplicacao(){
        return meuHashNt.get(NaoTerminal.MULTIPLICACAO);
    }
    public String getDivisao(){
        return meuHashNt.get(NaoTerminal.DIVISAO);
    }
    public String getAtribuicao(){
        return meuHashNt.get(NaoTerminal.ATRIBUICAO);
    }
    public String getFrase(){
        return meuHashNt.get(NaoTerminal.FRASE);
    }
    public String getAtributo_Saida(){
        return meuHashNt.get(NaoTerminal.ATRIBUTO_SAIDA);
    }
    public String getEscreva(){
        return meuHashNt.get(NaoTerminal.ESCREVA);
    }
    public String getLeia(){
        return meuHashNt.get(NaoTerminal.LEIA);
    }
    public String getIgual(){
        return meuHashNt.get(NaoTerminal.IGUAL);
    }
    public String getMenor(){
        return meuHashNt.get(NaoTerminal.MENOR);
    }
    public String getMenor_Igual(){
        return meuHashNt.get(NaoTerminal.MENOR_IGUAL);
    }
    public String getMaior(){
        return meuHashNt.get(NaoTerminal.MAIOR);
    }
    public String getMaior_Igual(){
        return meuHashNt.get(NaoTerminal.MAIOR_IGUAL);
    }
    public String getNaoCondicao(){
        return meuHashNt.get(NaoTerminal.NAOCONDICAO);
    }
}
