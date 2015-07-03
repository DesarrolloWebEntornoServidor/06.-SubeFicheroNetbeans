package paqueteServlets; 

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/SubeFichero")

// @MultipartConfig (
//      location = "carpeta donde se guardan los archivos (creada previamente)", 
//      maxFileSize =  tamaño maximo de archivo a subir (entero largo)
// )

@MultipartConfig(location = "c:/upload", maxFileSize = 10485760L) // (Barra para escapar c:\\ , 10MB)

public class SubeFichero extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // Los ficheros se envían usando el método post y van en el cuerpo de la petición
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String descripcion = request.getParameter("descripcion"); // Recibe la descripción del archivo
        Part parteArchivo = request.getPart("archivo");   // Recibe el archivo en un objeto de tipo Part
        String nombreArchivo = parteArchivo.getSubmittedFileName(); // Extrae el nombre original del archivo del objeto Part
        parteArchivo.write(nombreArchivo);  // Guarda en el disco el archivo con el nombre original
                      
        long tamanio = parteArchivo.getSize();
        String tipo = parteArchivo.getContentType();

        // Creación del html de salida
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SubeFichero</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Operación realizada con éxito</h1>");
            out.println("<table border=1px>");
            out.println("<tr><td>Descripción del archivo</td><td>" + descripcion + "</td></tr>");
            out.println("<tr><td>Archivo subido  </td><td><b>C:\\upload\\" + nombreArchivo + "</b></td></tr>");
            out.println("<tr><td>Tamaño del archivo </td><td>" + tamanio + " bytes</td></tr>");
            out.println("<tr><td>Tipo de archivo </td><td>" + tipo + "</td></tr>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }
}