package br.com.seniocaires.pdfcreator.util;

import java.io.InputStream;

import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextUserAgent;

public class ResourceLoaderUserAgent extends ITextUserAgent {

  /**
   * @author Senio Caires
   * @param outputDevice
   *          -
   */
  public ResourceLoaderUserAgent(final ITextOutputDevice outputDevice) {
    super(outputDevice);
  }

  /**
   * @author Senio Caires
   * @param uri
   *          -
   * @return inputStream
   */
  public final InputStream resolveAndOpenStream(final String uri) {
    InputStream inputStream = super.resolveAndOpenStream(uri);
    if (inputStream == null && uri != null) {
      String[] separado = uri.split("/");
      String ultimaParte = separado[separado.length - 1];
      inputStream = ResourceLoaderUserAgent.class.getResourceAsStream(ultimaParte);
    }
    return inputStream;
  }
}
