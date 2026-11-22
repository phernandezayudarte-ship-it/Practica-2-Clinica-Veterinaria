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

        IO.println(agenda.getTiempoLibre(lunes));
        IO.println(agenda.getJornada());
        IO.println(agenda.primerHueco(lunes,60));
        IO.println(agenda.primerHueco(lunes,90));
        IO.println(agenda.primerDiaConHueco(lunes,600));
        IO.println(agenda.getCitasEntre(lunes,lunes.plusDays(1)).size());




        for (int i = 0; i < agenda.getNumCitas(); i++) {
            IO.println(agenda.getCitaEn(i));
        }

        Mascota toby = new Mascota("M001", "Toby", "Ana García",
                Especie.PERRO, LocalDate.of(2020, 3, 15), 28000);
        System.out.println(toby);
        System.out.println("Edad: " + toby.getEdad(LocalDate.of(2026, 11, 22)));
        System.out.println("Duración consulta: " + toby.getEspecie().getDuracionConsulta());
        Mascota misi = new Mascota("M002", "Misi", "Bruno López",
                Especie.GATO, LocalDate.of(2022, 6, 1), 4000);
        Mascota kiwi = new Mascota("M003", "Kiwi", "Carla Ruz",
                Especie.EXOTICO, LocalDate.of(2024, 1, 10), 1800);
        Mascota luna = new Mascota("M004", "Luna", "Diego Martín",
                Especie.PERRO, LocalDate.of(2025, 2, 20), 8000);
        System.out.println(misi);
        System.out.println("Edad: " + misi.getEdad(LocalDate.of(2026, 11, 22)));
        System.out.println("Duración consulta: " + misi.getEspecie().getDuracionConsulta());

        System.out.println(kiwi);
        System.out.println("Edad: " + kiwi.getEdad(LocalDate.of(2026, 11, 22)));
        System.out.println("Duración consulta: " + kiwi.getEspecie().getDuracionConsulta());

        System.out.println(luna);
        System.out.println("Edad: " + luna.getEdad(LocalDate.of(2026, 11, 22)));
        System.out.println("Duración consulta: " + luna.getEspecie().getDuracionConsulta());

        Veterinario v1 = new Veterinario("V1", "Laura Martín", Especialidad.GENERAL);
        Veterinario v2 = new Veterinario("V2", "Pablo Ortega", Especialidad.CIRUGIA);
        Veterinario v3 = new Veterinario("V3", "Elena Sanz", Especialidad.EXOTICOS);

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v3);

        System.out.println(v1.puedeAtender(Especialidad.CIRUGIA));
        System.out.println(v2.puedeAtender(Especialidad.CIRUGIA));
        System.out.println(v3.puedeAtender(Especialidad.GENERAL));
        System.out.println(v3.puedeAtender(Especialidad.CIRUGIA));
    }

}
