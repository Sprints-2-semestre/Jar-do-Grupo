package dao;

import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import conexao.Conexao;
import modelo.Maquina;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MaquinaDao {
    Sistema maquina = new Sistema();
    Processador processador = new Processador();
    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();
    public void salvar() {
        con.update("INSERT INTO maquina (sistemaOperacional, arquitetura, fabricante, tempoAtividade, idMaquina) VALUES (?, ?, ?, ?, ?);",
                maquina.getSistemaOperacional(), maquina.getArquitetura(),
                maquina.getFabricante(), maquina.getTempoDeAtividade(),
                processador.getId());
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
