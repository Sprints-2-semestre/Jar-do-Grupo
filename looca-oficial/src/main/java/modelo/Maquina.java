package modelo;

public class Maquina {
    private Integer idMaquina;
    private String sistemaOperacional;
    private Integer arquitetura;
    private String fabricante;
    private String tempoAtividade;
    public Maquina(String sistemaOperacional, Integer arquitetura, String fabricante, String tempoAtividade) {
        idMaquina = null;
        this.sistemaOperacional = sistemaOperacional;
        this.arquitetura = arquitetura;
        this.fabricante = fabricante;
        this.tempoAtividade = tempoAtividade;

    }

    public Maquina() {
    }

    public Integer getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Integer idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public Integer getArquitetura() {
        return arquitetura;
    }

    public void setArquitetura(Integer arquitetura) {
        this.arquitetura = arquitetura;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getTempoAtividade() {
        return tempoAtividade;
    }

    public void setTempoAtividade(String tempoAtividade) {
        this.tempoAtividade = tempoAtividade;
    }


    @Override
    public String toString() {
        return """
                Id Máquina: %d
                Sistema Opercaional: %s
                Arquitetura: %d
                Fabricante: %s
                Tempo Atividade: %s
               
                """.formatted(idMaquina, sistemaOperacional, arquitetura, fabricante, tempoAtividade);
    }
}
