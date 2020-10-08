package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    private String defaultFolder = "C:/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getParameter("path");
        path = path == null ? defaultFolder : path;


        Path spath = Paths.get(path);
        File file = spath.toFile();

        if (file.isFile()) {
            resp.setHeader("Content-Type", "application/octet-stream");
            resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

            FileInputStream inStream = new FileInputStream(file);
            OutputStream outStream = resp.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();
        } else {
            printDirectory(req, path == null ? defaultFolder : path);

            req.setAttribute("now", new Date());
            req.setAttribute("name", "Directories");
            req.getRequestDispatcher("explorer.jsp").forward(req, resp);
        }
    }

    private void printDirectory(HttpServletRequest req, String path) {
        StringBuilder attrFiles = new StringBuilder();
        StringBuilder attrFolders = new StringBuilder();

        if (path.contains("/"))
            addFile(true, attrFolders, path.substring(0, path.lastIndexOf('/')), "Назад", 0, 0);

        File[] files = new File(path).listFiles();

        if (files == null || files.length == 0)
            return;

        for (File file : files) {
            if (file.isDirectory())
                addFile(true, attrFolders, path + "/" + file.getName(), file.getName(), file.lastModified(), file.length());

            else
                addFile(false, attrFiles, path, file.getName(), file.lastModified(), file.length());
        }

        req.setAttribute("folders", attrFolders);
        req.setAttribute("files", attrFiles);
    }


    private void addFile(boolean isDir, StringBuilder attrFiles, String path, String text, long date, long length) {
        if (isDir) {
            attrFiles.append("<tr><td><img src=\"https://icons.iconarchive.com/icons/hopstarter/sleek-xp-basic/16/Folder-icon.png\"><a style=\"color: #080E73\t\" href=\"?path=")
                    .append(path);
        } else {
            attrFiles.append("<tr><td><a style=\"color: black\" href=\"?path=")
                    .append(path + "/" + text);
        }

        attrFiles.append("\">")
                .append(text)
                .append("</a></td><td>")
                .append(length)
                .append(" Bytes")
                .append("</td><td>")
                .append(new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(new Date(date)))
                .append("</td></tr>");
    }
}