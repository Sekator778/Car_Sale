package servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * the servlet for uploading the picture.
 */
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        File repository = (File) session.getServletContext().getAttribute("javax.servlet.context.tempdir");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        FileItem pic = null;
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    pic = item;
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        String newFilePath = "";
        File folder = new File(repository + "/images");
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (pic != null) {
            String filePath = folder + File.separator + pic.getName();
            int i = 0;
            newFilePath = filePath;
            File file = new File(newFilePath);
            while (file.exists()) {
                i++;
                newFilePath = folder + File.separator + i + "_" + pic.getName();
                file = new File(newFilePath);
            }
            try (FileOutputStream out = new FileOutputStream(file)) {
                out.write(pic.getInputStream().readAllBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        JSONObject status = new JSONObject();
        status.put("pic", newFilePath);
        writer.append(status.toString());
        writer.flush();
    }
}