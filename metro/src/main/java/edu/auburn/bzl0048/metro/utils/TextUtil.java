package edu.auburn.bzl0048.metro.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liangboning
 * @date 2019/7/22 12:43
 */
public class TextUtil {

    public TextUtil(String rootPath) {
        setRootPath(rootPath);
        setOutPutRootPath(rootPath);
        fileNameList = new ArrayList<>();
        content = new HashMap<>();
    }

    private String rootPath;

    private String outPutRootPath;

    private List<String> fileNameList;

    private Map<String, String> content;

    public void listFileNames() {
        fileNameList.forEach(System.out::println);
    }

    public void getFileNames() {
        File foler = new File(rootPath);
        File[] files = foler.listFiles();
        for (File file: files) {
            fileNameList.add(file.getName().replaceAll(" ", ""));
        }
    }

    public void printFilesEncoding() {
        File foler = new File(rootPath);
        File[] files = foler.listFiles();
        for (File file: files) {
            System.out.println(resolveFileEncoding(file));
        }
    }

    public void printOutputFilesEncoding() {
        File foler = new File(outPutRootPath);
        File[] files = foler.listFiles();
        for (File file: files) {
            System.out.println(file.getName() + ": " + resolveFileEncoding(file));
        }
    }


    public void getContent() {
        String lineSeparator = System.getProperty("line.separator");
        File foler = new File(rootPath);
        File[] files = foler.listFiles();
        for (File file: files) {
            String fileEncoding = resolveFileEncoding(file);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), fileEncoding));
//                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + lineSeparator);
                }
                content.put(changeCharsetToUtf8(outPutRootPath+"\\"+ file.getName()), changeCharsetToUtf8(stringBuilder.toString()));
                reader.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveContentToUtf8Files2() {
        content.forEach((key, value) -> {
            try {
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(key), Charset.forName("UTF-8")));
                BufferedWriter writer = new BufferedWriter(new FileWriter(key));
                writer.write(value);
                writer.close();
                File outputFile2 = new File(key);
                System.out.println(key + ": " + resolveFileEncoding(outputFile2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void saveContentToUtf8Files() {
        content.forEach((key, value) -> {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(key), Charset.forName("UTF-8")));
                bufferedWriter.write(value);
                bufferedWriter.close();
                File outputFile2 = new File(key);
                System.out.println(key + ": " + resolveFileEncoding(outputFile2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void saveUTF8Files() {
        String lineSeparator = System.getProperty("line.separator");
        File foler = new File(rootPath);
        File[] files = foler.listFiles();
        for (File file: files) {
            String outputFileName = changeCharsetToUtf8(outPutRootPath+"\\"+ file.getName());
            File outputFile = new File(outputFileName);
            String fileEncoding = resolveFileEncoding(file);
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), Charset.forName("UTF-8")));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName(fileEncoding)));
//                char[] head = new char[] {0xEF, 0xBB, 0xBF};
//                bufferedWriter.write(head, 0, 3);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(changeCharsetToUtf8(line) + lineSeparator);
                }
                if (!fileEncoding.equals("UTF-8")) {
                    System.out.println(stringBuilder.toString());
                }
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.close();
                bufferedReader.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            if (!fileEncoding.equals("UTF-8")) {
                File outputFile2 = new File(outputFileName);
                System.out.println(outputFile2 + ": " + fileEncoding + " -> " +resolveFileEncoding(outputFile2));
            }
        }
    }

    private String resolveFileEncoding(File file){
        String code = "GBK";
        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            byte[] head = new byte[4];
            bufferedInputStream.read(head, 0, 4);
            if (head[0] == (byte) 0xFF && head[1] == (byte) 0xFE) {
                code = "UTF-16LE";
            } else if (head[0] == (byte) 0xFE && head[1] == (byte) 0xFF) {
                code = "UTF-16BE";
            } else if (head[0] == (byte) 0xEF && head[1] == (byte) 0xBB && head[2] == (byte) 0xBF) {
                code = "UTF-8";
            } else if (head[0] == (byte) 0xFF && head[1] == (byte) 0xFE && head[2] == (byte) 0x00 && head[3] == (byte) 0x00) {
                code = "UTF-32LE";
            } else if (head[0] == (byte) 0x00 && head[1] == (byte) 0x00 && head[2] == (byte) 0xFE && head[3] == (byte) 0xFF){
                code = "UTF-32BE";
            }
            return code;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void printAllOutput() {
        content.forEach((key, value) -> {
            File file = new File(key);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line + "\n");
                }
                System.out.println("\n\n\n\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


//    private String resolveFileEncoding(File file){
//        String code = "GBK";
//        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
//            byte[] head = new byte[3];
//            bufferedInputStream.read(head, 0, 3);
//            if (head[0] == (byte) 0xFF && head[1] == (byte) 0xFE) {
//                code = "UTF-16";
//            } else if (head[0] == (byte) 0xFE && head[1] == (byte) 0xFF) {
//                code = "Unicode";
//            } else if (head[0] == (byte) 0xEF && head[1] == (byte) 0xBB && head[2] == (byte) 0xBF) {
//                code = "UTF-8";
//            }
//            return code;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    private static String changeCharsetToUtf8(String str) {
        try {
            if (str != null) {
                byte[] bytes = str.getBytes();
                return new String(bytes, Charset.forName("UTF-8"));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getOutPutRootPath() {
        return outPutRootPath;
    }

    public void setOutPutRootPath(String outPutRootPath) {
        this.outPutRootPath = outPutRootPath + "_output";
    }
}
