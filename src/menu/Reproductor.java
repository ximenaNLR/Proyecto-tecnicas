
package menu;

import jaco.mp3.player.MP3Player;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Reproductor {
    public static Scanner sc = new Scanner(System.in);
    private List<ListaDeReproduccion> listasDeReproduccion;
    public static Biblioteca biblio = new Biblioteca();
    private MP3Player mp3Player;
    private int volumen;

    public Reproductor() {
        this.listasDeReproduccion = new ArrayList<>();
        this.mp3Player = new MP3Player();
        this.volumen = 50;
    }

    public void reproducirCancion(Cancion cancion) {
        mp3Player.stop();
        mp3Player = new MP3Player(new File(cancion.getRutaMP3()));
        mp3Player.play();
        System.out.println("Reproduciendo: " + cancion.getNombre());
    }

    public void agregarCancionAReproductor(Cancion cancion) {
        mp3Player.addToPlayList(new File(cancion.getRutaMP3()));
    }

    public void reproducirListaCompleta() {
        mp3Player.play();
        System.out.println("Reproduciendo lista completa.");
    }

    public void configurarRepeticionYAleatorizacion(boolean repetir, boolean aleatorio) {
        mp3Player.setRepeat(repetir);
        mp3Player.setShuffle(aleatorio);
        System.out.println("Configuración de reproducción: Repetir - " + repetir + ", Aleatorio - " + aleatorio);
    }

    public static void crearCancion() {
        System.out.println("Ingrese el nombre de la canción: ");
        String nombre = sc.nextLine();
        System.out.println("Ingrese el nombre del álbum: ");
        String album = sc.nextLine();
        System.out.println("Ingrese el nombre del artista: ");
        String artista = sc.nextLine();
        System.out.println("Ingrese el género: ");
        String genero = sc.nextLine();
        System.out.println("Ingrese la ruta del archivo MP3: ");
        String rutaMP3 = sc.nextLine();
        Cancion cancion = new Cancion(nombre, album, artista, genero, rutaMP3);
        biblio.agregarCancion(cancion);
        System.out.println("Canción agregada correctamente a la biblioteca.");
    }

    public void listarCanciones() {
        List<Cancion> canciones = biblio.obtenerTodasLasCanciones();
        for (int i = 0; i < canciones.size(); i++) {
            System.out.println(i + ". " + canciones.get(i).getNombre());
        }
    }

    public void reproducirCanciones() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Reproducir una canción específica");
        System.out.println("2. Reproducir todas las canciones de la biblioteca");
        int opcion = Integer.parseInt(sc.nextLine());

        if (opcion == 1) {
            System.out.println("Seleccione una canción para reproducir:");
            listarCanciones();
            int indice = Integer.parseInt(sc.nextLine());
            List<Cancion> canciones = biblio.obtenerTodasLasCanciones();
            if (indice >= 0 && indice < canciones.size()) {
                Cancion cancion = canciones.get(indice);
                reproducirCancion(cancion);
            } else {
                System.out.println("Índice de canción inválido.");
            }
        } else if (opcion == 2) {
            List<Cancion> canciones = biblio.obtenerTodasLasCanciones();
            for (Cancion cancion : canciones) {
                agregarCancionAReproductor(cancion);
            }
            System.out.println("¿Desea repetir la lista? (si/no)");
            boolean repetir = sc.nextLine().equalsIgnoreCase("si");
            System.out.println("¿Desea reproducir de forma aleatoria? (si/no)");
            boolean aleatorio = sc.nextLine().equalsIgnoreCase("si");

            configurarRepeticionYAleatorizacion(repetir, aleatorio);
            reproducirListaCompleta();
        } else {
            System.out.println("Opción no válida.");
        }
    }

    public void salirDeAplicacion() {
        System.out.println("Saliendo de la aplicación...");
        System.exit(0);
    }

    public void buscarCanciones() {
        System.out.print("Ingrese el criterio de búsqueda (nombre, artista, álbum, género): ");
        String criterio = sc.nextLine();
        List<Cancion> cancionesEncontradas = biblio.buscarCanciones(criterio);
        if (cancionesEncontradas.isEmpty()) {
            System.out.println("No se encontraron canciones.");
        } else {
            for (Cancion cancion : cancionesEncontradas) {
                System.out.println("Encontrada: " + cancion);
            }
        }
    }

    public void avanzarYRetroceder() {
        System.out.println("¿Desea avanzar o retroceder en la canción? (Avanzar/Retroceder)");
        String opcion = sc.nextLine();
        if (opcion.equalsIgnoreCase("Avanzar")) {
            System.out.println("Avanzando en la canción...");
        } else if (opcion.equalsIgnoreCase("Retroceder")) {
            System.out.println("Retrocediendo en la canción...");
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private boolean enPausa = false;

    public void pausarYReanudar() {
        System.out.println("¿Desea pausar o reanudar la reproducción? (Pausar/Reanudar)");
        String opcion = sc.nextLine();
        if (opcion.equalsIgnoreCase("Pausar")) {
            if (!enPausa) {
                mp3Player.pause();
                System.out.println("Reproducción pausada.");
                enPausa = true;
            } else {
                System.out.println("La reproducción ya está en pausa.");
            }
        } else if (opcion.equalsIgnoreCase("Reanudar")) {
            if (enPausa) {
                mp3Player.play();
                System.out.println("Reproducción reanudada.");
                enPausa = false;
            } else {
                System.out.println("La reproducción no está en pausa.");
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    public void controlarVolumen() {
        // Implementar lógica de control de volumen si es necesario
    }

    public void crearListaDeReproduccion() {
        System.out.println("Ingrese el nombre de la lista de reproducción:");
        String nombre = sc.nextLine();
        ListaDeReproduccion lista = new ListaDeReproduccion(nombre);
        listasDeReproduccion.add(lista);
        System.out.println("Lista de reproducción creada.");
    }

    public void agregarCancionALista() {
        System.out.println("Seleccione una lista de reproducción:");
        listarListasDeReproduccion();
        int indiceLista = Integer.parseInt(sc.nextLine());
        if (indiceLista >= 0 && indiceLista < listasDeReproduccion.size()) {
            ListaDeReproduccion lista = listasDeReproduccion.get(indiceLista);
            System.out.println("Seleccione una canción para agregar:");
            listarCanciones();
            int indiceCancion = Integer.parseInt(sc.nextLine());
            List<Cancion> canciones = biblio.obtenerTodasLasCanciones();
            if (indiceCancion >= 0 && indiceCancion < canciones.size()) {
                Cancion cancion = canciones.get(indiceCancion);
                lista.agregarCancion(cancion);
                System.out.println("Canción agregada a la lista de reproducción.");
            } else {
                System.out.println("Índice de canción inválido.");
            }
        } else {
            System.out.println("Índice de lista de reproducción inválido.");
        }
    }

    public void listarListasDeReproduccion() {
        for (int i = 0; i < listasDeReproduccion.size(); i++) {
            System.out.println(i + ". " + listasDeReproduccion.get(i).getNombre());
        }
    }

    public void eliminarCancion() {
        System.out.println("Seleccione una canción para eliminar:");
        List<Cancion> canciones = biblio.obtenerTodasLasCanciones();
        for (int i = 0; i < canciones.size(); i++) {
            System.out.println(i + ". " + canciones.get(i).getNombre());
        }
        int indice = Integer.parseInt(sc.nextLine());
        if (indice >= 0 && indice < canciones.size()) {
            Cancion cancion = canciones.get(indice);
            biblio.eliminarCancion(cancion);
            System.out.println("Canción eliminada correctamente.");
        } else {
            System.out.println("Índice de canción inválido.");
        }
    }

    public void ordenarCanciones() {
        List<Cancion> canciones = biblio.obtenerTodasLasCanciones();
        Collections.sort(canciones, new Comparator<Cancion>() {
            @Override
            public int compare(Cancion c1, Cancion c2) {
                return c1.getNombre().compareTo(c2.getNombre());
            }
        });
        System.out.println("Canciones ordenadas:");
        for (Cancion cancion : canciones) {
            System.out.println(cancion);
        }
    }
}
