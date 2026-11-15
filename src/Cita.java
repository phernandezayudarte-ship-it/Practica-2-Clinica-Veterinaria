import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
public abstract class Cita implements Serializable {
    private String cliente;
    private LocalDate fecha;
    private LocalTime inicio;
    private int duracion;

    protected Cita(String cliente, LocalDate fecha, LocalTime inicio, int duracion)
            throws ArgumentoException {
        if(cliente==null|| cliente.isEmpty())throw new ArgumentoException("Cliente no puede ser null ni vacio");
        if(fecha==null)throw new ArgumentoException("fecha no puede ser null");
        if(inicio==null)throw new ArgumentoException("inicio no puede ser null");
        if(duracion<=0)throw new ArgumentoException("La duración no puede ser menor o igual a 0");
        this.cliente=cliente;
        this.fecha=fecha;
        this.inicio=inicio;
        this.duracion=duracion;

    }

    public final String getCliente() {
        return cliente;
    }

    public final LocalDate getFecha() {
        return fecha;
    }

    public final LocalTime getInicio() {
        return inicio;
    }

    public final int getDuracion() {
        return duracion;
    }

    public final LocalTime getFin() {
        return inicio.plusMinutes(duracion) ;
    }

    public abstract Cita conInicio(LocalTime nuevoInicio) throws ArgumentoException;
    @Override
    public String toString(){
        return cliente+" "+fecha+" "+inicio+"-"+getFin();
    }


}
