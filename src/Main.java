//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) throws Exception {
        Agenda agenda = new Agenda(LocalTime.of(9, 0), LocalTime.of(20, 0));
        LocalDate lunes = LocalDate.of(2026, 10, 19);

        agenda.reservar(new CitaPrueba("Ana", lunes, LocalTime.of(10, 0), 30));
        agenda.reservar(new CitaPrueba("Bruno",lunes,LocalTime.of(9,0),30));
        agenda.reservar(new CitaPrueba("David",lunes,LocalTime.of(11,0),60));

        try {
            agenda.reservar(new CitaPrueba("Elena", lunes, LocalTime.of(10, 15), 30));
            IO.println("ERROR: debería haber fallado (solapamiento)");
        } catch (ArgumentoException e) {
            IO.println("OK: " + e.getMessage());
        }
        try {
            agenda.reservar(new CitaPrueba("Ana", lunes, LocalTime.of(15, 0), 30));
            IO.println("ERROR: debería haber fallado (solapamiento)");
        } catch (ArgumentoException e) {
            IO.println("OK: " + e.getMessage());
        }
        try {
            agenda.reservar(new CitaPrueba("Carmen", lunes, LocalTime.of(19, 45), 30));
            IO.println("ERROR: debería haber fallado (solapamiento)");
        } catch (ArgumentoException e) {
            IO.println("OK: " + e.getMessage());
        }

        agenda.reservar(new CitaPrueba("Carmen",lunes,LocalTime.of(10,30),30));

        try{
            agenda.retrasar("Ana",lunes,15);
            IO.println("Error: debería haber fallado(solapamiento)");
        } catch (ArgumentoException e){
            IO.println("Ok: "+e.getMessage());
        }

        agenda.retrasar("David",lunes,30);
        agenda.cancelar("Bruno",lunes);




        for (int i = 0; i < agenda.getNumCitas(); i++) {
            IO.println(agenda.getCitaEn(i));
        }
    }
}
