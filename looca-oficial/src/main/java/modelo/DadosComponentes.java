package modelo;

public class DadosComponentes {
    private Integer idDadosComponentes;
    private Integer fkMaquina;
    private Integer fkTipoComponente;
    private Integer fkMaquinaTipoComponente;
    private Double qtdUsoCpu;
    private Double memoriaEmUso;
    private Double memoriaDisponivel;
    private Double usoAtualDisco;
    private Double pacoteRecebido;
    private Double pacoteEnviado;

    public DadosComponentes(Integer fkMaquina, Integer fkTipoComponente, Integer fkMaquinaTipoComponente, Double qtdUsoCpu, Double memoriaEmUso, Double memoriaDisponivel, Double usoAtualDisco, Double pacoteRecebido, Double pacoteEnviado) {
        idDadosComponentes = null;
        this.fkMaquina = fkMaquina;
        this.fkTipoComponente = fkTipoComponente;
        this.fkMaquinaTipoComponente = fkMaquinaTipoComponente;
        this.qtdUsoCpu = qtdUsoCpu;
        this.memoriaEmUso = memoriaEmUso;
        this.memoriaDisponivel = memoriaDisponivel;
        this.usoAtualDisco = usoAtualDisco;
        this.pacoteRecebido = pacoteRecebido;
        this.pacoteEnviado = pacoteEnviado;
    }

    public DadosComponentes() {
    }

    public Integer getIdDadosComponentes() {
        return idDadosComponentes;
    }

    public void setIdDadosComponentes(Integer idDadosComponentes) {
        this.idDadosComponentes = idDadosComponentes;
    }

    public Integer getFkMaquina() {
        return fkMaquina;
    }

    public void setFkMaquina(Integer fkMaquina) {
        this.fkMaquina = fkMaquina;
    }

    public Integer getFkTipoComponente() {
        return fkTipoComponente;
    }

    public void setFkTipoComponente(Integer fkTipoComponente) {
        this.fkTipoComponente = fkTipoComponente;
    }

    public Integer getFkMaquinaTipoComponente() {
        return fkMaquinaTipoComponente;
    }

    public void setFkMaquinaTipoComponente(Integer fkMaquinaTipoComponente) {
        this.fkMaquinaTipoComponente = fkMaquinaTipoComponente;
    }

    public Double getQtdUsoCpu() {
        return qtdUsoCpu;
    }

    public void setQtdUsoCpu(Double qtdUsoCpu) {
        this.qtdUsoCpu = qtdUsoCpu;
    }

    public Double getMemoriaEmUso() {
        return memoriaEmUso;
    }

    public void setMemoriaEmUso(Double memoriaEmUso) {
        this.memoriaEmUso = memoriaEmUso;
    }

    public Double getMemoriaDisponivel() {
        return memoriaDisponivel;
    }

    public void setMemoriaDisponivel(Double memoriaDisponivel) {
        this.memoriaDisponivel = memoriaDisponivel;
    }

    public Double getUsoAtualDisco() {
        return usoAtualDisco;
    }

    public void setUsoAtualDisco(Double usoAtualDisco) {
        this.usoAtualDisco = usoAtualDisco;
    }

    public Double getPacoteRecebido() {
        return pacoteRecebido;
    }

    public void setPacoteRecebido(Double pacoteRecebido) {
        this.pacoteRecebido = pacoteRecebido;
    }

    public Double getPacoteEnviado() {
        return pacoteEnviado;
    }

    public void setPacoteEnviado(Double pacoteEnviado) {
        this.pacoteEnviado = pacoteEnviado;
    }

    @Override
    public String toString() {

        return """
                Id Dados Componentes: %d
                Fk Maquina: %d
                Fk Tipo Componente: %d
                Fk Maquina Tipo Componente: %d
                Quantidade de Uso CPU: %.4f
                Memória em Uso: %.4f
                Memória Disponível: %.4f
                Uso Atual do Disco: %.4f
                Pacote Recebido: %.4f
                Pacote Enviado: %.4f
                """.formatted(idDadosComponentes, fkMaquina, fkTipoComponente, fkMaquinaTipoComponente, qtdUsoCpu, memoriaEmUso, memoriaDisponivel, usoAtualDisco, pacoteRecebido, pacoteEnviado);
    }
}


