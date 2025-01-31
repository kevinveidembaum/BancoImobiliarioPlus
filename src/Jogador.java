import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private float dinheiro;
    private List<Propriedade> minhasPropriedades;
    private List<Emprestimo> emprestimosConcebidos;
    private List<Emprestimo> dividasEmprestimo;
    private String nome;
    private float riquezaTotal;


    public Jogador(String nome, float dinheiro){
        this.minhasPropriedades = new ArrayList<>();
        this.nome = nome;
        this.dinheiro = dinheiro;
    }


    public void comprarPropriedade(List<Propriedade> listaPropriedades){
        System.out.println("\nVocê escolheu comprar uma propriedade!");


        //Seleciona uma Propriedade
        Utility.propriedadesDisponiveis(listaPropriedades);
        int escolhaPropriedade = InputUtility.getIntInput("Digite o número da Propriedade desejada: ");


        //Verifica se o index está correto
        if (!isValidPropertyIndex(listaPropriedades, escolhaPropriedade)) {
            return;
        }


        Propriedade propriedade = listaPropriedades.get(escolhaPropriedade-1);


        if(! (this.getDinheiro() > propriedade.getValor())){
            System.out.println("Dinheiro insuficiente!");
            return;
        }


        //Se a Propriedade estiver Hipotecada e Quem está comprando não for o Dono da Hipoteca
        if(propriedade.isHipotecado() &&  !propriedade.getDono().equals(this)){
            System.out.println("Não é possível comprar uma Propriedade Hipotecada que não é Sua!");
            return;
        }


        //Se for o Dono de uma Propriedade Hipoteca e quer recomprá-la
        if(propriedade.isHipotecado()){
            float valorComJuros = (float) (propriedade.getValor() * 1.30);

            System.out.println("\nVocê está comprando sua Propriedade Hipotecada.");
            System.out.printf("Juros de 30%%, valor a pagar: $%.2f", valorComJuros);

            propriedade.setHipotecado(false);
            this.setDinheiro(this.getDinheiro() - valorComJuros);
            System.out.printf("\n%s recuperou %s, sua propriedade Hipotecada, por $%.2f!\n", this.getNome(), propriedade.getNome(), valorComJuros);
            return;
        }


        //Propriedade NÃO tem dono
        if(propriedade.getDono() == null){
            this.getMinhasPropriedades().add(propriedade);
            propriedade.setDono(this);
            this.setDinheiro(this.getDinheiro() - propriedade.getValor());
            System.out.printf("\n%s comprou a propriedade %s por $%.2f!\n", this.nome, propriedade.getNome(), propriedade.getValor());
            return;
        }


        //Propriedade TEM dono
        Jogador donoAtual = propriedade.getDono();


        //Se o DonoAtual tenta comprar uma Propriedade que já é dele
        if (propriedade.getDono().equals(this)){
            System.out.println("Não é possível comprar uma Propriedade que já é Sua!");
            return;
        }


        //Pergunta para o donoAtual se deseja Vender sua Propriedade
        System.out.printf("\n%s, deseja Vender %s para %s?\n", donoAtual.getNome(), propriedade.getNome(), this.getNome());
        boolean respostaDono = InputUtility.getYesOrNoInput("[S] Sim      [N] Não\n", 'S', 'N');


        //Caso o DonoAtual não queira vender sua Propriedade
        if(!respostaDono){
            System.out.println("\nO Proprietário não quis Vender sua Propriedade!");
            return;
        }


        //Caso o donoAtual queira vender, Jogador interessado deverá fazer uma proposta
        float proposta = InputUtility.getFloatInput("\nFaça uma Proposta para o Proprietário: $");
        System.out.printf("\n%s, Aceita a Oferta? \n", donoAtual.getNome());
        respostaDono = InputUtility.getYesOrNoInput("[S] Sim      [N] Não\n", 'S', 'N');


        //Caso o DonoAtual não aceitou a proposta feita pelo Jogador Interessado
        if(!respostaDono){
            System.out.println("\nO Proprietário não aceitou a Proposta!");
            return;
        }


        //donoAtual aceitou a proposta
        donoAtual.getMinhasPropriedades().remove(propriedade);
        donoAtual.setDinheiro(donoAtual.getDinheiro() + proposta);

        this.getMinhasPropriedades().add(propriedade);
        propriedade.setDono(this);
        this.setDinheiro(this.getDinheiro() - proposta);
        System.out.printf("\n%s comprou a propriedade %s de %s por $%.2f!\n", this.getNome(), propriedade.getNome(), donoAtual.getNome(), proposta);
    }


    public void hipotecar(List<Propriedade> listaPropriedades){
        System.out.println("\nVocê escolheu hipotecar uma propriedade!");


        //Verifica se Jogador possui alguma Propriedade
        if(!this.isThereProperty(this)){
            return;
        }


        //Seleciona Propriedade para Hipotecar
        Utility.propriedadesDisponiveis(this.getMinhasPropriedades());
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
        Utility.propriedadesDisponiveis(this.getMinhasPropriedades());
        int venderPropriedade = InputUtility.getIntInput("Digite o número da Propriedade a ser vendida: ");


        //Verifica se o index está correto
        if (!isValidPropertyIndex(listaPropriedades, venderPropriedade)) {
            return;
        }


        //Seleciona a propriedade escolhida pelo Jogador
        Propriedade propriedade = listaPropriedades.get(venderPropriedade - 1);


        // Não pode vender Propriedades Hipotecadas
        if(propriedade.isHipotecado()){
            System.out.println("Não é possível vender uma Propriedade Hipotecada!");
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


    public void pagarAluguel(Propriedade propriedade, Jogador dono){
        // Verifica se Jogador possui dinheiro suficiente
        if(this.getDinheiro() < propriedade.getValorAluguel()){
            System.out.println("Dinheiro Insuficiente para Pagar Aluguel!");
            return;
        }


        // Verifica se o Dono está tentando pagar Aluguel por uma Propriedade dele mesmo
        if(propriedade.getDono().equals(this)){
            System.out.println("Nâo é possível Pagar Aluguel pois esta Propriedade é Sua!");
            return;
        }


        // Verifica se o dono é o dono da propriedade mesmo
        if(!propriedade.getDono().equals(dono)){
            System.out.println("O Jogador declarado Dono não é o Dono desta Propriedade!");
            return;
        }


        // Verifica se a Propriedade está com status de Hipotecada
        if(propriedade.isHipotecado()){
            System.out.println("Você não precisa pagar aluguel para uma Propriedade Hipotecada!");
            return;
        }


        // Se a propriedade for Empresa
        if(propriedade.isEmpresa()){
            System.out.printf("\n%s é uma Empresa, portanto você deverá pagar uma Taxa!\n", propriedade.getNome());

            int numDado = InputUtility.getIntInput("Digite o número resultante da Soma dos dados: ");
            float valorTaxa = propriedade.calcularTaxa(numDado);

            this.setDinheiro(this.getDinheiro() - valorTaxa);
            dono.setDinheiro(dono.getDinheiro() + valorTaxa);

            System.out.printf("\n%s Pagou Taxa no valor de $%.2f para %s\n", this.getNome(), valorTaxa, dono.getNome());
            return;
        }


        this.setDinheiro(this.getDinheiro() - propriedade.getValorAluguel());
        dono.setDinheiro(dono.getDinheiro() + propriedade.getValorAluguel());

        System.out.printf("\n%s Pagou Aluguel no valor de $%.2f para %s\n", this.getNome(), propriedade.getValorAluguel(), dono.getNome());
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

        if(propriedade.isHipotecado()){
            System.out.println("Não é possível Comprar uma Casa em Imóvel Hipotecado");
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
        Utility.propriedadesDisponiveis(jogador.getMinhasPropriedades());


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
            Utility.propriedadesComDono(listaPropriedades, this);
            int escolhaPagar = InputUtility.getIntInput("\nEm qual Propriedade você deve Pagar o Aluguel? ");


            //Verifica se o index está correto
            if (!isValidPropertyIndex(listaPropriedades, escolhaPagar)) {
                return;
            }


            Propriedade propriedade = listaPropriedades.get(escolhaPagar - 1);


            jogador.pagarAluguel(propriedade, propriedade.getDono());
        }


        //todo pagar emprestimo
        //Pagar Emprestimo
        if(!respostaPagar){
            int escolhaPropriedadeVenda = InputUtility.getIntInput("\nEm qual Propriedade gostaria de Vender Casa/Hotel? ");
            jogador.venderCasa(jogador.getMinhasPropriedades(), escolhaPropriedadeVenda);
        }
    }



    public void fazerEmprestimo(Jogador credor, float valorEmprestar, Propriedade garantia){
        //TODO fazer emprestimo
    }


    public void pagarEmprestimo(Emprestimo emprestimo, float valorPagar){

    }


    public void receberPagamentoEmprestimo(Emprestimo pagamento){

    }


    public void visualizarSaldoPropriedades(Jogador jogador){
        System.out.println("\nVocê escolheu visualizar Saldo e Minhas Propriedades.");


        System.out.printf("\nDinheiro disponível: $%.2f\n", jogador.getDinheiro());


        if(!jogador.getMinhasPropriedades().isEmpty()){
            Utility.propriedadesDisponiveis(jogador.getMinhasPropriedades());
            return;
        }


        System.out.println("Você não possui propriedades no momento.");
    }


    public void visualizarTodasAsPropriedades(List<Propriedade> propriedades){
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


    public List<Emprestimo> getDividasEmprestimo() {
        return dividasEmprestimo;
    }


    public void setDividasEmprestimo(List<Emprestimo> dividasEmprestimo) {
        this.dividasEmprestimo = dividasEmprestimo;
    }


    public List<Emprestimo> getEmprestimosConcebidos() {
        return emprestimosConcebidos;
    }


    public void setEmprestimosConcebidos(List<Emprestimo> emprestimosConcebidos) {
        this.emprestimosConcebidos = emprestimosConcebidos;
    }
}
