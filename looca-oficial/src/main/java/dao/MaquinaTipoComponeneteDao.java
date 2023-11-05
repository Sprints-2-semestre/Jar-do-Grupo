package dao;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import conexao.Conexao;
import modelo.MaquinaTipoComponente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class MaquinaTipoComponeneteDao {
    Looca looca = new Looca();


    // Captura da fkMaquina;
    private String fkMaquina = looca.getProcessador().getId();


    // Capturas da Máquina
    private String idProcessador = looca.getProcessador().getId();
    private Integer qtdCpuLogico = looca.getProcessador().getNumeroCpusLogicas();
    private Integer qtdCpuFisico = looca.getProcessador().getNumeroCpusFisicas();


    // Capturas de RAM;
    private Long totalRam = looca.getMemoria().getTotal();


    // Capturas de Disco;
    private String numSerial = looca.getGrupoDeDiscos().getDiscos().get(0).getSerial();
    private Long totalDisco = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal();


    // Capturas de Rede;
    List<RedeInterface> redes = looca.getRede().getGrupoDeInterfaces().getInterfaces();

    // Captura de Processos
    List<Processo> processos = looca.getGrupoDeProcessos().getProcessos();

    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();
    public void salvar() {
        con.update("INSERT INTO maquinaTipoComponente (num_ProcesLogicos, num_ProcesFisicos, fkMaquina) VALUES (?, ?, ?);", qtdCpuLogico, qtdCpuFisico, fkMaquina);

        con.update("INSERT INTO maquinaTipoComponente (tamanhoTotalRam, fkMaquina) VALUES (?, ?);", totalRam, fkMaquina);

        con.update("INSERT INTO maquinaTipoComponente (numSerial, tamanhoTotalDisco, fkMaquina) VALUES (?, ?, ?);", numSerial, totalDisco, fkMaquina);

        for (int i = 0; i < redes.size(); i++) {
            if (redes.get(i).getBytesRecebidos() > 0.0 && redes.get(i).getBytesEnviados() > 0.0) {
                con.update("INSERT INTO maquinaTipoComponente (enderecoMac, ipv4, fkMaquina) VALUES (?, ?, ?);", redes.get(i).getEnderecoMac(), redes.get(i).getEnderecoIpv4().toString(), fkMaquina);
                Long bytesRecebido = (looca.getRede().getGrupoDeInterfaces().getInterfaces().get(i).getBytesRecebidos() / 10000);
                BigDecimal br = new BigDecimal(BigInteger.valueOf(bytesRecebido), 2);

                Long bytesEnviado = (looca.getRede().getGrupoDeInterfaces().getInterfaces().get(i).getBytesEnviados() / 10000);
                BigDecimal be = new BigDecimal(BigInteger.valueOf(bytesEnviado), 2);
                    con.update("INSERT INTO dadosComponente (bytesRecebido, bytesEnviado, fkMaquina) VALUES (?, ?, ?);", br, be, fkMaquina);
                break;
            }
        }

        for (int i = 0; i < processos.size(); i++) {
            if (processos.get(i).getUsoCpu() > 0.1 && processos.get(i).getUsoMemoria() > 0.1) {
                String ramProcessoFormatado = String.format("%.1f", processos.get(i).getUsoMemoria());
                ramProcessoFormatado = ramProcessoFormatado.replace(",", ".");

                String cpuProcessoFormatado = String.format("%.1f", processos.get(i).getUsoCpu());
                cpuProcessoFormatado = cpuProcessoFormatado.replace(",", ".");


                con.update("INSERT INTO maquinaTipoComponente (pid, nomeProcesso, fkMaquina) VALUES (?, ?, ?)", processos.get(i).getPid(), processos.get(i).getNome(), fkMaquina);
                con.update("INSERT INTO dadosComponente (qtdMemoriaProcesso, qtdProcessadorProcesso, fkMaquina) VALUES (?, ?, ?)", ramProcessoFormatado, cpuProcessoFormatado, fkMaquina);
            }
        }
    }

    public void listar() {
        System.out.println(con.query("""
                        select maquinaTipoComponente.idProcessador,
                        maquinaTipoComponente.num_ProcesLogicos,
                        maquinaTipoComponente.num_ProcesFisicos,
                        maquinaTipoComponente.tamanhoTotalRam,
                        maquinaTipoComponente.numSerial,
                        maquinaTipoComponente.enderecoMac,
                        maquinaTipoComponente.ipv4
                        from maquinaTipoComponente
                        where fkMaquina = 1;""",
                new BeanPropertyRowMapper<>(MaquinaTipoComponente.class)));
    }
}
