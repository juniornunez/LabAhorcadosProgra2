/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ahorcadoslab;

/**
 *
 * @author Junior Nuñez
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private JuegoAhorcadoBase juego;
    private JLabel palabraLabel;
    private JLabel intentosLabel;
    private JTextField letraTextField;
    private JButton adivinarButton;
    private JTextArea resultadoArea;
    private JTextArea listaPalabrasArea;
    private JTextField palabraTextField;
    private JButton agregarPalabraButton;
    private JButton establecerFijoButton;
    private AdminPalabrasSecretas admin;
    private String palabraFija;

    public GUI() {

        setTitle("Juego del Ahorcado");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.PINK);
        topPanel.setLayout(new GridLayout(2, 2));

        palabraLabel = new JLabel("Palabra: ");
        intentosLabel = new JLabel("Intentos restantes: ");
        letraTextField = new JTextField();
        adivinarButton = new JButton("Adivinar");

        topPanel.add(new JLabel("Ingresa una letra: "));
        topPanel.add(intentosLabel);
        topPanel.add(palabraLabel);

        topPanel.add(letraTextField);

        add(topPanel, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton administrarButton = new JButton("Administrar");
        JButton juegoFijoButton = new JButton("Juego Fijo");
        JButton juegoAzarButton = new JButton("Juego Azar");

        bottomPanel.add(administrarButton);
        bottomPanel.add(juegoFijoButton);
        bottomPanel.add(juegoAzarButton);

        add(bottomPanel, BorderLayout.SOUTH);

        juegoFijoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (palabraFija == null) {
                    resultadoArea.append("Primero debes establecer una palabra fija en la pantalla de administracion.\n");
                    return;
                }
                iniciarJuegoFijo();
            }
        });

        juegoAzarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuegoAzar();
            }
        });

        administrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPantallaAdministracion();
            }
        });

        adivinarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugar();
            }
        });

        topPanel.add(adivinarButton);
        setVisible(true);
    }

    private void iniciarJuegoFijo() {
        juego = new JuegoAhorcadoFijo(palabraFija) {
            @Override
            public void Jugar() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        resultadoArea.setText("Iniciado juego con palabra fija.\n");
        actualizarPantalla();
        desbloquearJuego();
    }

    private void iniciarJuegoAzar() {
        if (admin == null) {
            resultadoArea.append("Primero debes añadir las palabras en el apartado de Administrar.\n");
            return;
        }
        juego = new JuegoAhorcadoAzar(admin) {
            @Override
            public void Jugar() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        resultadoArea.setText("Iniciado juego con palabra al azar.\n");
        actualizarPantalla();
        desbloquearJuego();
    }

    private void jugar() {
        if (juego == null) {
            resultadoArea.append("Primero selecciona un tipo de juego.\n");
            return;
        }

        String letra = letraTextField.getText().trim();
        if (letra.length() != 1) {
            resultadoArea.append("Ingresa solo una letra.\n");
            letraTextField.setText("");
            return;
        }

        char letraChar = letra.charAt(0);
        if (juego.verificarLetra(letraChar)) {
            resultadoArea.append("Correcto \n");
            juego.actualizarPalabraActual(letraChar);
        } else {
            resultadoArea.append("Incorrecto \n");
            juego.intentos--;
        }

        actualizarPantalla();

        if (juego.hasGanado()) {
            resultadoArea.append("Felicidades. Has adivinado la palabra: " + juego.palabraSecreta + "\n");
            bloquearJuego();
        } else if (juego.intentos <= 0) {
            resultadoArea.append("Lo siento, has perdido. La palabra era: " + juego.palabraSecreta + "\n");
            bloquearJuego();
        }
    }

    private void actualizarPantalla() {

        if (juego != null) {
            palabraLabel.setText("Palabra: " + juego.palabraActual);
            intentosLabel.setText("Intentos restantes: " + juego.intentos);
        }
        letraTextField.setText("");
        letraTextField.requestFocus();
    }

    private void bloquearJuego() {
        adivinarButton.setEnabled(false);
        letraTextField.setEnabled(false);
    }

    private void desbloquearJuego() {
        adivinarButton.setEnabled(true);
        letraTextField.setEnabled(true);
    }

    private void mostrarPantallaAdministracion() {
        JFrame adminFrame = new JFrame("Administrar Palabras");
        adminFrame.setSize(600, 400);
        adminFrame.setBackground(Color.PINK);
        adminFrame.setLayout(new BorderLayout());
        adminFrame.setLocationRelativeTo(null);

        JPanel adminPanel = new JPanel();
        adminPanel.setBackground(Color.PINK);
        adminPanel.setLayout(new GridLayout(2, 2));

        if (admin == null) {
            admin = new AdminPalabrasSecretas();
        }

        palabraTextField = new JTextField();
        agregarPalabraButton = new JButton("Agregar Palabra");
        establecerFijoButton = new JButton("Establecer Palabra Fija");

        adminPanel.add(new JLabel("Nueva Palabra:"));
        adminPanel.add(palabraTextField);
        adminPanel.add(agregarPalabraButton);
        adminPanel.add(establecerFijoButton);

        listaPalabrasArea = new JTextArea();
        listaPalabrasArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(listaPalabrasArea);

        adminFrame.add(adminPanel, BorderLayout.NORTH);
        listaPalabrasArea.setBackground(Color.LIGHT_GRAY);
        adminFrame.add(scrollPane, BorderLayout.CENTER);

        listaPalabrasArea.setText("");
        for (String palabra : admin.getPalabras()) {
            listaPalabrasArea.append(palabra + "\n");
        }

        agregarPalabraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String palabra = palabraTextField.getText().trim();
                if (!palabra.isEmpty()) {
                    if (admin.agregarPalabra(palabra)) {
                        listaPalabrasArea.append(palabra.toLowerCase() + "\n");
                        palabraTextField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(adminFrame, "La palabra ya ha sido añadida.");
                    }
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "Por favor, ingresa una palabra.");
                }
            }
        });

        JPanel volverPanel = new JPanel(new BorderLayout());
        JButton volverBtn = new JButton("Volver");
        volverPanel.add(volverBtn, BorderLayout.CENTER);
        adminFrame.add(volverPanel, BorderLayout.SOUTH);

        volverBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose();
                setVisible(true);
            }
        });
        
        establecerFijoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (admin.getPalabras().isEmpty()) {
                    JOptionPane.showMessageDialog(adminFrame, "No hay palabras para establecer como fija");
                    return;
                }
                String palabra = JOptionPane.showInputDialog(adminFrame, "Selecciona la palabra fija: ");
                if (palabra != null && !palabra.isEmpty()) {
                    palabra = palabra.toLowerCase();
                    if (admin.getPalabras().contains(palabra)) {
                        palabraFija = palabra;
                    } else {
                        JOptionPane.showMessageDialog(adminFrame, "Palabra no válida o no encontrada");
                    }
                }
            }
        });

        adminFrame.setVisible(true);
    }
}
