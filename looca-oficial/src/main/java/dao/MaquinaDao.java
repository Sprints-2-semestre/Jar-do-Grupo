package dao;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.group.sistema.Sistema;
import conexao.Conexao;
import conexao.ConexaoSlack;
import modelo.Maquina;
import modelo.MaquinaTipoComponente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Class.forName;

public class MaquinaDao {
    Looca looca = new Looca();
    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();

    private String idMaquina = looca.getProcessador().getId();
    private String sistemaOperacional = looca.getSistema().getSistemaOperacional();
    private Integer arquitetura = looca.getSistema().getArquitetura();
    private String fabricante = looca.getProcessador().getFabricante();
    private Long tempoAtividade = looca.getSistema().getTempoDeAtividade();

    List<RedeInterface> redes = new ArrayList<>();

    ConexaoSlack conexaoSlack = new ConexaoSlack();
    ValidacaoIdMaquina validIdMaquina = new ValidacaoIdMaquina();
    MaquinaTipoComponenteDao maquinaTipoComponenteDao = new MaquinaTipoComponenteDao();

    public void salvar(){
        Boolean existeIdMaquina = validIdMaquina.verificarParametro(idMaquina);

        if(existeIdMaquina){
            maquinaTipoComponenteDao.salvar();

            Double usoCpu = looca.getProcessador().getUso();
//            if (usoCpu >= parametroPreocupante && usoCpu < parametroCritico) {
//                conexaoSlack.enviarMensagemCpu(usoCpu);
//            }

            // Captura do uso de RAM;
            Long usoRam = looca.getMemoria().getEmUso();
            Double divisaoUsoRam = (usoRam / 1000000000.0);
            String usoRamFormatado = String.format("%.1f", divisaoUsoRam);
            StringBuilder s = new StringBuilder(usoRamFormatado);
            s.setCharAt(1, '.');


//            if (s >= parametroRamPreocupante && s < parametroRamCritico) {
//                conexaoSlack.enviarMensagemRam(s);
//            }

            Long ramDisponivel = looca.getMemoria().getDisponivel();
            Double divisaoRamDisponivel = (ramDisponivel / 1000000000.0);
            String ramDisponivelFormatado = String.format("%.1f", divisaoRamDisponivel);
            StringBuilder sb = new StringBuilder(ramDisponivelFormatado);
            sb.setCharAt(1, '.');


            // Captura do uso de DISCO;
            Long tamanhoTotalDisco = (looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() / 1000 / 1000 / 1000);
            Long tamanhoDisponivel = (looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel() / 1000 / 1000 / 1000);

            con.update("INSERT INTO dadosComponente (qtdUsoCpu, fkMaquina, fkTipoComponente) VALUES (?, ?, ?);", usoCpu, idMaquina, 1);

            con.update("INSERT INTO dadosComponente (memoriaEmUso, memoriaDisponivel, fkMaquina, fkTipoComponente) VALUES (?, ?, ?, ?);", s, sb, idMaquina, 2);

            con.update("INSERT INTO dadosComponente (usoAtualDisco, usoDisponivelDisco, fkMaquina, fkTipoComponente) VALUES (?, ?, ?, ?);", (tamanhoTotalDisco - tamanhoDisponivel), tamanhoDisponivel, idMaquina, 3);

        } else{
            con.update("INSERT INTO maquina (idMaquina, sistemaOperacional, arquitetura, fabricante, tempoAtividade) VALUES (?, ?, ?, ?, ?);",
                    idMaquina, sistemaOperacional, arquitetura,
                    fabricante, tempoAtividade);

            maquinaTipoComponenteDao.salvar();

            Double usoCpu = looca.getProcessador().getUso();

            // Captura do uso de RAM;
            Long usoRam = looca.getMemoria().getEmUso();
            Double divisaoUsoRam = (usoRam / 1000000000.0);
            String usoRamFormatado = String.format("%.1f", divisaoUsoRam);
            StringBuilder s = new StringBuilder(usoRamFormatado);
            s.setCharAt(1, '.');

            Long ramDisponivel = looca.getMemoria().getDisponivel();
            Double divisaoRamDisponivel = (ramDisponivel / 1000000000.0);
            String ramDisponivelFormatado = String.format("%.1f", divisaoRamDisponivel);
            StringBuilder sb = new StringBuilder(ramDisponivelFormatado);
            sb.setCharAt(1, '.');


            // Captura do uso de DISCO;
            Long tamanhoTotalDisco = (looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() / 1000 / 1000 / 1000);
            Long tamanhoDisponivel = (looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel() / 1000 / 1000 / 1000);

            con.update("INSERT INTO dadosComponente (qtdUsoCpu, fkMaquina, fkTipoComponente) VALUES (?, ?, ?);", usoCpu, idMaquina, 1);

            con.update("INSERT INTO dadosComponente (memoriaEmUso, memoriaDisponivel, fkMaquina, fkTipoComponente) VALUES (?, ?, ?, ?);", s, sb, idMaquina, 2);

            con.update("INSERT INTO dadosComponente (usoAtualDisco, usoDisponivelDisco, fkMaquina, fkTipoComponente) VALUES (?, ?, ?, ?);", (tamanhoTotalDisco - tamanhoDisponivel), tamanhoDisponivel, idMaquina, 3);
        }
    }

    public void listar() {
        System.out.println(con.query("""
                        select maquina.sistemaOperacional,
                        maquina.arquitetura,
                        maquina.fabricante,
                        maquina.tempoAtividade
                        from maquina;""",
                new BeanPropertyRowMapper<>(Maquina.class)));
    }
}