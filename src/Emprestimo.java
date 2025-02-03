public class Emprestimo {
    private float valorEmprestimo;
    private Jogador devedor;
    private Jogador credor;
    private Propriedade garantia;





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
