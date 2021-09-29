package com.taskagile.infrastructure.file.local;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@WebServlet("/local-file/*")
public class LocalFileServlet extends HttpServlet {

    private String localRootPath;
    private Environment environment;

    public LocalFileServlet(@Value("${app.file-storage.local-root-folder}") String localRootPath,
                            Environment environment) {
        this.environment = environment;
        this.localRootPath = localRootPath;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (environment.acceptsProfiles("production", "staging")) {
            String activeProfiles = String.join(", ", environment.getActiveProfiles());
            log.warn("Access `{}` in environment `{}`. IP address : `{}` ", req.getPathInfo(), activeProfiles, req.getRemoteAddr());
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo.equals("/")) {
            resp.getWriter().write("/");
            return;
        }

        String filePath = localRootPath + req.getPathInfo();
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            resp.sendError(404);
            return;
        }

        resp.setContentType(req.getServletContext().getMimeType(pathInfo));
        resp.setHeader("Cache-Control", "public, max-age=31536000");
        Files.copy(Paths.get(localRootPath, pathInfo), resp.getOutputStream());
    }
}
