import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Agenda implements Serializable {
    private LocalTime apertura;
    private LocalTime cierre;
    private ArrayList<Cita> citas;

    public Agenda(LocalTime apertura, LocalTime cierre) throws ArgumentoException {
        if(apertura==null)throw new ArgumentoException("Apertura no puede ser nulo");
        if(cierre==null)throw new ArgumentoException("Cierre no puede ser nulo");
        if(!apertura.isBefore(cierre))throw new ArgumentoException("Apertura no es anterior a cierre");

        this.apertura = apertura;
        this.cierre = cierre;
        this.citas = new ArrayList<>();

    }

    public LocalTime getApertura() {
        return apertura;
    }

    public LocalTime getCierre() {
        return cierre;
    }

    public int getNumCitas() {
        return citas.size();
    }

    public Cita getCitaEn(int i) throws ArgumentoException {
        if(i<0||i>=citas.size()){
            throw new ArgumentoException("la posicion no puedeser menor a 0 ni tampoco mayor o igual que citas size");
        }
        return citas.get(i);
    }

    public ArrayList<Cita> getCitasDel(LocalDate fecha) throws ArgumentoException {
        if(fecha==null){
            throw new ArgumentoException("La fecha no puede ser nula");
        }
        ArrayList<Cita>resultado=new ArrayList<>();
        for (Cita c : citas) {
            if (c.getFecha().equals(fecha)) {
                resultado.add(c);
            }
        }
        return resultado;

    }

    public boolean tieneCita(String cliente, LocalDate fecha) throws ArgumentoException {
        if(cliente==null||fecha==null){
            throw new ArgumentoException("Ni cliente ni fecha pueden ser nulos");
        }
        for(Cita c:citas){
            if(c.getCliente().equals(cliente) && c.getFecha().equals(fecha)){
                return true;
            }

        }
        return false;

    }

    public Cita getCitaDe(String cliente, LocalDate fecha) throws ArgumentoException {
        if(cliente==null||fecha==null){
            throw new ArgumentoException("Ni cliente ni fecha pueden ser nulos");
        }
        for(Cita c:citas){
            if(c.getCliente().equals(cliente)&&c.getFecha().equals(fecha)){
                return c;
            }
        }
        throw new ArgumentoException("no existe esa cita");
    }

    public boolean estaLibre(LocalDate fecha, LocalTime h, int d) throws ArgumentoException {
        if(fecha==null||h==null||d<=0){
            throw new ArgumentoException("Ni fecha ni h pueden ser nulos ni tampoco d puede ser menor o igual a 0");
        }
        LocalTime fin=h.plusMinutes(d);
        if(h.isBefore(apertura)||fin.isAfter(cierre)){
            return false;
        }
        for(Cita c:citas){
            if(c.getFecha().equals(fecha)&&h.isBefore(c.getFin())&&c.getInicio().isBefore(fin)){
                return false;
            }
        }
        return true;
    }

    public void reservar(Cita c) throws ArgumentoException {
        if(c==null){
            throw new ArgumentoException("c no puede ser nulo");
        }
        if(tieneCita(c.getCliente(),c.getFecha())){
            throw new ArgumentoException("el cliente ya tiene cita");
        }
        if(!estaLibre(c.getFecha(),c.getInicio(),c.getDuracion())){
            throw new ArgumentoException("No esta libre");
        }

        int posicion = citas.size();
        for (int i = 0; i < citas.size(); i++) {
            Cita x = citas.get(i);
            if (x.getFecha().isAfter(c.getFecha())||(x.getFecha().equals(c.getFecha())&&x.getInicio().isAfter(c.getInicio()))) {
                posicion = i;
                break;
            }
        }
        citas.add(posicion, c);
    }

    public void cancelar(String cliente, LocalDate fecha) throws ArgumentoException {
        Cita c=getCitaDe(cliente,fecha);
        citas.remove(c);
    }

    public void retrasar(String cliente, LocalDate fecha, int minutos) throws ArgumentoException {
        if(minutos<=0){
            throw new ArgumentoException("Los minutos no pueden ser menor o igual a 0");
        }
        Cita c = getCitaDe(cliente, fecha);
        cancelar(cliente, fecha);
        try {
            reservar(c.conInicio(c.getInicio().plusMinutes(minutos)));
        } catch (ArgumentoException e) {
            reservar(c);   // restaura la cita original
            throw e;       // y vuelve a lanzar el error
        }
    }

    public ArrayList<Cita> getCitasEntre(LocalDate desde, LocalDate hasta) throws ArgumentoException {
        if(desde==null||hasta==null){
            throw new ArgumentoException("Desde y hasta no pueden ser nulos");
        }
        if (hasta.isBefore(desde)) {
            throw new ArgumentoException("Hasta no puede empezar antes que desde");
        }
        ArrayList<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if ( !c.getFecha().isBefore(desde) && !c.getFecha().isAfter(hasta) ) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    private LocalTime buscarHueco(LocalDate fecha, int d) throws ArgumentoException {
        if(estaLibre(fecha,apertura,d)){
            return apertura;
        }
        for(Cita c:citas){
            if(c.getFecha().equals(fecha)&&estaLibre(fecha,c.getFin(),d)){
                return c.getFin();
            }
        }
        return null;
    }
    public boolean hayHueco(LocalDate fecha, int d) throws ArgumentoException {
        if(buscarHueco(fecha,d)==null){
            return false;
        }
        return true;
    }

    public LocalTime primerHueco(LocalDate fecha, int d) throws ArgumentoException {
        LocalTime h=buscarHueco(fecha,d);
        if(h==null){
            throw new ArgumentoException("No hay hueco");
        }
        return h;
    }

    public int getJornada() {
        return (cierre.getHour()*60+cierre.getMinute())-(apertura.getHour()*60+apertura.getMinute());
    }

    public LocalDate primerDiaConHueco(LocalDate desde, int d) throws ArgumentoException {
        if(desde==null||d<=0||d>getJornada()){
            throw new ArgumentoException("desde no puede ser nulo y la duración debe ser positiva y no mayor que la jornada");
        }
        LocalDate dia=desde;
        while (!hayHueco(dia,d)){
            dia=dia.plusDays(1);
        }
        return dia;
    }
}
