import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static void realizarTurno(Jogador[] jogadores) {
        for (Jogador jogador : jogadores) {
            System.out.printf("\nÉ a vez do(a) %s!\n", jogador.getNome());

            while (true) {
                int escolha = InputUtility.getOption(jogador.getNome());

                if (escolha == 0) {
                    System.out.println("\nVez do próximo Jogador!");
                    break;
                }

                //processarEscolha(jogador, escolha);

                if (escolha == 0) break; // Explicit exit from the player's turn
            }
        }
    }



    public static List<Propriedade> inicializarPropriedades() {
        List<Propriedade> propriedades = new ArrayList<>();

        propriedades.add(new Propriedade("Leblon", 100));
        propriedades.add(new Propriedade("Avenida Presidente Vargas", 60));
        propriedades.add(new Propriedade("Avenida Nossa Senhora de Copacabana", 60));
        propriedades.add(new Propriedade("Avenida Brigadeiro Faria Lima", 240));
        propriedades.add(new Propriedade("Avenida Rebouças", 220));
        propriedades.add(new Propriedade("Avenida 9 de Julho", 220));
        propriedades.add(new Propriedade("Jardim Europa", 350));
        propriedades.add(new Propriedade("Avenida Paulista", 400));
        propriedades.add(new Propriedade("Avenida Vieira Souto", 120));
        propriedades.add(new Propriedade("Praia de Ipanema", 120));
        propriedades.add(new Propriedade("Avenida Brasil", 160));
        propriedades.add(new Propriedade("Avenida Augusta", 150));
        propriedades.add(new Propriedade("Rua 25 de Março", 200));
        propriedades.add(new Propriedade("Avenida Interlagos", 350));
        propriedades.add(new Propriedade("Morumbi", 400));
        propriedades.add(new Propriedade("Flamengo", 180));
        propriedades.add(new Propriedade("Botafogo", 180));
        propriedades.add(new Propriedade("Vila Mariana", 260));
        propriedades.add(new Propriedade("Avenida do Estado", 300));
        propriedades.add(new Propriedade("Avenida Ipiranga", 300));

        return propriedades;
    }


    public static void propriedadesDisponiveis(List<Propriedade> propriedades){
        System.out.println("\nPropriedades:");
        for (int i = 0; i < propriedades.size(); i++) {
            Propriedade propriedade = propriedades.get(i);


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
