import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Jogador {
    private float dinheiro;
    private List<Propriedade> minhasPropriedades;
    private String nome;
    private float riquezaTotal;
    private List<Emprestimo> emprestimosAtivos;


    public Jogador(String nome, float dinheiro){
        this.minhasPropriedades = new ArrayList<>();
        this.emprestimosAtivos = new ArrayList<>();
        this.nome = nome;
        this.dinheiro = dinheiro;
    }


    public void comprarPropriedade(List<Propriedade> listaPropriedades){
        System.out.println("\nVocê escolheu comprar uma propriedade!");


        //Seleciona uma Propriedade
        Propriedade propriedade = selecionarPropriedade(listaPropriedades);


        if(propriedade == null){
            return;
        }


        processarCompraPropriedade(propriedade);
    }


    public void hipotecar(List<Propriedade> listaPropriedades){
        System.out.println("\nVocê escolheu hipotecar uma propriedade!");


        //Verifica se Jogador possui alguma Propriedade
        if(!this.isThereProperty(this)){
            return;
        }


        //Seleciona Propriedade para Hipotecar
        GameUtility.propriedadesDisponiveis(this.getMinhasPropriedades());
        int hipotecarPropriedade = InputUtility.getIntInput("Qual Propriedade deseja Hipotecar? ");


        //Verifica se o index está correto
        if (!isValidPropertyIndex(listaPropriedades, hipotecarPropriedade)) {
            return;
        }


        Propriedade propriedade = listaPropriedades.get(hipotecarPropriedade - 1);


        if(propriedade.getQntCasas() != 0){
            System.out.println("Para Hipotecar é necessário NÃO ter Casas na Propriedade!");
            return;
        }


        if(propriedade.getDono() == null){
            System.out.println("Não foi possível Hipotecar pois essa Propriedade não pertence a ninguém!");
            return;
        }


        if(propriedade.isGarantia()){
            System.out.println("Não é possível Hipotecar Propriedade com status de Garantia!");
            return;
        }


        if(!this.getMinhasPropriedades().contains(propriedade)){
            System.out.println("A propriedade não está na sua Lista de Propriedades!");
            return;
        }


        if(propriedade.isHipotecado()){
            System.out.println("Não é possível Hipotecar uma Propriedade que já está Hipotecada!");
            return;
        }


        //Logica da Hipoteca
        this.setDinheiro(this.getDinheiro() + propriedade.getValor());
        propriedade.setHipotecado(true);
        System.out.printf("\n%s foi Hipotecada pelo(a) %s por $%.2f!\n", propriedade.getNome(), this.getNome(), propriedade.getValor());
    }


    public void venderPropriedade(List<Propriedade> listaPropriedades){
        System.out.println("\nVocê escolheu vender uma propriedade!");
        System.out.println("Propriedades são Vendidas por 75% de seu valor.");


        //Verifica se Jogador possui alguma Propriedade
        if(!this.isThereProperty(this)){
            return;
        }


        //Seleciona Propriedade para vender
        GameUtility.propriedadesDisponiveis(this.getMinhasPropriedades());
        int venderPropriedade = InputUtility.getIntInput("Digite o número da Propriedade a ser vendida: ");


        //Verifica se o index está correto
        if (!isValidPropertyIndex(listaPropriedades, venderPropriedade)) {
            return;
        }


        //Seleciona a propriedade escolhida pelo Jogador
        Propriedade propriedade = listaPropriedades.get(venderPropriedade - 1);


        // Não pode vender Propriedades Hipotecadas
        if(propriedade.isHipotecado() || propriedade.isGarantia()){
            System.out.println("Não é possível vender essa Propriedade defido ao seu Estado Atual!");
            return;
        }


        //75% do valor da Propriedade
        float valorVenda = (float) (propriedade.getValor() * 0.75);


        //A logica da venda em si
        this.setDinheiro(this.getDinheiro() + valorVenda);
        propriedade.setDono(null);
        listaPropriedades.remove(propriedade);
        System.out.printf("\n%s vendeu a propriedade %s por $%.2f!\n", this.getNome(), propriedade.getNome(), valorVenda);
    }


    public void pagarAluguel(Propriedade propriedade){
        // Verifica se a Propriedade está com status de Hipotecada
        if(propriedade.isHipotecado() || propriedade.isGarantia()){
            System.out.println("Você não precisa pagar aluguel para essa Propriedade!");
            return;
        }


        // Coleta o Dono da Propriedade
        Jogador dono = propriedade.getDono();


        // Se o Dono for Null
        if(dono == null){
            System.out.println("Não é possível Pagar Aluguel para Propriedades Sem Dono!");
            return;
        }


        // Se a propriedade for Empresa
        if(propriedade.isEmpresa()){
            System.out.printf("\n%s é uma Empresa, portanto você deverá pagar uma Taxa!\n", propriedade.getNome());

            int numDado = InputUtility.getIntInput("Digite o número resultante da Soma dos dados: ");


            if(!(numDado > 0 && numDado <= 12)){
                System.out.println("Número de Dado Inválido! ");
                return;
            }


            float valorTaxa = propriedade.calcularTaxa(numDado);

            this.setDinheiro(this.getDinheiro() - valorTaxa);
            dono.setDinheiro(dono.getDinheiro() + valorTaxa);

            System.out.printf("\n%s Pagou Taxa no valor de $%.2f para %s\n", this.getNome(), valorTaxa, dono.getNome());
            return;
        }


        //Calcular aluguel para pagar
        float aluguel = propriedade.calcularAluguel();


        // Verifica se Jogador possui dinheiro suficiente
        if(this.getDinheiro() < aluguel){
            System.out.println("Dinheiro Insuficiente para Pagar Aluguel!");
            return;
        }


        //Pagamento aluguel
        this.setDinheiro(this.getDinheiro() - aluguel);
        dono.setDinheiro(dono.getDinheiro() + aluguel);

        System.out.printf("\n%s Pagou Aluguel no valor de $%.2f para %s\n", this.getNome(), aluguel, dono.getNome());
    }


    public void comprarCasa(List<Propriedade> listaPropriedades, int index){
        //Verifica se o index está correto
        if (!isValidPropertyIndex(listaPropriedades, index)) {
            return;
        }


        Propriedade propriedade = listaPropriedades.get(index - 1);


        if(!propriedade.getDono().equals(this)){
            System.out.println("Não é possível Comprar Casa/Hotel para uma Propriedade que Não é Sua!");
            return;
        }

        if(this.getDinheiro() < propriedade.getValorCasa()){
            System.out.println("Você não possui Dinheiro o suficiente para Comprar uma Casa");
            return;
        }

        if(propriedade.isHipotecado() || propriedade.isGarantia()){
            System.out.println("Não é possível Comprar uma Casa em Imóvel Hipotecado Ou com Status de Garantia!");
            return;
        }

        if(propriedade.isEmpresa()){
            System.out.println("Não é possível Comprar uma Casa em Propriedades do tipo Empresa!");
            return;
        }


        // Se Propriedade já tiver Hotel, não é possível comprar mais nada
        if (propriedade.isHotel()) {
            System.out.println("\nEssa Propriedade já possui um Hotel. Não é possível realizar mais compras!");
        }


        // Se a Propriedade tiver 4 casas e nenhum hotel, Jogador pode comprar Hotel
        if (propriedade.getQntCasas() == 4 && !propriedade.isHotel()) {
            System.out.println("\nVocê possui 4 Casas nessa Propriedade.");
            System.out.printf("Deseja Comprar um Hotel por $%.2f?\n", propriedade.getValorCasa());

            boolean respostaDono = InputUtility.getYesOrNoInput("[S] Sim      [N] Não\n", 'S', 'N');

            if (!respostaDono) {
                System.out.println("\nO Proprietário não quis Comprar um Hotel!");
                return;
            }


            this.setDinheiro(this.getDinheiro() - propriedade.getValorCasa());
            propriedade.setHotel(true);
            System.out.println("\nParabéns, você Comprou um Hotel!");
        }


        // Se a Propriedade tiver menos que 4 casas, Jogador pode comprar uma Casa
        if (propriedade.getQntCasas() < 4) {
            System.out.printf("\nVocê pode adquirir Uma Casa por $%.2f\n", propriedade.getValorCasa());
            System.out.printf("Deseja Comprar uma Casa em %s?\n", propriedade.getNome());

            boolean respostaDono = InputUtility.getYesOrNoInput("[S] Sim      [N] Não\n", 'S', 'N');

            if (!respostaDono) {
                System.out.println("\nO Proprietário não quis Comprar uma Casa!");
                return;
            }


            //Dono quis comprar uma Casa
            this.setDinheiro(this.getDinheiro() - propriedade.getValorCasa());
            propriedade.setQntCasas(propriedade.getQntCasas() + 1);
            System.out.println("\nParabéns, você Comprou uma Casa!");
        }


        propriedade.inspecionar();
    }


    public void venderCasa(List<Propriedade> listaPropriedades, int index){
        //Verifica se o index está correto
        if (!isValidPropertyIndex(listaPropriedades, index)) {
            return;
        }


        Propriedade propriedade = listaPropriedades.get(index - 1);


        if(propriedade.getQntCasas() == 0){
            System.out.println("Não há Imóveis para Vender!");
            return;
        }

        if(propriedade.isEmpresa()){
            System.out.println("Não é possível Vender uma Casa em Propriedades do tipo Empresa!");
            return;
        }


        //Se possuir Hotel, vender o Hotel primeiro
        if(propriedade.isHotel()){
            System.out.println("Deseja Vender seu Hotel?");
            boolean resposta = InputUtility.getYesOrNoInput("[S] Sim      [N] Não\n", 'S', 'N');


            //Não Vender Hotel
            if(!resposta){
                System.out.println("Proprietário decidiu não Vender Hotel!");
                return;
            }


            //Vender Hotel
            float valorReceber = (float) (propriedade.getValorCasa() * 0.5);
            this.setDinheiro(this.getDinheiro() + valorReceber);
            propriedade.setHotel(false);

            System.out.printf("\nParabéns, você Vendeu seu Hotel por $%.2f!\n", valorReceber);
            propriedade.inspecionar();
            return;
        }


        propriedade.inspecionar();


        //Caso tiver Hotel perguntar se quer vender hotel

        int qtdVender = InputUtility.getIntInput("\nQuantas Casas deseja Vender? ");


        //Digitou valor incorreto de Casas
        if(qtdVender > propriedade.getQntCasas() || qtdVender <= 0){
            System.out.println("Número Inválido de Casas!");
            return;
        }


        //Logica da Venda
        float valorReceber = (float) ((propriedade.getValorCasa() * 0.5) * qtdVender);
        this.setDinheiro(this.getDinheiro() + valorReceber);
        propriedade.setQntCasas(propriedade.getQntCasas() - qtdVender);

        propriedade.inspecionar();
    }


    //Escolha Vender ou Comprar casa
    public void gerenciarVenderComprar(Jogador jogador){
        System.out.println("\nVocê escolheu comprar ou vender uma casa/hotel.");


        //Verifica se Jogador possui alguma Propriedade
        if(!jogador.isThereProperty(jogador)){
            return;
        }


        //Escolha Compra ou Venda
        System.out.println("\nPretende Comprar OU Vender? ");
        boolean respostaCasa = InputUtility.getYesOrNoInput("[C]Comprar       [V]Vender\n", 'C', 'V');


        //Visualizar Propriedades do Jogador
        GameUtility.propriedadesDisponiveis(jogador.getMinhasPropriedades());


        //Comprar casa
        if(respostaCasa){
            int escolhaPropriedadeCompra = InputUtility.getIntInput("\nEm qual Propriedade gostaria de Comprar uma Casa/Hotel? ");
            jogador.comprarCasa(jogador.getMinhasPropriedades(), escolhaPropriedadeCompra);
        }


        //Vender Casa
        if(!respostaCasa){
            int escolhaPropriedadeVenda = InputUtility.getIntInput("\nEm qual Propriedade gostaria de Vender Casa/Hotel? ");
            jogador.venderCasa(jogador.getMinhasPropriedades(), escolhaPropriedadeVenda);
        }
    }


    //Escolha Pagar Emprestimo ou Aluguel
    public void gerenciarEmprestimoAluguel(List<Propriedade> listaPropriedades, Jogador jogador){
        System.out.println("\nVocê escolheu pagar aluguel ou empréstimo.");


        //Escolha Pagar Emprestimo ou Aluguel
        System.out.println("\nPretende pagar Aluguel ou Empréstimo? ");
        boolean respostaPagar = InputUtility.getYesOrNoInput("[A]Aluguel       [E]Empréstimo\n", 'A', 'E');


        //Pagar Aluguel
        if(respostaPagar){
            GameUtility.propriedadesComDono(listaPropriedades, jogador);
            int escolhaPagar = InputUtility.getIntInput("\nEm qual Propriedade você deve Pagar o Aluguel? ");


            //Verifica se o index está correto
            if (!isValidPropertyIndex(listaPropriedades, escolhaPagar)) {
                return;
            }


            Propriedade propriedade = listaPropriedades.get(escolhaPagar - 1);


            if(propriedade.getDono() == this){
                System.out.println("Você é o Dono dessa Propriedade!");
                return;
            }


            jogador.pagarAluguel(propriedade);
        }


        //Pagar Emprestimo
        if(!respostaPagar){
            jogador.pagarEmprestimo();
        }
    }



    public void fazerEmprestimo(Jogador[] jogadores, Jogador devedor){
        //Emprestar dinheiro de outro Jogador
        System.out.println("\nVocê escolheu fazer empréstimo.");


        //Verifica se Jogador possui alguma Propriedade
        if(!this.isThereProperty(devedor)){
            return;
        }


        List<Jogador> credoresValidos = exibirCredoresValidor(jogadores, devedor);


        //Seleção do Jogador Credor
        Jogador credor = selecionarCredor(credoresValidos);


        if(credor == null){
            return;
        }


        //Seleção Propriedade como garantia
        Propriedade garantia = selecionarGarantia(devedor);


        if(garantia == null){
            return;
        }


        float valorEmprestimo = selecionarValorEmprestimo(credor, garantia);


        if(valorEmprestimo <= 0){
            return;
        }


        boolean respostaCredor = selecionarRespostaCredor(credor, devedor, valorEmprestimo);


        //Credor não aceitou emprestar dinheiro
        if(!respostaCredor){
            System.out.println("\n" + credor.getNome() + " não aceitou a oferta!");
            return;
        }


        //Credor aceita oferta
        processarEmprestimo(credor, devedor, valorEmprestimo, garantia);
    }


    public void pagarEmprestimo(){
        System.out.println("\nVocê escolheu Pagar Empréstimo.");


        Emprestimo emprestimoParaPagar = selecionarEmprestimoPagar();


        if(emprestimoParaPagar.equals(null)){
            return;
        }


        processarPagamentoEmprestimo(emprestimoParaPagar);
    }


    public void receberPagamentoEmprestimo(Emprestimo pagamento){

    }


    public void visualizarSaldoPropriedades(Jogador jogador){
        System.out.println("\nVocê escolheu visualizar seus Dados.");


        System.out.printf("\nDinheiro disponível: $%.2f\n", jogador.getDinheiro());


        if(!jogador.getMinhasPropriedades().isEmpty()){
            GameUtility.propriedadesDisponiveis(jogador.getMinhasPropriedades());
        }else {
            System.out.println("\nVocê não possui propriedades no momento.");
        }


        if(!jogador.getEmprestimosAtivos().isEmpty()){
            GameUtility.visualizarEmprestimoAtivos(jogador.getEmprestimosAtivos());
        }else{
            System.out.println("\nVocê não possui Emprestimos Ativos no momento.");
        }
    }


    public void visualizarTodasAsPropriedades(List<Propriedade> propriedades){
        System.out.println("\nVocê escolheu visualizar todas as propriedades.");


        GameUtility.propriedadesDisponiveis(propriedades);
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
                if (!this.isValidPropertyIndex(propriedades, inspecionar)) {
                    break;
                }


                Propriedade propriedadeInspecionar = propriedades.get(inspecionar - 1);


                //Inspeção em ação
                propriedadeInspecionar.inspecionar();
            }
        }
    }


    //Verificação e validação de index em Listas
    public boolean isValidPropertyIndex(List<Propriedade> listaPropriedades, int index) {
        if (index < 1 || index > listaPropriedades.size()) {
            System.out.println("Erro: Número inválido. Escolha uma propriedade disponível.");
            return false;
        }
        return true;
    }


    // Verifica se o Jogador possui alguma Propriedade (Propósitos de Lógica)
    public boolean isThereProperty(Jogador jogador){
        if(jogador.getMinhasPropriedades() == null || jogador.getMinhasPropriedades().isEmpty()){
            System.out.println("Você ainda não possui Propriedades!");
            return false;
        }
        return true;
    }


    //METODOS PRIVADOS DE AJUDA (PARA MANTER O CODIGO MAIS LIMPO)

    //Metodos para comprarPropriedade
    private Propriedade selecionarPropriedade(List<Propriedade> listaPropriedades){
        GameUtility.propriedadesDisponiveis(listaPropriedades);
        int escolhaPropriedade = InputUtility.getIntInput("Digite o número da Propriedade desejada: ");


        //Verifica se o index está correto
        if (!isValidPropertyIndex(listaPropriedades, escolhaPropriedade)) {
            return null;
        }


        Propriedade propriedade = listaPropriedades.get(escolhaPropriedade-1);


        if(! (this.getDinheiro() > propriedade.getValor())){
            System.out.println("Dinheiro insuficiente!");
            return null;
        }


        if(propriedade.isGarantia()){
            System.out.println("Não é possível comprar uma Propriedade em status de Garantia!");
            return null;
        }


        //Se a Propriedade estiver Hipotecada e Quem está comprando não for o Dono da Hipoteca
        if(propriedade.isHipotecado() &&  !propriedade.getDono().equals(this)){
            System.out.println("Não é possível comprar uma Propriedade Hipotecada que não é Sua!");
            return null;
        }


        return propriedade;
    }


    private void processarCompraPropriedade(Propriedade propriedade){
        //Se for o Dono de uma Propriedade Hipotecada e quer recomprá-la
        if(propriedade.isHipotecado()){
            comprarPropriedadeHipotecada(propriedade);
            return;
        }


        //Propriedade NÃO tem dono
        if(propriedade.getDono() == null){
            comprarPropriedadeSemDono(propriedade);
            return;
        }


        comprarPropriedadeDeTerceiro(propriedade);
    }


    private void comprarPropriedadeHipotecada(Propriedade propriedade){
        float valorComJuros = (float) (propriedade.getValor() * 1.30);

        System.out.println("\nVocê está comprando sua Propriedade Hipotecada.");
        System.out.printf("Juros de 30%%, valor a pagar: $%.2f", valorComJuros);

        propriedade.setHipotecado(false);
        this.setDinheiro(this.getDinheiro() - valorComJuros);
        System.out.printf("\n%s recuperou %s, sua propriedade Hipotecada, por $%.2f!\n", this.getNome(), propriedade.getNome(), valorComJuros);
    }


    private void comprarPropriedadeSemDono(Propriedade propriedade){
        this.getMinhasPropriedades().add(propriedade);
        propriedade.setDono(this);
        this.setDinheiro(this.getDinheiro() - propriedade.getValor());
        System.out.printf("\n%s comprou a propriedade %s por $%.2f!\n", this.getNome(), propriedade.getNome(), propriedade.getValor());
    }


    private void comprarPropriedadeDeTerceiro(Propriedade propriedade){
        //Propriedade TEM dono
        Jogador donoAtual = propriedade.getDono();


        //Se o DonoAtual tenta comprar uma Propriedade que já é dele
        if (this.equals(donoAtual)){
            System.out.println("\nNão é possível comprar uma Propriedade que já é Sua!");
            return;
        }


        //Caso o DonoAtual não queira vender sua Propriedade
        if(!solicitarVenda(donoAtual, propriedade)){
            System.out.println("\nO Proprietário não quis Vender sua Propriedade!");
            return;
        }


        //Caso o donoAtual queira vender, Jogador interessado deverá fazer uma proposta
        float proposta = obterProposta();
        if(!confirmarProposta(donoAtual, propriedade, proposta)){
            //Caso o DonoAtual não aceitou a proposta feita pelo Jogador Interessado
            System.out.println("\nO Proprietário não aceitou a Proposta!");
            return;
        }


        //donoAtual aceitou a proposta
        finalizarTransacao(donoAtual, propriedade, proposta);
    }


    private boolean solicitarVenda(Jogador donoAtual, Propriedade propriedade){
        System.out.printf("\n%s, deseja Vender %s para %s?\n",
                donoAtual.getNome(), propriedade.getNome(), this.getNome());

        return InputUtility.getYesOrNoInput("[S] Sim      [N] Não\n", 'S', 'N');
    }


    private float obterProposta(){
        return InputUtility.getFloatInput("\nFaça uma Proposta para o Proprietário: $");
    }


    private boolean confirmarProposta(Jogador dono, Propriedade propriedade, float proposta) {
        System.out.printf("\n%s, Aceita a Oferta de $%.2f por %s? \n",
                dono.getNome(), proposta, propriedade.getNome());
        return InputUtility.getYesOrNoInput("[S] Sim      [N] Não\n", 'S', 'N');
    }


    private void finalizarTransacao(Jogador donoAtual, Propriedade propriedade, float proposta){
        donoAtual.getMinhasPropriedades().remove(propriedade);
        donoAtual.setDinheiro(donoAtual.getDinheiro() + proposta);

        this.getMinhasPropriedades().add(propriedade);
        propriedade.setDono(this);
        this.setDinheiro(this.getDinheiro() - proposta);
        System.out.printf("\n%s comprou a propriedade %s de %s por $%.2f!\n", this.getNome(), propriedade.getNome(), donoAtual.getNome(), proposta);
    }
    

    //Metodos para fazerEmprestimo
    private List<Jogador> exibirCredoresValidor(Jogador[] jogadores, Jogador JogadorAtual){
        // Cópia dos Jogadores[] para não usar metodo sort no Array original
        Jogador[] jogadoresOrdenados = Arrays.copyOf(jogadores, jogadores.length);


        // Organiza Jogadores em ordem decrescente de dinheiro
        Arrays.sort(jogadoresOrdenados, new Comparator<Jogador>() {
            @Override
            public int compare(Jogador j1, Jogador j2) {
                return Float.compare(j2.getDinheiro(), j1.getDinheiro());
            }
        });


        //Cria uma Lista com apenas com Jogadores válidos
        List<Jogador> jogadoresValidos = new ArrayList<>();
        for(Jogador jogador : jogadoresOrdenados){
            if(jogador != null && jogador != this){
                jogadoresValidos.add(jogador);
            }
        }


        //Mostra jogadores e seus respectivos Saldos em Dinheiro
        System.out.println("\nEscolha um Jogador para Emprestar dinheiro: ");
        for (int i = 0; i < jogadoresValidos.size(); i++) {
            System.out.println((i + 1) + "º Lugar: " + jogadoresValidos.get(i).getNome() +
                    " - Dinheiro disponível: $" + jogadoresValidos.get(i).getDinheiro());
        }

        return jogadoresValidos;
    }


    private Jogador selecionarCredor(List<Jogador> credoresValidos){
        int escolhaCredor = InputUtility.getIntInput("\nDigite o número do Credor: ");


        if(escolhaCredor < 1 || escolhaCredor > credoresValidos.size()){
            System.out.println("Número Inválido");
            return null;
        }


        return credoresValidos.get(escolhaCredor - 1);
    }


    private Propriedade selecionarGarantia(Jogador devedor){
        /*
         * Jogador devedor deve selecionar uma de suas Propriedades que tenha no mínimo
         * metade do valor de sua Proposta para servir de garantia ao Jogador Credor
         */
        System.out.println("\nPara realizar um Empréstimo, você deve selecionar uma Propriedade como garantia.");
        System.out.print("\n======== Selecione uma de suas Propriedades como garantia ========");
        GameUtility.propriedadesDisponiveis(devedor.getMinhasPropriedades());


        //Escolha Propriedade garantia
        int propriedadeGarantia = InputUtility.getIntInput("Digite o número da Propriedade: ");


        if(!isValidPropertyIndex(devedor.getMinhasPropriedades(), propriedadeGarantia)){
            return null;
        }


        //Seleção da Propriedade de garantia
        Propriedade garantia = devedor.getMinhasPropriedades().get(propriedadeGarantia - 1);


        if(garantia.getQntCasas() != 0){
            System.out.println("\nA Propriedade de Garantia Não deve conter Casas/Hotels!");
            return null;
        }


        if(garantia.isGarantia() || garantia.isHipotecado()){
            System.out.println("\nPropriedades Hipotecadas ou Já Garantidas não podem ser selecionadas!");
            return null;
        }


        return garantia;
    }


    private float selecionarValorEmprestimo(Jogador credor, Propriedade garantia){
        System.out.println("\nTenha em Mente que Empréstimos possuem Taxa de 12.5% por turno!" +
                "\nValor Máximo de Empréstimo: $" + (garantia.getValor()*2) );

        float valorEmprestimo = InputUtility.getFloatInput("\nDigite o valor que deseja emprestar: $");


        if(valorEmprestimo > garantia.getValor()*2){
            System.out.println("Valor de Empréstimo Negado! Propriedade garantia Insuficiente!");
            return -1;
        }


        if(credor.getDinheiro() < valorEmprestimo){
            System.out.printf("\n%s não possui $%.2f disponível!\n", credor.getNome(), valorEmprestimo);
            return -1;
        }


        return valorEmprestimo;
    }


    private boolean selecionarRespostaCredor(Jogador credor, Jogador devedor, float valorEmprestimo){
        System.out.printf("\n%s, você aceita Conceder um Empréstimo no valor de $%.2f para %s?\n",
                credor.getNome(), valorEmprestimo, devedor.getNome());

        return InputUtility.getYesOrNoInput("[S] Sim      [N] Não\n", 'S', 'N');
    }


    private void processarEmprestimo(Jogador credor, Jogador devedor, float valorEmprestimo, Propriedade garantia){
        //Cria o emprestimo e processa
        Emprestimo emprestimo = new Emprestimo(credor, devedor, valorEmprestimo, garantia);

        emprestimo.processar();

        devedor.getEmprestimosAtivos().add(emprestimo);
    }


    //Metodos para pagarEmprestimo
    private Emprestimo selecionarEmprestimoPagar(){
        if(this.getEmprestimosAtivos().isEmpty()){
            System.out.println("\nVocê não possui Empréstimos Ativos!");
            return null;
        }


        GameUtility.visualizarEmprestimoAtivos(this.getEmprestimosAtivos());
        int escolhaEmprestimo = InputUtility.getIntInput("Selecione o Empréstimo a Pagar: ");


        if(escolhaEmprestimo < 1 || escolhaEmprestimo > this.getEmprestimosAtivos().size()){
            System.out.println("\nNúmero Inválido");
            return null;
        }


        return this.getEmprestimosAtivos().get(escolhaEmprestimo - 1);
    }


    private void processarPagamentoEmprestimo(Emprestimo emprestimo){
        float valorPagamento = obterValorPagamento(emprestimo);


        if(valorPagamento == -1){
            return;
        }


        finalizarPagamentoEmprestimo(emprestimo, valorPagamento);
    }


    private float obterValorPagamento(Emprestimo emprestimo){
        System.out.printf("\nValor da Dívida Atual: $%.2f\n",
                emprestimo.getValorAtual());


        float valorPagar = InputUtility.getFloatInput("O quanto deseja Pagar: $");


        if(valorPagar <= 0 || valorPagar > emprestimo.getValorAtual()){
            System.out.println("\nValor inválido!");
            return -1;
        }


        if(valorPagar > this.getDinheiro()){
            System.out.println("\nVocê não tem Dinheiro o suficiente para Pagar esse valor!");
            return -1;
        }


        return valorPagar;
    }


    private void finalizarPagamentoEmprestimo(Emprestimo emprestimo, float valorPagamento){
        Jogador credor = emprestimo.getCredor();
        Jogador devedor = emprestimo.getDevedor();


        //Pagamento dinheiro
        credor.setDinheiro(credor.getDinheiro() + valorPagamento);
        devedor.setDinheiro(credor.getDinheiro() - valorPagamento);


        //Quitou a divida
        if(valorPagamento == emprestimo.getValorAtual()){
            quitarEmprestimo(credor, devedor, emprestimo);

            System.out.println("\nParabéns! Você honrou com suas Dívidas!");
            System.out.printf("%s pagou $%.2f para %s no prazo de %d turnos!\n",
                    devedor.getNome(), valorPagamento, credor.getNome(), emprestimo.getPrazoFinal());

            return;
        }


        System.out.printf("\n%s pagou $%.2f para %s, de uma Divída total de $%.2f\n",
                devedor.getNome(), valorPagamento, credor.getNome(), emprestimo.getValorAtual());


        //Descontar do valor a pagar Emprestimo
        emprestimo.setValorAtual(emprestimo.getValorAtual() - valorPagamento);
    }


    private void quitarEmprestimo(Jogador credor, Jogador devedor, Emprestimo emprestimo){
        //Propriedade garantia é recuperada
        emprestimo.getGarantia().setGarantia(false);
        emprestimo.getGarantia().setDono(devedor);


        //Remoção do Emprestimo dos Emprestimos Ativos
        devedor.getEmprestimosAtivos().remove(emprestimo);
    }




    //Getters and Setters


    public List<Emprestimo> getEmprestimosAtivos() {
        return emprestimosAtivos;
    }

    public void setEmprestimosAtivos(List<Emprestimo> emprestimosAtivos) {
        this.emprestimosAtivos = emprestimosAtivos;
    }

    public float getRiquezaTotal() {
        return riquezaTotal;
    }

    public void setRiquezaTotal(float riquezaTotal) {
        this.riquezaTotal = riquezaTotal;
    }

    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public float getDinheiro() {
        return dinheiro;
    }


    public void setDinheiro(float dinheiro) {
        this.dinheiro = dinheiro;
    }


    public List<Propriedade> getMinhasPropriedades() {
        return minhasPropriedades;
    }


    public void setMinhasPropriedades(List<Propriedade> minhasPropriedades) {
        this.minhasPropriedades = minhasPropriedades;
    }
}
