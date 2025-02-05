public class Emprestimo {
    private float valorEmprestimo;
    private Jogador devedor;
    private Jogador credor;
    private Propriedade garantia;
    private int prazoFinal;

    public Emprestimo(Jogador credor, Jogador devedor, float valorEmprestimo, Propriedade garantia){
        this.devedor = devedor;
        this.credor = credor;
        this.valorEmprestimo = valorEmprestimo;
        this.garantia = garantia;
        this.prazoFinal = 5;
    }


    //Logica de transações
    public void processar(){
        // Tranferencia de Dinheiro
        credor.setDinheiro(credor.getDinheiro() - valorEmprestimo);
        devedor.setDinheiro(devedor.getDinheiro() + valorEmprestimo);


        // Marca a Propriedade como Garantia para Credor
        garantia.setDono(credor);
        garantia.setGarantia(true);


        System.out.println("\nEmpréstimo processado com sucesso!");
        System.out.printf("%s emprestou $%.2f para %s usando %s como garantia.\n",
                credor.getNome(), valorEmprestimo, devedor.getNome(), garantia.getNome());
    }


    //Calculo Prazo
    public void calcularPrazo(){
        if(prazoFinal > 0){
            prazoFinal--;
        }
    }


    //Verifica se o Prazo não estourou
    public boolean verificarPrazoEstourou(){
        return prazoFinal <= 0;
    }


    public int getPrazoFinal() {
        return prazoFinal;
    }

    public void setPrazoFinal(int prazoFinal) {
        this.prazoFinal = prazoFinal;
    }

    public float getValorEmprestimo() {
        return valorEmprestimo;
    }

    public void setValorEmprestimo(float valorEmprestimo) {
        this.valorEmprestimo = valorEmprestimo;
    }

    public Jogador getDevedor() {
        return devedor;
    }

    public void setDevedor(Jogador devedor) {
        this.devedor = devedor;
    }

    public Propriedade getGarantia() {
        return garantia;
    }

    public void setGarantia(Propriedade garantia) {
        this.garantia = garantia;
    }

    public Jogador getCredor() {
        return credor;
    }

    public void setCredor(Jogador credor) {
        this.credor = credor;
    }
}
