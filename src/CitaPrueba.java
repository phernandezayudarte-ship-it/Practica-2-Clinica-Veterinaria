import java.time.LocalDate;
import java.time.LocalTime;

public class CitaPrueba extends Cita {
    public CitaPrueba(String cliente, LocalDate fecha, LocalTime inicio, int duracion)
            throws ArgumentoException {
        super(cliente, fecha, inicio, duracion);
    }

    @Override
    public Cita conInicio(LocalTime nuevoInicio) throws ArgumentoException {
        return new CitaPrueba(getCliente(), getFecha(), nuevoInicio, getDuracion());
    }
}
