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
    private float multiplicador;
    private boolean garantia;

    //Armazenando números aleatórios para calculo de aluguel
    final int RANDOM_1 = generateRandomNumber(100, 200); // Instead of 100-300
    final int RANDOM_2 = generateRandomNumber(50, 150);
    final int RANDOM_3 = generateRandomNumber(100, 300);  // Instead of 0-300


    public Propriedade(String nome, float valor, boolean isEmpresa){
        this.nome = nome;
        this.valor = valor;
        this.empresa = isEmpresa;


        // Empresas não podem ter construções nem valor de aluguel
        if(!isEmpresa){
            this.valorCasa = generateRandomNumber(0, 50) + this.valor;
        }else{
            //Porém somente empresas possuem multiplicadores de taxas
            this.multiplicador = generateRandomNumber(40, 50);
        }
    }


    public float calcularAluguel(){
        /*  Regras para Cálculo Aluguel
        *
        *   Aluguel (Sem casas) = até 25% do valor da Propriedade
        *   1 Casa = valor da Propriedade - (até 25% do valor da Propriedade)
        *   2 Casas = 150% preço de 1 Casas + (até $200)
        *   3 Casas = 150% preço de 2 Casas + (até $150)
        *   4 Casas = valor de 3 Casas + (mínimo $100 até $300)
        *   Hotel = 150% preço de 4 Casas
        */


        float aluguel;
        float aluguelBase = (generateRandomNumber(5, 25) / 100.0f) * this.getValor();
        aluguel = aluguelBase;


        //Loop para atribuição de Aluguel acumulativo
        if(this.getQntCasas() >= 1){
            for(int i=1; i <= this.getQntCasas(); i++){
                switch (i) {
                    case 1 -> aluguel = this.getValor() - aluguelBase;
                    case 2 -> aluguel = (float) ((1.5 * aluguel) + RANDOM_1);
                    case 3 -> aluguel = (float) ((1.5 * aluguel) + RANDOM_2);
                    case 4 -> aluguel = aluguel + RANDOM_3;
                }
            }
        }


        if (this.isHotel()) {
            // Aluguel Hotel
            aluguel = (float) (1.5 * aluguel);
        }


        //aluguel não pode ser negativo
        return Math.max(aluguel, 0);
    }


    // Calculo da Taxa de cada Empresa
    public float calcularTaxa(int numDado){
        return this.getMultiplicador() * numDado;
    }


    // Metodo para gerar números aleatórios, vai ser usado para calcular aluguel e valor Casas.
    private int generateRandomNumber(float min, float max) {
        return (int) (Math.random() * (max - min) + min);
    }


    //Apenas mostrar informações das Propriedades
    public void inspecionar() {
        //Se for Empresa
        if(this.isEmpresa()){
            System.out.printf("\n%s => Empresa: %s | Valor do Multiplicador: %.2f\n",
                    this.getNome(),
                    this.isEmpresa() ? "Sim" : "Não",
                    this.getMultiplicador());

            return;
        }


        //Propriedades normais
        System.out.printf("\n%s => Qntd. Casas: %d | Hotel: %s\n",
                this.getNome(),
                this.getQntCasas(),
                this.isHotel() ? "Sim" : "Não");
    }

    public boolean isGarantia() {
        return garantia;
    }

    public void setGarantia(boolean garantia) {
        this.garantia = garantia;
    }

    public float getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(float multiplicador) {
        this.multiplicador = multiplicador;
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
        return calcularAluguel();
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
