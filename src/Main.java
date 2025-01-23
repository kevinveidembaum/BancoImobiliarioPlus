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

            while(true){
                int escolha = InputUtility.getOption(jogador.getNome());

                switch (escolha) {
                    case 0:
                        System.out.println("\nVez do próximo Jogador!");
                        break;
                    case 1:
                        System.out.println("\nVocê escolheu comprar uma propriedade!");


                        propriedadesDisponiveis(propriedades);
                        int escolhaPropriedade = InputUtility.getIntInput("Digite o número da Propriedade desejada: ");
                        jogador.comprarPropriedade(propriedades, escolhaPropriedade);

                        break;
                    case 2:
                        System.out.println("\nVocê escolheu vender uma propriedade!");
                        System.out.println("Propriedades são Vendidas por 75% de seu valor.");

                        //Verifica se Jogador possui alguma Propriedade
                        if(!jogador.isThereProperty(jogador)){
                            break;
                        }


                        propriedadesDisponiveis(jogador.getMinhasPropriedades());
                        int venderPropriedade = InputUtility.getIntInput("Digite o número da Propriedade a ser vendida: ");
                        jogador.venderPropriedade(jogador.getMinhasPropriedades(), venderPropriedade);

                        break;
                    case 3:
                        System.out.println("\nVocê escolheu hipotecar uma propriedade!");

                        //Verifica se Jogador possui alguma Propriedade
                        if(!jogador.isThereProperty(jogador)){
                            break;
                        }


                        propriedadesDisponiveis(jogador.getMinhasPropriedades());
                        int hipotecarPropriedade = InputUtility.getIntInput("Qual Propriedade deseja Hipotecar? ");
                        jogador.hipotecar(jogador.getMinhasPropriedades(), hipotecarPropriedade);

                        break;
                    case 4:
                        System.out.println("\nVocê escolheu comprar ou vender uma casa/hotel.");


                        //Verifica se Jogador possui alguma Propriedade
                        if(!jogador.isThereProperty(jogador)){
                            break;
                        }


                        System.out.println("\nPretende Comprar OU Vender? ");
                        boolean respostaCasa = InputUtility.getYesOrNoInput("[C]Comprar       [V]Vender\n", 'C', 'V');


                        //Comprar casa
                        propriedadesDisponiveis(jogador.getMinhasPropriedades());
                        if(respostaCasa){
                            int escolhaPropriedadeCompra = InputUtility.getIntInput("Em qual Propriedade gostaria de Comprar uma Casa/Hotel? ");
                            jogador.comprarCasa(jogador.getMinhasPropriedades(), escolhaPropriedadeCompra);
                        }


                        //Vender Casa
                        if(!respostaCasa){
                            int escolhaPropriedadeVenda = InputUtility.getIntInput("Em qual Propriedade gostaria de Vender Casa/Hotel? ");
                            jogador.venderCasa(jogador.getMinhasPropriedades(), escolhaPropriedadeVenda);
                        }

                        break;
                    case 5:
                        System.out.println("\nVocê escolheu fazer empréstimo.");
                        // Skip turn
                        break;
                    case 6:
                        System.out.println("\nVocê escolheu pagar empréstimo OU aluguel.");
                        // Skip turn
                        break;
                    case 7:
                        System.out.println("\nVocê escolheu visualizar Saldo e Minhas Propriedades.");

                        System.out.printf("\nDinheiro disponível: $%.2f\n", jogador.getDinheiro());

                        if (jogador.getMinhasPropriedades().isEmpty()) {
                            System.out.println("Você não possui propriedades no momento.");
                        } else {
                            propriedadesDisponiveis(jogador.getMinhasPropriedades());
                        }

                        break;
                    case 8:
                        System.out.println("\nVocê escolheu visualizar todas as propriedades.");


                        propriedadesDisponiveis(propriedades);
                        System.out.println("=============================================================");







                        // Skip turn
                        break;
                    case 9:
                        System.out.println("\nVocê escolheu ver ranking.");
                        // Skip turn
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }


                if(escolha == 0) break;
            }
        }
    }


    private static List<Propriedade> inicializarPropriedades() {
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


    private static void propriedadesDisponiveis(List<Propriedade> propriedades){
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