package modelo;

import java.io.IOException;

public abstract class ValidacaoParametro {
    private String parametro;

    public ValidacaoParametro() {
    }

    public abstract Boolean verificarParametro(String parametro) throws IOException;

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    @Override
    public String toString() {
        return """
                Parâmetro: %s""".formatted(parametro);
    }
}
