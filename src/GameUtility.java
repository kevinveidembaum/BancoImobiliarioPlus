import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GameUtility {

    // Controle de flow do Jogo
    public void realizarTurno(List<Propriedade> propriedades, Jogador[] jogadores) {
        boolean jogoAtivo = true;
        int indiceJogadorAtual = 0;
        int turnoAtual = 0;

        while(jogoAtivo){
            Jogador jogador = jogadores[indiceJogadorAtual];

            System.out.printf("\nÉ a vez do(a) %s!\n", jogador.getNome());

            while (true) {
                int escolha = InputUtility.getOption(jogador.getNome());


                //Encerra o turno do Jogador Atual
                if (escolha == 0) {
                    System.out.println("\nVez do próximo Jogador!");
                    break;
                }


                //Encerra o Jogo independente do momento
                if(escolha == 999){
                    System.out.println("\nJogo Encerrado! Até a Próxima!");
                    jogoAtivo = false;
                    return;
                }


                switch (escolha) {
                    case 1 -> jogador.comprarPropriedade(propriedades);
                    case 2 -> jogador.venderPropriedade(jogador.getMinhasPropriedades());
                    case 3 -> jogador.hipotecar(jogador.getMinhasPropriedades());
                    case 4 -> jogador.gerenciarVenderComprar(jogador);
                    case 5 -> jogador.fazerEmprestimo(jogadores, jogador);
                    case 6 -> jogador.gerenciarEmprestimoAluguel(propriedades, jogador);
                    case 7 -> jogador.visualizarSaldoPropriedades(jogador);
                    case 8 -> jogador.visualizarTodasAsPropriedades(propriedades);
                    case 9 -> this.visualizarRanking(jogadores);
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            }


            //Adiciona 1 ao turno
            turnoAtual++;


            //Verifica se está no fim do Ciclo de Jogadores
            if(turnoAtual == jogadores.length){

                verificarPrazo(jogadores);

                //Reseta o Ciclo
                turnoAtual = 0;
            }


            // Avança para o próximo jogador
            indiceJogadorAtual = (indiceJogadorAtual + 1) % jogadores.length;
        }
    }


    //Gerar Jogadores
    public Jogador[] gerarJogadores(){
        //Quantos Players
        int numJogadores = InputUtility.getIntInput("Quantos Jogadores irão participar: ");
        Jogador jogadores[] = new Jogador[numJogadores];


        //Nome de cada Jogador
        for(int i = 0; i < numJogadores; i++){
            int n = i+1;
            String nomeJogador = InputUtility.getStringInput("Nome do " + n + "° Jogador: ");
            jogadores[i] = new Jogador(nomeJogador, 2558);
        }

        return jogadores;
    }


    //Verificar Prazo de pagamento de Emprestimos ativos
    public void verificarPrazo(Jogador[] jogadores){
        for (Jogador j : jogadores) {
            //Cria uma Lista para armazenar apenas Emprestimo que venceram
            List<Emprestimo> emprestimosVencidos = new ArrayList<>();


            for (Emprestimo emprestimo : j.getEmprestimosAtivos()) {
                emprestimo.calcularPrazo();

                // verifica o Prazo do Emprestimo
                if (emprestimo.verificarPrazoEstourou()) {


                    //Consequencias Inadimplência
                    emprestimo.processarInadimplencia(emprestimo);


                    // adicionar Emprestimo a Lista de Emprestimos Vencidos
                    emprestimosVencidos.add(emprestimo);
                }
            }


            //Remover Emprestimos vencidos da Lista de emprestimos dos Jogadores
            j.getEmprestimosAtivos().removeAll(emprestimosVencidos);
        }
    }


    public void visualizarRanking(Jogador[] jogadores) {
        // Calcula a Riqueza individual de cada Jogador
        for (Jogador jogador : jogadores) {
            float riquezaTotal = jogador.getDinheiro();
            float valorCasaHotel = 0;


            // Seleciona e soma a riqueza, o valor de cada Propriedade
            for (Propriedade propriedade : jogador.getMinhasPropriedades()) {
                float valorTotalPropriedade = 0;


                /* Operador ternário
                * Basicamente verifica se a Propriedade possui Hotel.
                * Caso Positivo: Multiplica o valor de uma Casa por cinco -
                * (1 Hotel é o valor de uma casa, mas para ter um Hotel é preciso ter 4 Casas)
                *
                * Caso Negativo: Multiplica o número atual de Casas na Propriedade pelo valor de Uma Casa.
                */
                valorCasaHotel += (propriedade.isHotel())? propriedade.getValorCasa() * 5 : propriedade.getQntCasas() * propriedade.getValorCasa();


                //Soma valor da Propriedade + valor de todas as construções nela (Casas/Hotel)
                valorTotalPropriedade += propriedade.getValor() + valorCasaHotel;


                riquezaTotal += valorTotalPropriedade;
            }


            //Atribui na riqueza total do Jogador
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


    public List<Propriedade> inicializarPropriedades() {
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


    public static void propriedadesDisponiveis(List<Propriedade> propriedades){
        System.out.println("\nPropriedades:");
        for (int i = 0; i < propriedades.size(); i++) {
            Propriedade propriedade = propriedades.get(i);


            if(propriedade.isGarantia()){
                System.out.printf("%d. Nome: %s, Valor: $%.2f (Propriedade Garantia para %s)\n", i + 1, propriedade.getNome(), propriedade.getValor(), propriedade.getDono().getNome());
                continue;
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


    // Mostra apenas Propriedades com Dono, sem contar o Jogador que chamou o metodo (usado na logica aluguel)
    public static void propriedadesComDono(List<Propriedade> propriedades, Jogador jogador) {
        System.out.println("\nPropriedades: ");
        for (int i = 0; i < propriedades.size(); i++) {
            Propriedade propriedade = propriedades.get(i);


            // Pula Propriedades sem Dono OU Hipotecadas OU o Dono é o próprio Jogador que chamou o metodo
            if (propriedade.getDono() == null || propriedade.isHipotecado() || propriedade.isGarantia() || propriedade.getDono().equals(jogador)) {
                continue;
            }


            System.out.printf("%d. Nome: %s, Valor: $%.2f (Dono Atual: %s)\n", i + 1, propriedade.getNome(), propriedade.getValor(), propriedade.getDono().getNome());
        }
    }


    // Mostra Emprestimo Ativos do Jogador
    public static void visualizarEmprestimoAtivos(List<Emprestimo> emprestimos) {
        System.out.println("\nEmpréstimos: ");
        for (int i = 0; i < emprestimos.size(); i++) {
            Emprestimo emprestimo = emprestimos.get(i);


            System.out.printf("%d. Credor: %s ==> Valor Emprestado: $%.2f, Valor a Pagar: $%.2f, Prazo Restante: %d turnos\n",
                    i + 1, emprestimo.getCredor().getNome(), emprestimo.getValorEmprestimo(),
                    emprestimo.getValorAtual(), emprestimo.getPrazoFinal());
        }
    }
}
