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
        List<Propriedade> propriedades = Utility.inicializarPropriedades();

        //TODO fazer o jogo infinito até um Jogador não ter dinheiro

        //TODO refatorar este trecho de código, switch case, em metodos








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


                        Utility.propriedadesDisponiveis(propriedades);
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


                        Utility.propriedadesDisponiveis(jogador.getMinhasPropriedades());
                        int venderPropriedade = InputUtility.getIntInput("Digite o número da Propriedade a ser vendida: ");
                        jogador.venderPropriedade(jogador.getMinhasPropriedades(), venderPropriedade);

                        break;
                    case 3:
                        System.out.println("\nVocê escolheu hipotecar uma propriedade!");

                        //Verifica se Jogador possui alguma Propriedade
                        if(!jogador.isThereProperty(jogador)){
                            break;
                        }


                        Utility.propriedadesDisponiveis(jogador.getMinhasPropriedades());
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
                        Utility.propriedadesDisponiveis(jogador.getMinhasPropriedades());
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
                            Utility.propriedadesDisponiveis(jogador.getMinhasPropriedades());
                        }

                        break;
                    case 8:
                        System.out.println("\nVocê escolheu visualizar todas as propriedades.");


                        Utility.propriedadesDisponiveis(propriedades);
                        System.out.println("=============================================================");


                        while(true){
                            boolean respostaInspecionar = InputUtility.getYesOrNoInput("\nDeseja SAIR ou INSPECIONAR? [S/I]  ", 'S', 'I');


                            //Sair do Menu de visualizar todas as propriedades
                            if(respostaInspecionar) break;


                            //Inspecionar alguma Propriedade
                            if(!respostaInspecionar) {
                                //Escolha de Qual Propriedade quer Inspecionar
                                int inspecionar = InputUtility.getIntInput("Qual Propriedade deseja Inspecionar? ");


                                //Verifica se o existe tal propriedade na lista
                                if (!jogador.isValidPropertyIndex(propriedades, inspecionar)) {
                                    return;
                                }


                                Propriedade propriedadeInspecionar = propriedades.get(inspecionar - 1);


                                //Inspeção em ação
                                propriedadeInspecionar.inspecionar();
                            }
                        }

                        break;
                    case 9:
                        System.out.println("\nVocê escolheu ver ranking.");
                        // TODO fazer o ranking
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }


                if(escolha == 0) break;
            }
        }
    }
}