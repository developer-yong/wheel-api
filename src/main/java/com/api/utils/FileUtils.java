package com.api.utils;

import org.springframework.lang.NonNull;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 文件上传工具包
 */
public class FileUtils {

    /**
     * 创建文件
     *
     * @param parent 文件存放路径
     * @param name   文件名
     * @return 文件，文件路径无效时返回null
     */
    public static File create(String parent, String name) {
        if (!parent.endsWith("/")) {
            parent += "/";
        }
        return create(parent + name);
    }

    /**
     * 创建文件
     *
     * @param absolutePath 文件绝对路径
     * @return 文件，文件路径无效时返回null
     */
    public static File create(String absolutePath) {
        File file = new File(absolutePath);
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            if (!parentFile.mkdirs()) {
                return null;
            }
        }
        if (!file.exists()) {
            if (file.isDirectory() || absolutePath.endsWith("/")) {
                return file.mkdirs() ? file : null;
            } else {
                try {
                    return file.createNewFile() ? file : null;
                } catch (IOException e) {
                    return null;
                }
            }
        }
        return file;
    }

    /**
     * 检测文件是否存在
     *
     * @param parent 文件存放路径
     * @param name   文件名
     * @return {@link File#exists()}
     */
    public static boolean exists(String parent, String name) {
        return new File(parent, name).exists();
    }

    /**
     * 检测文件是否存在
     *
     * @param absolutePath 文件绝对路径
     * @return {@link File#exists()}
     */
    public static boolean exists(String absolutePath) {
        return new File(absolutePath).exists();
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return 是否删除
     */
    public static boolean delete(File file) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                return file.delete();
            } else if (file.isDirectory()) {
                //声明目录下所有的文件 files[];
                File[] files = file.listFiles();
                if (files != null) {
                    //遍历目录下所有的文件
                    for (File f : files) {
                        //把每个文件用这个方法进行迭代
                        delete(f);
                    }
                }
                //删除文件夹
                return file.delete();
            }
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param absolutePath 文件绝对路径
     * @return 是否删除
     */
    public static boolean delete(String absolutePath) {
        return delete(new File(absolutePath));
    }

    /**
     * 文件重命名
     *
     * @param file 原文件
     * @param name 新文件名
     * @return 新文件名
     */
    public static String rename(@NonNull File file, @NonNull String name) {
        File destFile = new File(file.getParent(), name);
        return file.renameTo(destFile) ? destFile.getName() : file.getName();
    }

    /**
     * 复制文件
     *
     * @param fromFile 原文件
     * @param toFile   新文件
     */
    public static void copy(@NonNull File fromFile, @NonNull File toFile) {
        try {
            FileInputStream ins = new FileInputStream(fromFile);
            FileOutputStream out = new FileOutputStream(toFile);
            byte[] b = new byte[1024];
            int n;
            while ((n = ins.read(b)) != -1) {
                out.write(b, 0, n);
            }
            ins.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件移动
     *
     * @param file 原文件
     * @param path 新文件路径
     */
    public static File move(@NonNull File file, @NonNull String path) {
        if (file.exists() && !path.equals(file.getParent())) {
            File destFile = new File(path, file.getName());
            if (!destFile.exists() || destFile.delete()) {
                return create(path) != null && file.renameTo(destFile) ? destFile : file;
            }
        }
        return file;
    }

    /**
     * 文件压缩
     *
     * @param zipPath   压缩文件路径
     * @param filePaths 要压缩的文件路径集合
     */
    public static File zip(@NonNull String zipPath, @NonNull String... filePaths) {
        ZipOutputStream zipOut = null;
        File zipFile = create(zipPath);
        try {
            //构造最终压缩包的输出流
            assert zipFile != null;
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            for (String path : filePaths) {
                File file = new File(path);
                if (file.exists()) {
                    compress(zipOut, file, file.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != zipOut) {
                    zipOut.flush();
                    zipOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return zipFile;
    }

    static void compress(ZipOutputStream zipOut, File file, String name) throws Exception {
        byte[] buf = new byte[1024];
        if (file.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zipOut.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(file);
            while ((len = in.read(buf)) != -1) {
                zipOut.write(buf, 0, len);
            }
            // Complete the entry
            zipOut.closeEntry();
            in.close();
        } else {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 空文件夹的处理
                zipOut.putNextEntry(new ZipEntry(file.getName() + "/"));
                // 没有文件，不需要文件的copy
                zipOut.closeEntry();
            } else {
                for (File f : listFiles) {
                    // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                    // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                    compress(zipOut, f, name + "/" + f.getName());
                }
            }
        }
    }

    /**
     * 解压文件到指定目录
     * 解压后的文件名，和之前一致
     *
     * @param zipFile 待解压的zip文件
     * @param descDir 指定目录
     */
    public static void unZip(File zipFile, String descDir) {
        try {
            //解决中文文件夹乱码
            ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
            String name = zip.getName().substring(zip.getName().lastIndexOf('\\') + 1, zip.getName().lastIndexOf('.'));
            File descFile = new File(descDir + name);
            if (descFile.exists() || descFile.mkdirs()) {
                Enumeration<? extends ZipEntry> entries = zip.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    String entryName = entry.getName();
                    InputStream in = zip.getInputStream(entry);
                    String outPath = (descDir + name + "/" + entryName).replaceAll("\\*", "/");

                    // 判断路径是否存在,不存在则创建文件路径
                    File outFile = new File(outPath);
                    if (outFile.getParentFile().exists() || outFile.getParentFile().mkdirs()) {
                        // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                        if (outFile.isDirectory() && (outFile.exists() || outFile.mkdir())) {
                            continue;
                        }

                        FileOutputStream out = new FileOutputStream(outFile);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                        in.close();
                        out.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件后缀
     *
     * @param path 文件绝对路径
     * @return 文件后缀
     */
    public static String getSuffix(String path) {
        if (path.contains(".")) {
            return path.substring(path.lastIndexOf(".")).toLowerCase();
        } else {
            return "";
        }
    }

    /**
     * 获取文件前缀
     *
     * @param path 文件绝对路径
     * @return 文件前缀
     */
    public static String getPrefix(String path) {
        if (path.contains(".")) {
            return path.substring(0, path.lastIndexOf("."));
        } else {
            return path;
        }
    }

    /**
     * 文件大小格式化
     *
     * @param length 文件字节长度
     * @return 格式字符串
     */
    public static String formatFileSize(long length) {
        DecimalFormat df = new DecimalFormat("#.00");
        String size;
        if (length < 1024) {
            size = df.format((double) length) + "B";
        } else if (length < 1048576) {
            size = df.format((double) length / 1024) + "K";
        } else if (length < 1073741824) {
            size = df.format((double) length / 1048576) + "M";
        } else {
            size = df.format((double) length / 1073741824) + "G";
        }
        return size;
    }
}