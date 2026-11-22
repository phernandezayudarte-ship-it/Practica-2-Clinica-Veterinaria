import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public final void comprobarNormas(LocalDateTime ahora) throws ClinicaException, ArgumentoException {
        if (ahora == null) throw new ArgumentoException("La fecha y hora actual no puede ser nula");

        if (getFecha().getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new ClinicaException("La clínica cierra los domingos");
        }

        int apertura = enMinutos(veterinario.getAgenda().getApertura());
        int cierre = enMinutos(veterinario.getAgenda().getCierre());
        int inicio = enMinutos(getInicio());
        if (inicio < apertura || inicio + getDuracion() > cierre) {
            throw new ClinicaException("La cita debe empezar y terminar dentro del horario de "
                    + veterinario.getAgenda().getApertura() + " a " + veterinario.getAgenda().getCierre());
        }

        if (LocalDateTime.of(getFecha(), getInicio()).isBefore(ahora)) {
            throw new ClinicaException("No se puede programar una cita en el pasado");
        }

        if (!veterinario.puedeAtender(especialidadRequerida())) {
            throw new ClinicaException(veterinario.getNombre() + " no tiene la especialidad exigida ("
                    + especialidadRequerida() + ")");
        }

        comprobarNormasPropias(ahora);
    }

    protected void comprobarNormasPropias(LocalDateTime ahora) throws ClinicaException {
        // Por defecto no hay normas propias; Cirugia lo redefine
    }

    // Minutos desde las 00:00, para que una cita que pase de medianoche no parezca acabar pronto
    private static int enMinutos(LocalTime h) {
        return h.getHour() * 60 + h.getMinute();
    }

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
