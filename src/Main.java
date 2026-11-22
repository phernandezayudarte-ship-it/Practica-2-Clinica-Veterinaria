//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        // ---- Hito 2: jerarquía de citas (escenario del apartado 7) ----
        LocalDate lunes19 = LocalDate.of(2026, 10, 19);
        LocalDate martes20 = LocalDate.of(2026, 10, 20);
        LocalDate miercoles21 = LocalDate.of(2026, 10, 21);
        LocalDate jueves22 = LocalDate.of(2026, 10, 22);

        // Consultas: precio, tipo, duración y especialidad según la especie,
        // calculados a través de variables de tipo CitaVeterinaria (polimorfismo)
        CitaVeterinaria cita1 = new Consulta(toby, v1, lunes19, LocalTime.of(9, 0), "cojera");
        CitaVeterinaria cita2 = new Consulta(kiwi, v3, lunes19, LocalTime.of(10, 0), "revisión anual");
        CitaVeterinaria cita4 = new Consulta(luna, v1, miercoles21, LocalTime.of(11, 0), "vómitos");
        CitaVeterinaria cita5 = new Consulta(misi, v1, jueves22, LocalTime.of(12, 0), "pérdida de apetito");
        CitaVeterinaria[] consultas = {cita1, cita2, cita4, cita5};
        for (CitaVeterinaria c : consultas) {
            System.out.println(c);
            System.out.println("  " + c.getTipo() + " de " + c.getMascota().getEspecie()
                    + ": precio " + c.precio() + ", duración " + c.getDuracion()
                    + " min, especialidad " + c.especialidadRequerida());
        }

        try {
            new Consulta(toby, v1, lunes19, LocalTime.of(13, 0), "   ");
            System.out.println("ERROR: debería haber fallado (motivo vacío)");
        } catch (ArgumentoException e) {
            System.out.println("OK: " + e.getMessage());
        }
        try {
            new Consulta(null, v1, lunes19, LocalTime.of(13, 0), "revisión");
            System.out.println("ERROR: debería haber fallado (mascota nula)");
        } catch (ArgumentoException e) {
            System.out.println("OK: " + e.getMessage());
        }

        // Cirugía: precio con la anestesia según el peso (ejemplo del enunciado: 222,00 €)
        CitaVeterinaria cita3 = new Cirugia(toby, v2, martes20, LocalTime.of(9, 0), 120,
                "extirpación de un quiste");
        System.out.println(cita3);
        System.out.println("  " + cita3.getTipo() + ": precio " + cita3.precio()
                + " (esperado 22200), duración " + cita3.getDuracion()
                + " min, especialidad " + cita3.especialidadRequerida());

        try {
            new Cirugia(toby, v2, martes20, LocalTime.of(9, 0), 120, "");
            System.out.println("ERROR: debería haber fallado (intervención vacía)");
        } catch (ArgumentoException e) {
            System.out.println("OK: " + e.getMessage());
        }

        // Normas: una que se cumple y otra que falla por cada norma.
        // Fecha y hora actual fija: viernes 16/10/2026 a las 10:00
        LocalDateTime ahora = LocalDateTime.of(2026, 10, 16, 10, 0);
        LocalDate jueves15 = LocalDate.of(2026, 10, 15);
        LocalDate viernes16 = LocalDate.of(2026, 10, 16);
        LocalDate sabado17 = LocalDate.of(2026, 10, 17);
        LocalDate domingo18 = LocalDate.of(2026, 10, 18);

        probarNormas("cita 1 del escenario", cita1, ahora, true);
        probarNormas("cita 2 del escenario", cita2, ahora, true);
        probarNormas("cita 3 del escenario", cita3, ahora, true);
        probarNormas("cita 4 del escenario", cita4, ahora, true);
        probarNormas("cita 5 del escenario", cita5, ahora, true);

        // Domingo cerrado
        probarNormas("consulta en sábado", new Consulta(misi, v1, sabado17, LocalTime.of(10, 0), "revisión"), ahora, true);
        probarNormas("consulta en domingo", new Consulta(misi, v1, domingo18, LocalTime.of(10, 0), "revisión"), ahora, false);

        // Horario de 9:00 a 20:00 (casos límite)
        probarNormas("empieza justo a las 9:00", new Consulta(misi, v1, lunes19, LocalTime.of(9, 0), "revisión"), ahora, true);
        probarNormas("empieza a las 8:45", new Consulta(misi, v1, lunes19, LocalTime.of(8, 45), "revisión"), ahora, false);
        probarNormas("termina justo a las 20:00", new Consulta(misi, v1, lunes19, LocalTime.of(19, 30), "revisión"), ahora, true);
        probarNormas("termina a las 20:15", new Consulta(misi, v1, lunes19, LocalTime.of(19, 45), "revisión"), ahora, false);
        probarNormas("exótico (45 min) termina justo a las 20:00", new Consulta(kiwi, v3, lunes19, LocalTime.of(19, 15), "revisión"), ahora, true);

        // No se programa en el pasado
        probarNormas("hoy a las 12:00", new Consulta(misi, v1, viernes16, LocalTime.of(12, 0), "revisión"), ahora, true);
        probarNormas("hoy a las 9:00", new Consulta(misi, v1, viernes16, LocalTime.of(9, 0), "revisión"), ahora, false);
        probarNormas("ayer", new Consulta(misi, v1, jueves15, LocalTime.of(12, 0), "revisión"), ahora, false);

        // Especialidad exigida
        probarNormas("perro con Exóticos", new Consulta(toby, v3, lunes19, LocalTime.of(12, 0), "revisión"), ahora, true);
        probarNormas("exótico con General", new Consulta(kiwi, v1, lunes19, LocalTime.of(12, 0), "revisión"), ahora, false);
        probarNormas("cirugía con General", new Cirugia(toby, v1, martes20, LocalTime.of(9, 0), 60, "sutura"), ahora, false);

        // Cirugía: al menos el día anterior
        probarNormas("cirugía mañana", new Cirugia(toby, v2, sabado17, LocalTime.of(9, 0), 60, "sutura"), ahora, true);
        probarNormas("cirugía hoy", new Cirugia(toby, v2, viernes16, LocalTime.of(12, 0), 60, "sutura"), ahora, false);

        // Cirugía: termina como muy tarde a las 14:00
        probarNormas("cirugía termina justo a las 14:00", new Cirugia(toby, v2, martes20, LocalTime.of(12, 0), 120, "sutura"), ahora, true);
        probarNormas("cirugía termina a las 14:30", new Cirugia(toby, v2, martes20, LocalTime.of(12, 30), 120, "sutura"), ahora, false);

        // Cirugía: de 30 a 240 minutos en tramos de 30
        probarNormas("cirugía de 30 min", new Cirugia(toby, v2, martes20, LocalTime.of(9, 0), 30, "sutura"), ahora, true);
        probarNormas("cirugía de 240 min", new Cirugia(toby, v2, martes20, LocalTime.of(9, 0), 240, "sutura"), ahora, true);
        probarNormas("cirugía de 45 min", new Cirugia(toby, v2, martes20, LocalTime.of(9, 0), 45, "sutura"), ahora, false);
        probarNormas("cirugía de 270 min", new Cirugia(toby, v2, martes20, LocalTime.of(9, 0), 270, "sutura"), ahora, false);
    }

    private static void probarNormas(String caso, CitaVeterinaria cita, LocalDateTime ahora, boolean debeCumplir) {
        try {
            cita.comprobarNormas(ahora);
            System.out.println((debeCumplir ? "OK: " : "ERROR: debería haber fallado: ") + caso);
        } catch (ClinicaException | ArgumentoException e) {
            System.out.println((debeCumplir ? "ERROR: " : "OK: ") + caso + " -> " + e.getMessage());
        }
    }

}
