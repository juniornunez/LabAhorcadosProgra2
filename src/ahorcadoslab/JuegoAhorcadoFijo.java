/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ahorcadoslab;

/**
 *
 * @author Junior Nuñes
 */
public abstract class JuegoAhorcadoFijo extends JuegoAhorcadoBase {

    public JuegoAhorcadoFijo(String palabraSecreta) {
        this.palabraSecreta = palabraSecreta;
        this.palabraActual = "_".repeat(palabraSecreta.length());
        this.intentos = 6; 
    }

    @Override
    public void inicializarPalabraSecreta() {
       
    }

    @Override
    public void jugar() {
        
    }

    @Override
    public void actualizarPalabraActual(char letra) {
        StringBuilder nuevaPalabra = new StringBuilder(palabraActual);
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra) {
                nuevaPalabra.setCharAt(i, letra);
            }
        }
        palabraActual = nuevaPalabra.toString();
    }

    @Override
    public boolean verificarLetra(char letra) {
        return palabraSecreta.indexOf(letra) >= 0;
    }

    @Override
    public boolean hasGanado() {
        return palabraActual.equals(palabraSecreta);
    }

    void setPalabraFija(String palabraSeleccionada) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
