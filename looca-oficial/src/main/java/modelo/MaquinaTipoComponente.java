package modelo;

public class MaquinaTipoComponente {
    private Integer idMaqTipoComp;
    private Integer fkMaquina;
    private Integer fkTipoComp;
    private String idProcessador;
    private Integer num_ProcesLogicos;
    private Integer num_ProcesFisicos;
    private Double tamanhoTotal;
    private String enderecoMac;
    private String numSerial;
    private String ipv4;

    public MaquinaTipoComponente(Integer fkMaquina, Integer fkTipoComp, String idProcessador, Integer num_ProcesLogicos, Integer num_ProcesFisicos, Double tamanhoTotal, String enderecoMac, String numSerial, String ipv4) {
        idMaqTipoComp = null;
        this.fkMaquina = fkMaquina;
        this.fkTipoComp = fkTipoComp;
        this.idProcessador = idProcessador;
        this.num_ProcesLogicos = num_ProcesLogicos;
        this.num_ProcesFisicos = num_ProcesFisicos;
        this.tamanhoTotal = tamanhoTotal;
        this.enderecoMac = enderecoMac;
        this.numSerial = numSerial;
        this.ipv4 = ipv4;
    }

    public MaquinaTipoComponente() {
    }

    public Integer getIdMaqTipoComp() {
        return idMaqTipoComp;
    }

    public void setIdMaqTipoComp(Integer idMaqTipoComp) {
        this.idMaqTipoComp = idMaqTipoComp;
    }

    public Integer getFkMaquina() {
        return fkMaquina;
    }

    public void setFkMaquina(Integer fkMaquina) {
        this.fkMaquina = fkMaquina;
    }

    public Integer getFkTipoComp() {
        return fkTipoComp;
    }

    public void setFkTipoComp(Integer fkTipoComp) {
        this.fkTipoComp = fkTipoComp;
    }

    public String getIdProcessador() {
        return idProcessador;
    }

    public void setIdProcessador(String idProcessador) {
        this.idProcessador = idProcessador;
    }

    public Integer getNum_ProcesLogicos() {
        return num_ProcesLogicos;
    }

    public void setNum_ProcesLogicos(Integer num_ProcesLogicos) {
        this.num_ProcesLogicos = num_ProcesLogicos;
    }

    public Integer getNum_ProcesFisicos() {
        return num_ProcesFisicos;
    }

    public void setNum_ProcesFisicos(Integer num_ProcesFisicos) {
        this.num_ProcesFisicos = num_ProcesFisicos;
    }

    public Double getTamanhoTotal() {
        return tamanhoTotal;
    }

    public void setTamanhoTotal(Double tamanhoTotal) {
        this.tamanhoTotal = tamanhoTotal;
    }

    public String getEnderecoMac() {
        return enderecoMac;
    }

    public void setEnderecoMac(String enderecoMac) {
        this.enderecoMac = enderecoMac;
    }

    public String getNumSerial() {
        return numSerial;
    }

    public void setNumSerial(String numSerial) {
        this.numSerial = numSerial;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    @Override
    public String toString() {
        return """
                Id Maquina Tipo Componente: %d
                Fk Maquina: %d
                Fk Tipo Componente: %d
                Id Processador: %s
                Número Processadores Lógicos: %d
                Número Processadores Físicos: %d
                Tamanho Total: %.4f
                Endereço Mac: %s
                Número Serial: %s
                Ipv4: %s
                """.formatted(idMaqTipoComp, fkMaquina, fkTipoComp, idProcessador, num_ProcesLogicos, num_ProcesFisicos, tamanhoTotal, enderecoMac, numSerial, ipv4);
    }
}
