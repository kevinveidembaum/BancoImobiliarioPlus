public class Propriedade {
    private float valor;
    private float valorAluguel;
    private String nome;

    public Propriedade(String nome, float valor){
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValorAluguel() {
        return valorAluguel;
    }


    public void setValorAluguel(float valorAluguel) {
        this.valorAluguel = valorAluguel;
    }


    public float getValor() {
        return valor;
    }


    public void setValor(float valor) {
        this.valor = valor;
    }
}
