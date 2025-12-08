package data;

import model.Departamento;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private static List<Departamento> lista = new ArrayList<>();

    public static void addDepartamento(Departamento d) {
        lista.add(d);
    }

    public static List<Departamento> getDepartamentos() {
        return lista;
    }
}
