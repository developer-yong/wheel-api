package com.api.core.utils;

import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * @author coderyong
 */
public class Logger {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_LEFT_CORNER = '╟';
//    private static final char TOP_RIGHT_CORNER = '╗';
//    private static final char MIDDLE_RIGHT_CORNER = '╢';
//    private static final char BOTTOM_RIGHT_CORNER = '╝';
//    private static final char SPACE = ' ';
    private static final char HORIZONTAL_LINE = '║';
    private static final char DOUBLE_DIVIDER = '═';
    private static final char SINGLE_DIVIDER = '─';

    public static boolean ENABLE = true;
    public static boolean T_ENABLE = true;
    public static boolean D_ENABLE = true;
    public static boolean I_ENABLE = true;
    public static boolean W_ENABLE = true;
    public static boolean E_ENABLE = true;

    private Logger() {
        throw new UnsupportedOperationException("Cannot be created");
    }

    public static void t(String... msg) {
        if (ENABLE && T_ENABLE) {
            LOGGER.trace(createLogMessage(msg));
        }
    }

    public static void d(String... msg) {
        if (ENABLE && D_ENABLE) {
            LOGGER.debug(createLogMessage(msg));
        }
    }

    public static void i(String... msg) {
        if (ENABLE && I_ENABLE) {
            LOGGER.info(createLogMessage(msg));
        }
    }

    public static void w(String... msg) {
        if (ENABLE && W_ENABLE) {
            LOGGER.warn(createLogMessage(msg));
        }
    }

    public static void w(String msg, Throwable tr) {
        if (ENABLE && W_ENABLE) {
            LOGGER.warn(createLogMessage(msg, getStackTraceString(tr)));
        }
    }

    public static void e(String... msg) {
        if (ENABLE && E_ENABLE) {
            LOGGER.error(createLogMessage(msg));
        }
    }

    public static void e(String msg, Throwable tr) {
        if (ENABLE && E_ENABLE) {
            LOGGER.error(createLogMessage(msg, getStackTraceString(tr)));
        }
    }

    private static String getUseMethodName() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int usePosition = 3;
        for (int i = 0; i < trace.length; i++) {
            String className = trace[i].getClassName();
            if (className.contains(Logger.class.getName())) {
                usePosition += i;
                break;
            }
        }
        return trace[usePosition].getClassName() +
                "." +
                trace[usePosition].getMethodName() +
                "  (" +
                trace[usePosition].getFileName() +
                ":" +
                trace[usePosition].getLineNumber() +
                ")";
    }

    /**
     * 获取堆栈跟踪字符串
     *
     * @param tr 错误信息
     * @return 格式化字符串
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of createLogMessage spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * 创建日志消息
     *
     * @param msg 打印的消息内容
     * @return 带有格式的日志消息
     */
    private static String createLogMessage(String... msg) {
        String methodName = getUseMethodName();
        StringBuilder message = new StringBuilder();
        int maxLength = methodName.length() + 8;
        if (msg != null) {
            for (String m : msg) {
                if (m != null) {
                    String[] lines = m.split(System.getProperty("line.separator"));
                    for (String line : lines) {
                        if (line.length() > maxLength) {
                            maxLength = (8 * getTabsSize(line)) + line.length();
                        }
                        message.append(HORIZONTAL_LINE + "\t").append(line).append("\n");
                    }
                }
            }
        }

        String topBorder = TOP_LEFT_CORNER + appendDivider(DOUBLE_DIVIDER, maxLength);
        String middleBorder = MIDDLE_LEFT_CORNER + appendDivider(SINGLE_DIVIDER, maxLength);
        String bottomBorder = BOTTOM_LEFT_CORNER + appendDivider(DOUBLE_DIVIDER, maxLength);

        return "\n" +
                topBorder + "\n" +
                HORIZONTAL_LINE + "\t" +
                methodName + "\n" +
                middleBorder + "\n" +
                message.toString() +
                bottomBorder;
    }

    private static int getTabsSize(String line) {
        int size = 1;
        int i = 0;
        while (i < line.length() && line.startsWith("\t")) {
            if (line.charAt(i) != '\t') {
                size = i + 1;
                break;
            }
            i++;
        }
        return size;
    }

    private static String appendDivider(char divider, int length) {
        StringBuilder dividers = new StringBuilder();
        for (int i = 0; i < length; i++) {
            dividers.append(divider);
        }
        return dividers.toString();
    }
}