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
}
