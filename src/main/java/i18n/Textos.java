package i18n;

public enum Textos {

    APP_TITLE(0),
    APP_SUBTITLE(1),

    ALTA(2),
    BAJA(3),
    CONSULTA(4),
    MODIFICACION(5),
    ACEPTAR(6),
    CANCELAR(7),
    ERROR(8),
    NO_ENCONTRADO(9),
    OK(10),
    SALIDA(11),
    SALIR(12),
    OPERACIONES(13),
    IDIOMA(14),
    ESPAÑOL(15),
    INGLES(16),
    FRANCES(17),
    ITALIANO(18),
    ALEMAN(19),
    POLACO(20),
    ID(21),
    NOMBRE(22),
    LOCALIDAD(23),
    ALTA_DEP(24),
    MOD_DEP(25),
    DEL_DEP(26),
    LISTADO(27),
    BUSCAR(28),
    ELIMINAR_SELECCION(29),
    AYUDA(30),
    ACERCA_DE(31),
    TEMA(32),
    DEP_DUPLICADO_TITLE(33),
    DEP_DUPLICADO_MSG(34),
    CLARO(35),
    OSCURO(36);

    public final int index;

    Textos(int index) {
        this.index = index;
    }
}
