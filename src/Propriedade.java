public class Propriedade {
    private float valor;
    private Jogador dono;
    private boolean hipotecado;
    private boolean empresa;
    private int qntCasas;
    private float valorCasa;
    private float valorAluguel;
    private boolean hotel;
    private String nome;

    public Propriedade(String nome, float valor, boolean isEmpresa){
        this.nome = nome;
        this.valor = valor;
        this.empresa = isEmpresa;


        // Empresas não podem ter construções nem valor de aluguel
        if(!isEmpresa){
            this.valorCasa = generateRandomNumber(0, 50) + this.valor;
            this.valorAluguel = calcularAluguel();
        }
    }

    private float calcularAluguel(){
        /*  Regras para Cálculo Aluguel
        *
        *   Aluguel (Sem casas) = até 25% do valor da Propriedade
        *   1 Casa = valor da Propriedade - (até $300)
        *   2 Casas = x3 o valor de 1 casa, - (até $300)
        *   3 Casas = x2 o valor de 2 casas + (até $200) OU x3 valor de 2 casas
        *   4 Casas = valor de 3 casas + (até $300)
        *   Hotel = valor de 3 casas + (até $300)
        */

        //TODO adicionar Empresas aluguel(multiplica pelo valor do dado)


        float aluguel = (generateRandomNumber(5, 25) / 100.0f) * valor;

        if (this.isHotel()) {
            // Aluguel Hotel
            return aluguel = 2 * (aluguel + generateRandomNumber(0, 300));
        }


        for (int i = 1; i <=  this.getQntCasas(); i++) {
            if (i == 1) {
                // Aluguel para 1 casas
                aluguel = valor - generateRandomNumber(100, 300);
            } else if (i == 2) {
                // Aluguel para 2 casas
                aluguel = aluguel * 3 - generateRandomNumber(100, 300);
            } else if (i == 3) {
                // Aluguel para 3 casas
                aluguel = aluguel * 2 + generateRandomNumber(0, 200);
            } else if (i == 4) {
                // Aluguel para 4 casas
                aluguel = aluguel + generateRandomNumber(0, 300);
            }

            return aluguel;
        }


        //aluguel não pode ser negativo
        return Math.max(aluguel, 0);
    }


    // Calculo da Taxa de cada Empresa
    public float calcularTaxa(int numDado){
        float multiplicador = generateRandomNumber(40, 50);

        return multiplicador * numDado;
    }


    // Metodo para gerar números aleatórios, vai ser usado para calcular aluguel e valor Casas.
    private int generateRandomNumber(float min, float max) {
        return (int) (Math.random() * (max - min) + min);
    }


    //Apenas mostrar informações das Propriedades
    public void inspecionar() {
        System.out.printf("\n%s => Qntd. Casas: %d | Hotel: %s\n",
                this.getNome(),
                this.getQntCasas(),
                this.isHotel() ? "Sim" : "Não");
    }





    public boolean isHotel() {
        return hotel;
    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
    }

    public float getValorCasa() {
        return valorCasa;
    }

    public void setValorCasa(float valorCasa) {
        this.valorCasa = valorCasa;
    }

    public boolean isEmpresa() {
        return empresa;
    }

    public void setEmpresa(boolean empresa) {
        this.empresa = empresa;
    }

    public Jogador getDono() {
        return dono;
    }

    public void setDono(Jogador dono) {
        this.dono = dono;
    }

    public boolean isHipotecado() {
        return hipotecado;
    }

    public void setHipotecado(boolean hipotecado) {
        this.hipotecado = hipotecado;
    }

    public int getQntCasas() {
        return qntCasas;
    }

    public void setQntCasas(int qntCasas) {
        this.qntCasas = qntCasas;
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
