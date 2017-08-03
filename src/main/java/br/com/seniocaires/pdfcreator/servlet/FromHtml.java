package br.com.seniocaires.pdfcreator.servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import br.com.seniocaires.pdfcreator.util.AppUtil;
import br.com.seniocaires.pdfcreator.util.ResourceLoaderUserAgent;

public class FromHtml extends HttpServlet {

  /**
   * @author Senio Caires
   */
  private static final long serialVersionUID = 1L;

  /**
   * @author Senio Caires
   */
  public FromHtml() {
  }

  /**
   * @author Senio Caires
   * @param request
   *          - Request
   * @param response
   *          - Response
   * @throws ServletException
   *           -
   * @throws IOException
   *           -
   */
  public final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    OutputStream out = response.getOutputStream();
    try {
      response.setContentType("application/pdf");
      ITextRenderer renderer = new ITextRenderer();
      ResourceLoaderUserAgent callback = new ResourceLoaderUserAgent(renderer.getOutputDevice());
      callback.setSharedContext(renderer.getSharedContext());
      renderer.getSharedContext().setUserAgentCallback(callback);
      Tidy tidy = new Tidy();
      tidy.setShowErrors(0);
      tidy.setQuiet(true);
      tidy.setInputEncoding("UTF-8");
      tidy.setOutputEncoding("UTF-8");
      tidy.setWraplen(Integer.MAX_VALUE);
      tidy.setPrintBodyOnly(true);
      tidy.setXmlOut(true);
      tidy.setSmartIndent(true);
      ByteArrayInputStream inputStream = new ByteArrayInputStream(AppUtil.nuloParaVazio(request.getParameter("html")).getBytes("UTF-8"));
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      Document documento = tidy.parseDOM(inputStream, outputStream);
      renderer.setDocument(documento, "file:///" + request.getSession().getServletContext().getRealPath(File.separator));
      renderer.layout();
      renderer.createPDF(out);
    } catch (Exception exception) {
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, exception.getMessage(), exception);
    } finally {
      out.close();
    }
  }

  /**
   * @author Senio Caires
   * @param request
   *          - Request
   * @param response
   *          - Response
   * @throws ServletException
   *           -
   * @throws IOException
   *           -
   */
  public final void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
