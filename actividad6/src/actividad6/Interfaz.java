package actividad6;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Se define la clase 'Interfaz' que extiende de JFrame e implementa ActionListener
public class Interfaz extends JFrame implements ActionListener {
    // Atributo que representa el archivo donde se almacenarán los contactos
    private File contactsFile = new File("friendsContact.txt");

    // Botones para realizar diferentes acciones en la interfaz
    private JButton createButton, readButton, deleteButton, updateButton, clearButton, exitButton;

    // Campos de texto para ingresar el nombre y número del contacto
    private JTextField nameField, numberField;

    // Área de texto para mostrar los resultados o mensajes
    private JTextArea resultField;

    // Constructor de la clase 'Interfaz'
    public Interfaz() {
        // Configuración de la ventana principal
        setTitle("Gestión de contactos");
        setSize(600, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creación de etiquetas y campos de texto para el nombre y número del contacto
        JLabel nameLabel = new JLabel("Nombre:");
        nameField = new JTextField(10);
        JLabel numberLabel = new JLabel("Número:");
        numberField = new JTextField(10);

        // Creación de botones y asignación de ActionListener para manejar eventos
        createButton = new JButton("Crear contacto");
        createButton.addActionListener(this);

        readButton = new JButton("Listar contactos");
        readButton.addActionListener(this);

        updateButton = new JButton("Actualizar contacto");
        updateButton.addActionListener(this);

        deleteButton = new JButton("Eliminar contacto");
        deleteButton.addActionListener(this);

        clearButton = new JButton("Limpiar");
        clearButton.addActionListener(this);

        exitButton = new JButton("Salir");
        exitButton.addActionListener(this);

        resultField = new JTextArea(15, 20);
        resultField.setEditable(false);

        // Establece el diseño principal de la interfaz como BorderLayout con espaciado horizontal y vertical de 10 píxeles
        setLayout(new BorderLayout(10, 10));

// Panel para los datos del contacto con un diseño de cuadrícula de 2 filas y 2 columnas, y un espacio de 5 píxeles entre componentes
        JPanel datosPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        datosPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20)); // Agrega un borde vacío alrededor del panel
        datosPanel.add(nameLabel);  // Agrega la etiqueta del nombre al panel
        datosPanel.add(nameField);  // Agrega el campo de texto para el nombre al panel
        datosPanel.add(numberLabel);  // Agrega la etiqueta del número al panel
        datosPanel.add(numberField);  // Agrega el campo de texto para el número al panel

// Panel para los botones con un diseño de flujo centrado y espacio de 10 píxeles entre componentes
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonesPanel.add(createButton);  // Agrega el botón de crear contacto al panel
        botonesPanel.add(readButton);  // Agrega el botón de listar contactos al panel
        botonesPanel.add(updateButton);  // Agrega el botón de actualizar contacto al panel
        botonesPanel.add(deleteButton);  // Agrega el botón de eliminar contacto al panel
        botonesPanel.add(clearButton);  // Agrega el botón de limpiar al panel
        botonesPanel.add(exitButton);  // Agrega el botón de salir al panel

// Panel para la respuesta o resultado con un diseño de cuadrícula de 1 fila y 2 columnas y espacio de 5 píxeles entre componentes
        JPanel responsePanel = new JPanel(new GridLayout(1, 2, 5, 5));
        responsePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20)); // Agrega un borde vacío alrededor del panel

// Panel de desplazamiento que permite desplazarse por el contenido de resultField
        JScrollPane scrollPane = new JScrollPane(resultField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Configura la barra de desplazamiento vertical
        responsePanel.add(scrollPane);  // Agrega el panel de desplazamiento al panel de respuesta

// Agrega los paneles de datos, botones y respuesta a la interfaz principal utilizando BorderLayout
        add(datosPanel, BorderLayout.NORTH);  // Coloca el panel de datos en la parte superior de la interfaz
        add(botonesPanel, BorderLayout.CENTER);  // Coloca el panel de botones en el centro de la interfaz
        add(responsePanel, BorderLayout.SOUTH);  // Coloca el panel de respuesta en la parte inferior de la interfaz
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Verifica la acción específica del evento


        if (e.getActionCommand().equals("Crear contacto")) {
            // Obtiene el nombre y número del nuevo contacto  CORRESPONDE A LA ACCIÓN CREATE
            String newName = nameField.getText();
            long newNumber;

            try {
                newNumber = Long.parseLong(numberField.getText());
            } catch (NumberFormatException ex) {
                resultField.setText("Ingresa un número válido.");
                return;
            }

            try {
                // Crea un objeto File que representa el archivo "friendsContact.txt"
                File file = new File("friendsContact.txt");

                // Si el archivo no existe, lo crea
                if (!file.exists()) {
                    file.createNewFile();
                }

                // Crea un flujo de acceso aleatorio para escribir en el archivo
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                boolean found = false;

                // Busca en el archivo para evitar duplicados por nombre o número
                while (raf.getFilePointer() < raf.length()) {
                    String nameNumberString = raf.readLine();
                    String[] lineSplit = nameNumberString.split("!");

                    if (lineSplit.length >= 2) {
                        String name = lineSplit[0];
                        long number = Long.parseLong(lineSplit[1]);

                        if (name.equals(newName) || number == newNumber) {
                            found = true;
                            break;
                        }
                    }
                }

                // Si no se encontró, agrega el nuevo contacto al archivo
                if (!found) {
                    String nameNumberString = newName + "!" + String.valueOf(newNumber);
                    raf.writeBytes(nameNumberString);
                    raf.writeBytes(System.lineSeparator());
                    raf.close();
                    resultField.setText("Contacto añadido: " + newName);
                } else {
                    raf.close();
                    resultField.setText("El contacto ya existe.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                resultField.setText("Error.");
            }
        }

        else if (e.getActionCommand().equals("Listar contactos")) {
            try {
                // StringBuilder para construir la lista de contactos como texto CORRESPONDE A LA ACCIÓN READ
                StringBuilder contactList = new StringBuilder("Contactos:\n\n");

                // Crea un objeto File que representa el archivo "friendsContact.txt"
                File file = new File("friendsContact.txt");

                // Si el archivo no existe, informa al usuario y sale
                if (!file.exists()) {
                    resultField.setText("No se encontraron contactos.");
                    return;
                }

                // Crea un flujo de acceso aleatorio para leer el contenido del archivo
                RandomAccessFile raf = new RandomAccessFile(file, "r");

                // Recorre cada línea del archivo
                while (raf.getFilePointer() < raf.length()) {
                    // Lee una línea del archivo
                    String nameNumberString = raf.readLine();

                    // Divide la cadena para obtener el nombre y el número
                    String[] lineSplit = nameNumberString.split("!");

                    // Extrae el nombre y el número
                    String name = lineSplit[0];
                    long number = Long.parseLong(lineSplit[1]);

                    // Agrega los datos del contacto al StringBuilder con un salto de línea
                    contactList.append("Nombre: ").append(name).append("\n");
                    contactList.append("Número: ").append(number).append("\n\n");
                }

                // Establece el texto de resultField con la lista de contactos construida
                resultField.setText(contactList.toString());

                // Cierra los recursos
                raf.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                resultField.setText("Error.");
            } catch (NumberFormatException nef) {
                nef.printStackTrace();
                resultField.setText("Error.");
            }
        }

        else if (e.getActionCommand().equals("Actualizar contacto")) {
            // Obtiene el nombre y número actualizados del contacto CORRESPONDE A LA ACCIÓN UPDATE
            String newName = nameField.getText();
            long newNumber;

            try {
                newNumber = Long.parseLong(numberField.getText());
            } catch (NumberFormatException ex) {
                resultField.setText("Ingrese un número válido.");
                return;
            }

            try {
                // Crea un objeto File que representa el archivo "friendsContact.txt"
                File file = new File("friendsContact.txt");

                // Si el archivo no existe, informa al usuario y sale
                if (!file.exists()) {
                    resultField.setText("No se encontró el contacto.");
                    return;
                }

                // Crea un lector de archivos para leer el contenido del archivo
                BufferedReader reader = new BufferedReader(new FileReader(file));
                List<String> updatedLines = new ArrayList<>();
                String line;
                boolean found = false;

                // Recorre cada línea del archivo
                while ((line = reader.readLine()) != null) {
                    String[] lineSplit = line.split("!");
                    String name = lineSplit[0];
                    long number = Long.parseLong(lineSplit[1]);

                    // Comprueba si el nombre coincide con el del contacto a actualizar
                    if (name.equals(newName)) {
                        found = true;
                        updatedLines.add(newName + "!" + newNumber);
                    } else {
                        updatedLines.add(line);
                    }
                }

                // Cierra el lector de archivos
                reader.close();

                // Si no se encontró el contacto, informa al usuario
                if (!found) {
                    resultField.setText("Contacto no encontrado: " + newName);
                } else {
                    // Sobrescribe el archivo original con el contenido actualizado
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                    for (String updatedLine : updatedLines) {
                        writer.write(updatedLine);
                        writer.newLine();
                    }
                    // Cierra el escritor de archivos
                    writer.close();
                    resultField.setText("Contacto actualizado: " + newName);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                resultField.setText("Error.");
            }
        }

        else if (e.getActionCommand().equals("Eliminar contacto")) {
            // Obtiene el nombre del contacto a eliminar CORRESPONDE A LA ACCIÓN DELETE
            String nameToDelete = nameField.getText();
            boolean found = false;

            try {
                // Crea un objeto File que representa el archivo "friendsContact.txt"
                File file = new File("friendsContact.txt");

                // Si el archivo no existe, informa al usuario y sale
                if (!file.exists()) {
                    resultField.setText("No se encontraron contactos.");
                    return;
                }

                // Crea un flujo de acceso aleatorio para leer y escribir en el archivo
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                List<String> remainingLines = new ArrayList<>();
                String line;

                // Recorre cada línea del archivo
                while ((line = raf.readLine()) != null) {
                    String[] lineSplit = line.split("!");
                    if (lineSplit.length >= 1) {
                        String name = lineSplit[0];

                        // Comprueba si el nombre coincide con el del contacto a eliminar
                        if (name.equals(nameToDelete)) {
                            found = true;
                        } else {
                            remainingLines.add(line);
                        }
                    }
                }

                // Cierra el flujo de acceso aleatorio
                raf.close();

                // Si no se encontró el contacto, informa al usuario
                if (!found) {
                    resultField.setText("Contacto no encontrado: " + nameToDelete);
                } else {
                    // Sobrescribe el archivo original sin el registro eliminado
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                    for (String updatedLine : remainingLines) {
                        writer.write(updatedLine);
                        writer.newLine();
                    }
                    // Cierra el escritor de archivos
                    writer.close();
                    resultField.setText("Contacto eliminado: " + nameToDelete);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                resultField.setText("Error.");
            }
        }
        else if (e.getActionCommand().equals("Limpiar")) {
            // Limpia los campos de texto y el área de resultado
            nameField.setText("");
            numberField.setText("");
            resultField.setText("");
        }
        else if (e.getActionCommand().equals("Salir")) {
            // Sale de la aplicación
            System.exit(0);
        }
    }
    // Guarda la lista de contactos en el archivo utilizando serialización de objetos.
    private void saveContactsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(contactsFile))) {
            // Crea una nueva lista de contactos (puede estar vacía) y la escribe en el archivo
            List<Contacto> contacts= new ArrayList<>();
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }}
    // Lee la lista de contactos desde el archivo utilizando deserialización de objetos.
    private void readContactsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(contactsFile))) {
            // Lee la lista de contactos desde el archivo
            List<Contacto> contacts = (List<Contacto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}