package meli.rasec.app.util;

/**
 * Contiene utilitarios para uso de Numeros
 * @author <a href="mailto:cesarnt@gmail.com">Cesar Nunez T</a>
 * @version 1.0.0
 */
public class NumberUtils {

    private NumberUtils(){

    }

    /**
     * Convierte un arreglo de double a uno de float
     * @param doubleArray
     * @return float[]
     */
    public static float[] toFloatArray(double[] doubleArray) {
        float[] floatArray = new float[doubleArray.length];
        for(int i=0; i < doubleArray.length; ++i) {
            floatArray[i]= (float)doubleArray[i];
        }
        return floatArray;
    }


    /**
     * Convierte un arreglo de float a uno de double
     * @param floatArray
     * @return double[]
     */
    public static double[] toDoubleArray(float[] floatArray) {
        double[] doubleArray = new double[floatArray.length];
        for(int i=0; i < floatArray.length; ++i) {
            doubleArray[i]= floatArray[i];
        }

        return doubleArray;
    }

    /**
     * Se redondae el valor <code>n</code> a la cantidad definida en <code>decimales</code>
     * @param n numero a redondear
     * @param decimales cantidad de decimales resultantes
     * @return double redondeado
     */
    public static float redondear(double n, int decimales){
        double potencia = Math.pow(10,decimales);
        return (float)(Math.round(n*potencia)/potencia);
    }
}
