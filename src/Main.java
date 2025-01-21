import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        int numJogadores = InputUtility.getIntInput("Quantos Jogadores irão participar: ");

        Jogador jogadores[] = new Jogador[numJogadores];

        for(int i = 0; i < numJogadores; i++){
            int n = i+1;
            String nomeJogador = InputUtility.getStringInput("Nome do " + n + "° Jogador: ");
            jogadores[i] = new Jogador(nomeJogador, 2558);
        }

        List<Propriedade> propriedades = inicializarPropriedades(); //Todas Propriedades em uma Lista
        propriedadesDisponiveis(propriedades);


        Propriedade propriedadeTeste = propriedades.get(2);

        System.out.println(jogadores[0].getDinheiro());
        jogadores[1].comprarPropriedade(propriedades, 1);
        System.out.println(jogadores[1].getDinheiro());



        propriedadesDisponiveis(propriedades);

        jogadores[1].hipotecar(propriedades, jogadores[1]);

        System.out.println(jogadores[1].getDinheiro());

        propriedadesDisponiveis(propriedades);

    }

    private static List<Propriedade> inicializarPropriedades() {
        List<Propriedade> propriedades = new ArrayList<>();
        propriedades.add(new Propriedade("Avenida Paulista", 500));
        propriedades.add(new Propriedade("Copacabana", 300));
        propriedades.add(new Propriedade("Leblon", 700));
        propriedades.add(new Propriedade("Ipanema", 650));
        propriedades.add(new Propriedade("Jardins", 450));
        return propriedades;
    }

    private static void propriedadesDisponiveis(List<Propriedade> propriedades){
        System.out.println("\nPropriedades disponíveis para compra:");
        for (int i = 0; i < propriedades.size(); i++) {
            Propriedade propriedade = propriedades.get(i);

            if(propriedade.isHipotecado()){
                System.out.printf("%d. Nome: %s, Valor: %.2f (Hipotecado por %s)\n", i + 1, propriedade.getNome(), propriedade.getValor(), propriedade.getDono().getNome());
                continue;
            }

            System.out.printf("%d. Nome: %s, Valor: %.2f\n", i + 1, propriedade.getNome(), propriedade.getValor());
        }
    }
}