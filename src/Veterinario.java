import java.io.Serializable;
import java.time.LocalTime;

public class Veterinario implements Serializable {

    private String codigo;
    private String nombre;
    private Especialidad especialidad;
    private Agenda agenda;

    public Veterinario(String codigo, String nombre, Especialidad especialidad) throws ArgumentoException {
        this.agenda = new Agenda(LocalTime.of(9, 0), LocalTime.of(20, 0));
        this.codigo = codigo;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public Agenda getAgenda() {
        return agenda;
    }
    public boolean puedeAtender(Especialidad requerida) {
        return requerida == Especialidad.GENERAL || requerida == especialidad;
    }

    @Override
    public String toString() {
        return nombre + " (" + especialidad + ")";
    }
}
