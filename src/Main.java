import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        //Quantos Players
        int numJogadores = InputUtility.getIntInput("Quantos Jogadores irão participar: ");
        Jogador jogadores[] = new Jogador[numJogadores];


        //Nome de cada Jogador
        for(int i = 0; i < numJogadores; i++){
            int n = i+1;
            String nomeJogador = InputUtility.getStringInput("Nome do " + n + "° Jogador: ");
            jogadores[i] = new Jogador(nomeJogador, 2558);
        }


        //Inicializando Todas Propriedades em uma Lista
        List<Propriedade> propriedades = inicializarPropriedades();


        // Turno por Jogador
        for (Jogador jogador : jogadores) {
            System.out.printf("\nÉ a vez do(a) %s!\n", jogador.getNome());
            int escolha = InputUtility.getPlayerAction(jogador.getNome());

            switch (escolha) {
                case 1:
                    System.out.println("\nVocê escolheu comprar uma propriedade!");
                    // Add logic to buy property
                    break;
                case 2:
                    System.out.println("\nVocê escolheu hipotecar uma propriedade!");
                    // Add logic to mortgage property
                    break;
                case 3:
                    System.out.println("\nVocê escolheu vender uma propriedade!");
                    // Add logic to sell property
                    break;
                case 4:
                    System.out.println("\nVocê escolheu passar a vez.");
                    // Skip turn
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
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