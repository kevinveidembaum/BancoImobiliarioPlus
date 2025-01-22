import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private float dinheiro;
    private List<Propriedade> minhasPropriedades;
    private List<Emprestimo> emprestimosConcebidos;
    private List<Emprestimo> dividasEmprestimo;
    private String nome;


    public Jogador(String nome, float dinheiro){
        this.minhasPropriedades = new ArrayList<>();
        this.nome = nome;
        this.dinheiro = dinheiro;
    }


    public void comprarPropriedade(List<Propriedade> listaPropriedades, int index){
        //Verifica se o index está correto
        if (!isValidPropertyIndex(listaPropriedades, index)) {
            return;
        }

        Propriedade propriedade = listaPropriedades.get(index-1);

        if(! (this.getDinheiro() > propriedade.getValor())){
            System.out.println("Dinheiro insuficiente!");
            return;
        }


        //Se a Propriedade estiver Hipotecada e Quem está comprando não for o Dono da Hipoteca
        if(propriedade.isHipotecado() &&  !propriedade.getDono().equals(this)){
            System.out.println("Não é possível comprar uma Propriedade Hipotecada que não é Sua!");
            return;
        }


        //Se o Dono de uma Propriedade Hipoteca quer recomprá-la
        if(propriedade.isHipotecado()){
            float valorComJuros = (float) (propriedade.getValor() * 1.30);

            System.out.println("Você está comprando sua Propriedade Hipotecada.");
            System.out.printf("Juros de 30%, valor a pagar: $%.2f", valorComJuros);

            this.getMinhasPropriedades().add(propriedade);
            this.setDinheiro(this.getDinheiro() - valorComJuros);
            System.out.printf("\n%s recuperou %s, sua propriedade Hipotecada, por $%.2f!\n", this.nome, propriedade.getNome(), propriedade.getValor());
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


        //Pergunta para o donoAtual se deseja Vender sua Propriedade
        System.out.printf("\n%s, deseja Vender %s para %s?\n", donoAtual.getNome(), propriedade.getNome(), this.getNome());
        String respostaDono = InputUtility.getStringInput("[S] Sim      [N] Não\n");


        //Caso o DonoAtual não queira vender sua Propriedade
        if(respostaDono.equalsIgnoreCase("N")){
            System.out.println("\nO Proprietário não quis Vender sua Propriedade!");
            return;
        }


        //Caso o donoAtual queira vender, Jogador interessado deverá fazer uma proposta
        float proposta = InputUtility.getFloatInput("\nFaça uma Proposta para o Proprietário: $");
        System.out.printf("\n%s, Aceita a Oferta? \n", donoAtual.getNome());
        respostaDono = InputUtility.getStringInput("[S] Sim      [N] Não\n");


        //Caso o DonoAtual não aceitou a proposta feita pelo Jogador Interessado
        if(respostaDono.equalsIgnoreCase("N")){
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


    public void hipotecar(List<Propriedade> listaPropriedades, int index){
        //Verifica se o index está correto
        if (!isValidPropertyIndex(listaPropriedades, index)) {
            return;
        }


        Propriedade propriedade = listaPropriedades.get(index - 1);

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

        this.getMinhasPropriedades().remove(propriedade);
        this.setDinheiro(this.getDinheiro() + propriedade.getValor());
        propriedade.setHipotecado(true);
        listaPropriedades.add(propriedade);
        System.out.printf("\n%s foi Hipotecada pelo(a) %s por $%.2f!\n", propriedade.getNome(), this.getNome(), propriedade.getValor());
    }


    public void venderPropriedade(List<Propriedade> listaPropriedades, int index){
        //Verifica se o index está correto
        if (!isValidPropertyIndex(listaPropriedades, index)) {
            return;
        }


        //Seleciona a propriedade escolhida pelo Jogador
        Propriedade propriedade = listaPropriedades.get(index - 1);


        // Não pode vender Propriedades Hipotecadas
        if(propriedade.isHipotecado()){
            System.out.println("Não é possível vender uma Propriedade Hipotecada!");
            return;
        }


        //75% do valor da Propriedade
        float valorVenda = (float) (propriedade.getValor() * 0.75);


        //A logica da venda em si
        this.setDinheiro(this.getDinheiro() + valorVenda);
        this.getMinhasPropriedades().remove(propriedade);
        propriedade.setDono(null);
        listaPropriedades.add(propriedade);
        System.out.printf("\n%s vendeu a propriedade %s por $%.2f!\n", this.getNome(), propriedade.getNome(), valorVenda);
    }


    public void pagarAluguel(Propriedade propriedade, Jogador dono){
        if(this.getDinheiro() < propriedade.getValorAluguel()){
            System.out.println("Dinheiro Insuficiente para Pagar Aluguel!");
            return;
        }

        this.setDinheiro(this.getDinheiro() - propriedade.getValorAluguel());
        dono.setDinheiro(dono.getDinheiro() + propriedade.getValorAluguel());
    }


    public void fazerEmprestimo(Jogador credor, float valorEmprestar, Propriedade garantia){

    }


    public void pagarEmprestimo(Emprestimo emprestimo, float valorPagar){

    }


    public void receberPagamentoEmprestimo(Emprestimo pagamento){

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
