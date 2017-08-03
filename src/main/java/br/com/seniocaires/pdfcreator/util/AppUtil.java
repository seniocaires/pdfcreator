package br.com.seniocaires.pdfcreator.util;

public class AppUtil {

  /**
   * Retorna uma String vazia ("") se o parâmetro passado for nulo. <br/>
   * Se o parâmetro não for nulo, retorna ele mesmo.
   * 
   * @author Senio Caires
   * @param valorString
   *          - Valor
   * @return vazio ("") ou valorString.
   */
  public static String nuloParaVazio(final String valorString) {
    String retorno;
    if (valorString == null) {
      retorno = "";
    } else {
      retorno = valorString;
    }
    return retorno;
  }
}
