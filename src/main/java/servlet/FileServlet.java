package servlet;

import DAO.FileService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class FileServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String docType = "<!DOCTYPE html>";
        PrintWriter writer = response.getWriter();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File("1.txt"));
        factory.setSizeThreshold(1024 * 1024 * 3);
        ServletFileUpload upload = new ServletFileUpload(factory);


        try {
            List fileItems = upload.parseRequest(request);
            Iterator iterator = fileItems.iterator();
            writer.println(docType + "<html><head><title>File upload</title></head><body>");
            while (iterator.hasNext()) {
                FileItem fileItem = (FileItem) iterator.next();
                System.out.println(fileItem);
                File file = new File("1" + fileItem.getName());
                fileItem.write(file);
                FileService.save(file.getAbsolutePath());
                writer.println(fileItem.getName() + " is uploaded. <br>");
            }
            writer.println("</body></html>");
            writer.println("<div><a href=\"/user\">back</a></div>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
