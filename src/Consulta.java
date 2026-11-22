import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta extends CitaVeterinaria {
    public static final int PRECIO = 3500;

    private String motivo;

    public Consulta(Mascota mascota, Veterinario veterinario, LocalDate fecha,
                    LocalTime inicio, String motivo) throws ArgumentoException {
        super(mascota, veterinario, fecha, inicio, duracionDe(mascota));
        this.motivo = texto(motivo, "motivo", false);
    }

    // La duración de una consulta depende de la especie de la mascota
    private static int duracionDe(Mascota mascota) throws ArgumentoException {
        if (mascota == null) throw new ArgumentoException("La mascota no puede ser nula");
        if (mascota.getEspecie() == null) throw new ArgumentoException("La especie no puede ser nula");
        return mascota.getEspecie().getDuracionConsulta();
    }

    public String getMotivo() {
        return motivo;
    }

    @Override
    public int precio() {
        return PRECIO;
    }

    @Override
    public String getTipo() {
        return "Consulta";
    }

    @Override
    public Especialidad especialidadRequerida() {
        return getMascota().getEspecie().getEspecialidad();
    }

    @Override
    public String descripcion() {
        return "Motivo: " + motivo;
    }

    @Override
    public Consulta conInicio(LocalTime nuevoInicio) throws ArgumentoException {
        return new Consulta(getMascota(), getVeterinario(), getFecha(), nuevoInicio, motivo);
    }
}
