import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Utility {

    public static void visualizarRanking(Jogador[] jogadores) {
        // Calcula a Riqueza individual de cada Jogador
        for (Jogador jogador : jogadores) {
            float riquezaTotal = jogador.getDinheiro();


            // Seleciona e soma a riqueza, o valor de cada Propriedade
            for (Propriedade propriedade : jogador.getMinhasPropriedades()) {
                riquezaTotal += propriedade.getValor();
            }


            jogador.setRiquezaTotal(riquezaTotal);
        }


        // Organiza Jogadores em ordem decrescente de riqueza (mais ricos no topo)
        Arrays.sort(jogadores, new Comparator<Jogador>() {
            @Override
            public int compare(Jogador j1, Jogador j2) {
                return Float.compare(j2.getRiquezaTotal(), j1.getRiquezaTotal());
            }
        });


        // Mostra o Ranking em si
        System.out.println("\n======== Ranking dos Jogadores ========");
        for (int i = 0; i < jogadores.length; i++) {
            System.out.println((i + 1) + "º Lugar: " + jogadores[i].getNome() +
                    " - Riqueza Total: $" + jogadores[i].getRiquezaTotal());
        }
    }


    public static List<Propriedade> inicializarPropriedades() {
        List<Propriedade> propriedades = new ArrayList<>();

        propriedades.add(new Propriedade("Leblon", 100, false));
        propriedades.add(new Propriedade("Avenida Presidente Vargas", 60, false));
        propriedades.add(new Propriedade("Avenida Nossa Senhora de Copacabana", 60, false));
        propriedades.add(new Propriedade("Avenida Brigadeiro Faria Lima", 240, false));
        propriedades.add(new Propriedade("Avenida Rebouças", 220, false));
        propriedades.add(new Propriedade("Avenida 9 de Julho", 220, false));
        propriedades.add(new Propriedade("Jardim Europa", 350, false));
        propriedades.add(new Propriedade("Avenida Paulista", 400, false));
        propriedades.add(new Propriedade("Avenida Vieira Souto", 120, false));
        propriedades.add(new Propriedade("Praia de Ipanema", 120, false));
        propriedades.add(new Propriedade("Avenida Brasil", 160, false));
        propriedades.add(new Propriedade("Avenida Augusta", 150, false));
        propriedades.add(new Propriedade("Rua 25 de Março", 200, false));
        propriedades.add(new Propriedade("Avenida Interlagos", 350, false));
        propriedades.add(new Propriedade("Morumbi", 400, false));
        propriedades.add(new Propriedade("Flamengo", 180, false));
        propriedades.add(new Propriedade("Botafogo", 180, false));
        propriedades.add(new Propriedade("Vila Mariana", 260, false));
        propriedades.add(new Propriedade("Avenida do Estado", 300, false));
        propriedades.add(new Propriedade("Avenida Ipiranga", 300, false));

        // Empresas
        propriedades.add(new Propriedade("Imposto de Renda", 150, true));
        propriedades.add(new Propriedade("Companhia de Taxi Aéreo", 150, true));
        propriedades.add(new Propriedade("Companhia de Viação", 150, true));
        propriedades.add(new Propriedade("Companhia de Lucros ou Dividendos", 200, true));
        propriedades.add(new Propriedade("Companhia de Táxi", 250, true));
        propriedades.add(new Propriedade("Companhia de Aviação", 100, true));
        propriedades.add(new Propriedade("Companhia de Ferroviária", 300, true));
        propriedades.add(new Propriedade("Companhia de Tecnologia", 400, true));
        propriedades.add(new Propriedade("Companhia de Saúde", 300, true));
        propriedades.add(new Propriedade("Companhia de Educação", 200, true));

        return propriedades;
    }


    public static void propriedadesDisponiveis(List<Propriedade> propriedades, boolean apenasComDono){
        System.out.println("\nPropriedades:");
        for (int i = 0; i < propriedades.size(); i++) {
            Propriedade propriedade = propriedades.get(i);


            if (apenasComDono) {
                // Pula Propriedades sem Dono OU Hipotecadas
                if (propriedade.getDono() == null || propriedade.isHipotecado()) {
                    continue;
                }
            }


            if(propriedade.isHipotecado()){
                System.out.printf("%d. Nome: %s, Valor: $%.2f (Hipotecado por %s)\n", i + 1, propriedade.getNome(), propriedade.getValor(), propriedade.getDono().getNome());
                continue;
            }


            if(propriedade.getDono() != null){
                System.out.printf("%d. Nome: %s, Valor: $%.2f (Dono Atual: %s)\n", i + 1, propriedade.getNome(), propriedade.getValor(), propriedade.getDono().getNome());
                continue;
            }


            System.out.printf("%d. Nome: %s, Valor: $%.2f\n", i + 1, propriedade.getNome(), propriedade.getValor());
        }
    }

}
