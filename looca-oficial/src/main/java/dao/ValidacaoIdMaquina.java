package dao;

import conexao.Conexao;
import modelo.ValidacaoParametro;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ValidacaoIdMaquina extends ValidacaoParametro {

    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();

    public ValidacaoIdMaquina() {
    }

    @Override
    public Boolean verificarParametro(String parametro) {
        List<String> idMaquinaExistentes;

        idMaquinaExistentes = con.queryForList("SELECT idMaquina FROM maquina;", String.class);
        for(int i = 0; i < idMaquinaExistentes.size(); i++){
            if(idMaquinaExistentes.get(i).equals(parametro)){
                return true;
            }
        }
        return false;
    }
}
