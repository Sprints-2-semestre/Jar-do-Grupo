package dao;

import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.Rede;
import conexao.Conexao;
import modelo.DadosComponentes;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DadosComponentesDao {
    public void salvar(Processador processador, Memoria memoria, Disco disco){//, Rede rede){
        Conexao conexao = new Conexao();
        JdbcTemplate con = conexao.getConexaoDoBanco();

        con.update("INSERT INTO dadosComponente (fkMaquina, fkTipoComponente, fkMaquinaTipoComponente, qtdUsoCpu) VALUES (?,?,?,?)", 1, 1, 1, processador.getUso());

        con.update("INSERT INTO dadosComponente (fkMaquina, fkTipoComponente, fkMaquinaTipoComponente, memoriaEmUso, memoriaDisponivel) VALUES (?,?,?,?,?)", 1, 2, 2, memoria.getEmUso(), memoria.getDisponivel());

        con.update("INSERT INTO dadosComponente (fkMaquina, fkTipoComponente, fkMaquinaTipoComponente, usoAtualDisco) VALUES (?,?,?,?)", 1, 3, 3,disco.getEscritas());

        //con.update("INSERT INTO dadosComponente (fkMaquina, fkTipoComponente, fkMaquinaTipoComponente, pacoteRecebido, pacoteEnviado) VALUES (?,?,?,?,?)", 1, 4, 4,rede.getGrupoDeInterfaces().getInterfaces().get(1).getPacotesRecebidos(), rede.getGrupoDeInterfaces().getInterfaces().get(1).getPacotesEnviados());
    }

    public List<DadosComponentes> listar() {
        Conexao conexao = new Conexao();
        JdbcTemplate con = conexao.getConexaoDoBanco();

        return con.query("SELECT * FROM dadosComponente",
                new BeanPropertyRowMapper<>(DadosComponentes.class));
    }
}
