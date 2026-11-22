import java.io.Serializable;
import java.time.LocalDate;

public class Mascota implements Serializable {

    private String id;
    private String nombre;
    private String propietario;
    private Especie especie;
    private LocalDate nacimiento;
    private int peso;

    public Mascota(String id, String nombre, String propietario, Especie especie, LocalDate nacimiento, int peso) {
        this.id = id;
        this.nombre = nombre;
        this.propietario = propietario;
        this.especie = especie;
        this.nacimiento = nacimiento;
        this.peso = peso;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPropietario() {
        return propietario;
    }

    public Especie getEspecie() {
        return especie;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public int getPeso() {
        return peso;
    }
}
