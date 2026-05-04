package main;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import java.util.HashMap;
import java.util.Map;

public class IndiceVulnerabilidade {

    public static void main(String[] args) {
        //Carrega arquivo fcl
        FIS fis = FIS.load("src/resource/analise.fcl", true);

        if (fis == null) {
            System.err.println("Can't load file");
            System.exit(1);
        }

        // Mostra todas as varivais linguisticas em grafico
        JFuzzyChart.get().chart(fis.getFunctionBlock("analise"));

        //Seta as entradas para cada variavel linguistica
        fis.setVariable("RendaPercapta", 0.9);
        fis.setVariable("ProcedenciaRenda", 0);

        //Avalia as regras
        fis.evaluate();

        //Mostra saída em grafico
        Variable analise = fis.getFunctionBlock("analise").getVariable("IVS");
        JFuzzyChart.get().chart(analise, analise.getDefuzzifier(), true);
        System.out.println(analise.getValue());

        //Normalização
        double Me = 66.64;
        double me = 8.31;
        double Md = 75;
        double md = 0;
        double indice = ((analise.getValue()-me)/(Me-me))*(Md-md);
        System.out.println(Math.round(indice));

        //Outras Variaveis
        Map<String, Integer> Tabela = new HashMap<>();
        Tabela.put("Fragilidade",5);
        Tabela.put("IdGenero",5);
        Tabela.put("EstudantePcD",3);
        Tabela.put("MembroFamiliarPcD",2);
        Tabela.put("CicloFilho",3);
        Tabela.put("CicloMembroFilho",2);
        Tabela.put("SitRuaOUAbrigoSoc",5);
        Tabela.put("Alugada",4);
        Tabela.put("CedidaOUResidencia",3);
        Tabela.put("MoradiaFinanciada",2);
        Tabela.put("MoradiaPropria",1);

        System.out.println(Tabela);
        //System.out.println(Tabela.get("Alugada"));

        double IVStotal = Tabela.get("CedidaOUResidencia")+Tabela.get("Fragilidade")+Tabela.get("MembroFamiliarPcD")+Tabela.get("CicloFilho")+Math.round(indice);
        System.out.println(IVStotal);
    }
    
}