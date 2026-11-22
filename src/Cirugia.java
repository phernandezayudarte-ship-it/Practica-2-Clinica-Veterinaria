import java.time.LocalDate;
import java.time.LocalTime;

public class Cirugia extends CitaVeterinaria {
    public static final int PRECIO_HORA = 9000;
    public static final int ANESTESIA_POR_KG = 150;
    public static final LocalTime FIN_MANANA = LocalTime.of(14, 0);
    public static final int DURACION_MINIMA = 30;
    public static final int DURACION_MAXIMA = 240;

    private String intervencion;

    public Cirugia(Mascota mascota, Veterinario veterinario, LocalDate fecha,
                   LocalTime inicio, int duracion, String intervencion) throws ArgumentoException {
        super(mascota, veterinario, fecha, inicio, duracion);
        this.intervencion = texto(intervencion, "intervención", false);
    }

    public String getIntervencion() {
        return intervencion;
    }

    @Override
    public int precio() {
        // El peso está en gramos: se divide entre 1000 para pasarlo a kilos
        return PRECIO_HORA * getDuracion() / 60 + ANESTESIA_POR_KG * getMascota().getPeso() / 1000;
    }

    @Override
    public String getTipo() {
        return "Cirugía";
    }

    @Override
    public Especialidad especialidadRequerida() {
        return Especialidad.CIRUGIA;
    }

    @Override
    public String descripcion() {
        return "Intervención: " + intervencion;
    }

    @Override
    public Cirugia conInicio(LocalTime nuevoInicio) throws ArgumentoException {
        return new Cirugia(getMascota(), getVeterinario(), getFecha(), nuevoInicio, getDuracion(), intervencion);
    }
}
