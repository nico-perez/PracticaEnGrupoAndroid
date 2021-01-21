package chavales.los.practica1android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Producto implements Parcelable {

    private String nombreProducto;
    private String marcaProducto;
    private int imagenProducto;
    private Calidad calidadProducto;
    private Calendar ultimaConsulta;

    // esto no se sinceramente
    private String designacionProducto;
    private String supermercado;

    private List<Detalle> detalles;

    public Producto(String nombreProducto, String marcaProducto, int imagenProducto, Calidad calidadProducto, Calendar ultimaConsulta, List<Detalle> detalles) {
        this.nombreProducto = nombreProducto;
        this.marcaProducto = marcaProducto;
        this.imagenProducto = imagenProducto;
        this.calidadProducto = calidadProducto;
        this.ultimaConsulta = ultimaConsulta;
        this.detalles = detalles;
    }

    public Producto(String nombreProducto, String marcaProducto, int imagenProducto, Calidad calidadProducto, Calendar ultimaConsulta, List<Detalle> detalles, String designacionProducto, String supermercado) {
        this.nombreProducto = nombreProducto;
        this.marcaProducto = marcaProducto;
        this.imagenProducto = imagenProducto;
        this.calidadProducto = calidadProducto;
        this.ultimaConsulta = ultimaConsulta;
        this.detalles = detalles;
        this.designacionProducto = designacionProducto;
        this.supermercado = supermercado;
    }

    public String getNombre() {
        return nombreProducto;
    }

    public void setNombre(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getMarca() {
        return marcaProducto;
    }

    public void setMarca(String marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    public int getImagen() {
        return imagenProducto;
    }

    public void setImagen(int imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public Calidad getCalidad() {
        return calidadProducto;
    }

    public void setCalidad(Calidad calidadProducto) {
        this.calidadProducto = calidadProducto;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    public static class Detalle {

        private final String bajoAltoEn;
        private final Nutricion ingrediente;
        private final float cantidad; // p.ej. «5,3 g», «80 ml», etc.
        private final Calidad calidad;

        public Detalle(String bajoAltoEn, Nutricion ingrediente, float cantidad, Calidad calidad) {
            this.bajoAltoEn = bajoAltoEn;
            this.ingrediente = ingrediente;
            this.cantidad = cantidad;
            this.calidad = calidad;
        }

        public Nutricion getIngrediente() {
            return ingrediente;
        }

        public float getCantidad() {
            return cantidad;
        }

        public String getBajoAltoEn() {
            return bajoAltoEn;
        }
        public Calidad getCalidad() {
            return calidad;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombreProducto);
        dest.writeString(marcaProducto);
        dest.writeInt(imagenProducto);
        dest.writeValue(calidadProducto);
        dest.writeValue(ultimaConsulta);
        dest.writeString(designacionProducto);
        dest.writeString(supermercado);

        if (detalles != null) {
            // Número de elementos en la lista de detalles
            dest.writeInt(detalles.size());
            // Detalles en sí
            for (Detalle d : detalles) {
                dest.writeString(d.bajoAltoEn);
                dest.writeInt(d.ingrediente.ordinal());
                dest.writeFloat(d.cantidad);
                dest.writeInt(d.calidad.ordinal());
            }
        } else {
            dest.writeInt(0);
        }
    }

    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel source) {

            Producto aDevolver = new Producto(source.readString(),
                                     source.readString(),
                                     source.readInt(),
                                     (Calidad) source.readValue(null),
                                     (Calendar) source.readValue(null),
                                     null,
                                     source.readString(),
                                     source.readString());

            final int numDetalles;
            List<Detalle> detalles = new ArrayList<>(numDetalles = source.readInt());
            for (int i = 0; i < numDetalles; ++i) {
                detalles.add(new Detalle(source.readString(),
                                         Nutricion.values()[source.readInt()],
                                         source.readFloat(),
                                         Calidad.values()[source.readInt()]));
            }
            aDevolver.setDetalles(detalles);
            return aDevolver;
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };
}