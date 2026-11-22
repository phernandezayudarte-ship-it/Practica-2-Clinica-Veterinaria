import java.time.LocalDate;
import java.time.LocalTime;

public abstract class CitaVeterinaria extends Cita {
    private Mascota mascota;
    private Veterinario veterinario;
    private Resultado resultado;
    private String notas;

    protected CitaVeterinaria(Mascota mascota, Veterinario veterinario, LocalDate fecha,
                              LocalTime inicio, int duracion) throws ArgumentoException {
        super(idDe(mascota), fecha, inicio, duracion);
        if (veterinario == null) throw new ArgumentoException("El veterinario no puede ser nulo");
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.resultado = null;
        this.notas = "";
    }

    // El cliente de la cita es el identificador de la mascota (apartado 3)
    private static String idDe(Mascota mascota) throws ArgumentoException {
        if (mascota == null) throw new ArgumentoException("La mascota no puede ser nula");
        return mascota.getId();
    }

    protected static String texto(String s, String que, boolean vacioPermitido) throws ArgumentoException {
        if (s == null) throw new ArgumentoException("Valor nulo en: " + que);
        String limpio = s.trim();
        if (!vacioPermitido && limpio.isEmpty()) throw new ArgumentoException("No puede estar en blanco: " + que);
        return limpio;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public String getNotas() {
        return notas;
    }

    public abstract int precio();

    public abstract String getTipo();

    public abstract Especialidad especialidadRequerida();

    public abstract String descripcion();

    protected String camposComunes() {
        return getFecha() + " " + getInicio() + "-" + getFin() + " | " + mascota
                + " | " + veterinario.getNombre();
    }

    @Override
    public String toString() {
        String estado = (resultado == null) ? "PENDIENTE" : resultado.toString();
        String s = getTipo() + " " + camposComunes() + " | " + descripcion() + " [" + estado + "]";
        if (!notas.isEmpty()) {
            s += " Notas: " + notas;
        }
        return s;
    }
}
