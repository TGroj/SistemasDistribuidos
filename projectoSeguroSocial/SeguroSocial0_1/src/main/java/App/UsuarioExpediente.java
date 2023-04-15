package App;

public class UsuarioExpediente {
    private final String nombre;
    private final String tipoSangre;
    private final String unidadSeguro;

    public UsuarioExpediente(String nombre, String tipoSangre, String unidadSeguro) {
        this.nombre = nombre;
        this.tipoSangre = tipoSangre;
        this.unidadSeguro = unidadSeguro;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public String getUnidadSeguro() {
        return unidadSeguro;
    }

    @Override
    public String toString() {
        return "{" +
                "nombre:'" + nombre + '\'' +
                ", tipoSangre:'" + tipoSangre + '\'' +
                ", unidadSeguro:'" + unidadSeguro + '\'' +
                '}';
    }
}
