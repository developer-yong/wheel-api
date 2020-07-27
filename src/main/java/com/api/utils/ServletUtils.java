package com.api.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Servlet工具类
 */
public class ServletUtils {

    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    public static void responseFile(HttpServletResponse response, File file) {
        responseFile(response, file, true);
    }

    public static void responseFile(HttpServletResponse response, File file, boolean delete) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Length", file.length() + "");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());

        BufferedInputStream in = null;
        try {
            OutputStream out = response.getOutputStream();
            in = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int i = in.read(buffer);
            while (i != -1) {
                out.write(buffer, 0, buffer.length);
                out.flush();
                i = in.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (delete && file.delete()) {
            System.out.println(file.getName() + "已删除");
        }
    }
}
