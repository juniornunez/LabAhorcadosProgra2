/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ahorcadoslab;

/**
 *
 * @author Junior Nuñez
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdminPalabrasSecretas {

    private List<String> palabras;

    public AdminPalabrasSecretas() {
        palabras = new ArrayList<>();
    }

    public boolean agregarPalabra(String palabra) {
        palabra = palabra.toLowerCase();
        if (palabras.contains(palabra)) {
            return false;
        }
        palabras.add(palabra);
        return true;
    }

    public List<String> getPalabras() {
        return palabras;
    }

    public String seleccionarPalabraAlAzar() {
        if (palabras.isEmpty()) {
            throw new IllegalStateException("No hay palabras en la lista");
        }
        Random random = new Random();
        return palabras.get(random.nextInt(palabras.size()));
    }
}