package analise.lexico;

import util.UtilStr;

public class Tokens {
    private String tokens;
    private String lexemas;
    private int linha;
    private int coluna;

    public Tokens(String tokens, String lexemas, int linha, int coluna) {
        this.tokens = tokens;
        this.lexemas = lexemas;
        this.linha = linha;
        this.coluna = coluna;
    }
    
    public Tokens() {
        this.tokens = tokens;
        this.lexemas = lexemas;
        this.linha = linha;
        this.coluna = coluna;
    }
    
    public String toString(){
        UtilStr uStr    = new UtilStr();
        String Token    = uStr.PadRight(this.tokens, 20,' ');
        String Lexema   = uStr.PadRight(this.lexemas,20,' ');
        String Linha    = uStr.PadRight(String.valueOf(this.linha), 5,' ');
        String Coluna   = uStr.PadRight(String.valueOf(this.coluna),6,' ');
        return Token + " | " + Lexema + " | " + Linha + " | " + Coluna + " | ";
    }

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    public String getLexemas() {
        return lexemas;
    }

    public void setLexemas(String lexemas) {
        this.lexemas = lexemas;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
    
    
}
