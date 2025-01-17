package U3_Red.tareas.tarea_1_adivina_numero;

import java.io.Serializable;

public class Acreditacion implements Serializable {

  private String nomUsuario;
  private String mensaje;
  private boolean valida;

  public String getNomUsuario() {
    return nomUsuario;
  }

  public void setNomUsuario(String nomUsuario) {
    this.nomUsuario = nomUsuario;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public boolean isValida() {
    return valida;
  }

  public void setValida(boolean valida) {
    this.valida = valida;
  }
}
