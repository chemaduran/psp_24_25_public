package U3_Red.clase.tarea_2_adivina_multihilo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Partida {
    private static ConcurrentHashMap<String, Integer> participantes = new ConcurrentHashMap<>();
    private AtomicBoolean finPartida = new AtomicBoolean(false);
    private AtomicInteger numDeJugadas = new AtomicInteger(1);
    private int numSecreto = aleatorio(1, 100);

    public static Map<String, Integer> getParticipantes() {
        return participantes;
    }

    public static void setParticipantes(ConcurrentHashMap<String, Integer> participantes) {
        Partida.participantes = participantes;
    }

    public AtomicBoolean isFinPartida() {
        return finPartida;
    }

    public void setFinPartida(boolean finPartida) {
        this.finPartida.set(finPartida);
    }

    public AtomicInteger getNumDeJugadas() {
        return numDeJugadas;
    }

    public void setNumDeJugadas(AtomicInteger numDeJugadas) {
        this.numDeJugadas = numDeJugadas;
    }

    public int getNumSecreto() {
        return numSecreto;
    }

    public void setNumSecreto(int numSecreto) {
        this.numSecreto = numSecreto;
    }

    private static int aleatorio(int min, int max) {
        Random r = new Random();
        return min + r.nextInt(max - min + 1); // Entre min y max -> [min,max];
    }


    public static boolean validarJugador(String nombreJugador) {
        boolean jugadorValido = true;
        String key = nombreJugador.toUpperCase();
        if (participantes.containsKey(key)) {
            if (participantes.get(key) > 2) {
                jugadorValido = false;
            } else {
                participantes.replace(key, participantes.get(key) + 1);
            }
        } else {
            participantes.put(key, 1);
        }
        return jugadorValido;
    }
}

