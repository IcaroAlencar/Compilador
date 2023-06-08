/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analise.sintatico;

/**
 *
 * @author icaroalencar
 */
public class Log {
    
    private String Op;
    private String Pr;
    private String Nt;
    
    public void Log(){
        this.Op = "";
        this.Pr = "";
        this.Nt = "";
    }
    
    public void Log(String Operacao, String Producao){
        this.Op = Operacao;
        this.Pr = Producao;
    }
    
    public void Log(String Operacao, String NaoTerminal, String Producao){
        this.Op = Operacao;
        this.Nt = NaoTerminal;
        this.Pr = Producao;
    }
    
    public void setOperacao(String Operacao){
        this.Op = Operacao;
    }
    
    public void setProducao(String Producao){
        this.Pr = Producao;
    }
     
    public void setNaoTerminal(String NaoTerminal){
        this.Nt = NaoTerminal;
    }
    
    public String getOperacao(){
        return this.Op;
    }
    
    public String getProducao(){
        return this.Pr;
    }
    
    public String getNaoTerminal(){
        return this.Nt;
    }
    
    
    public String toStringEOpNt(){
        return "Operaçao..: " + this.Op + "    - " + " NãoTerminal.....: " + this.Nt;
    }
    
    public String toStringDOpNt(){
        return "Operaçao..: " + this.Op + " - " + " NãoTerminal.....: " + this.Nt;
    }
    
    public String toStringDOpNtPr(){
        return "Operaçao..: " + this.Op + " - " + " NãoTerminal.....: " + this.Nt + " - " + " Produção..: "+this.Pr;
    }
    
    public String toStringEOpNtPr(){
        return "Operaçao..: " + this.Op + " - " + " NãoTerminal..: " + this.Nt + " - " + " Produção..: "+this.Pr;
    }
    
    public String toStringDeuMatch(){
        return "DeuMatch..: " + this.Nt;
    }

}
