package dao;

import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import conexao.Conexao;
import modelo.Maquina;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MaquinaDao {
    public void salvar(Sistema maquina){
        Conexao conexao = new Conexao();
        JdbcTemplate con = conexao.getConexaoDoBanco();

        con.update("INSERT INTO maquina (sistemaOperacional, arquitetura, fabricante, tempoAtividade) VALUES (?,?,?,?)",
                maquina.getSistemaOperacional(), maquina.getArquitetura(), maquina.getFabricante(), maquina.getTempoDeAtividade());
    }

    public List<Maquina> listar() {
        Conexao conexao = new Conexao();
        JdbcTemplate con = conexao.getConexaoDoBanco();

        return con.query("select * from maquina",
                new BeanPropertyRowMapper<>(Maquina.class));
    }
}
