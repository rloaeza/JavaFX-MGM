package modelo;

public class PDFvalores {

    public static String rutaFormatosVacios = "formatos/";
    public static String prefijoGuardado = "Formato_";


    private String campo;
    private String valor;

    public PDFvalores(String campo, String valor) {
        this.campo = campo;
        this.valor = valor;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "PDFvalores{" +
                "campo='" + campo + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
