package com.jeespring.common.utils;

import java.io.File;
import java.io.IOException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

/**
 * @author 压缩指定的目录以及解压指定的压缩文件(仅限ZIP格式).
 */
public class ZipCompressorByAnt {
    public final static String encoding = "GBK";

    /**
     * 1.可以压缩目录(支持多级)<br>
     * 2.可以压缩文件<br>
     * 3.如果压缩文件的路径或父路径不存在, 将会自动创建<br>
     * @param src
     *  将要进行压缩的目录
     * @param --最终生成的压缩文件的路径
     */
    public static void zip(File src, File dest) throws IOException {
        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(dest);
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        if (src.isFile()) {
            fileSet.setFile(src);
        } else {
            fileSet.setDir(src);
        }
        zip.addFileset(fileSet);
        zip.execute();
    }

    /**
     * 将指定的压缩文件解压到指定的目标目录下. 如果指定的目标目录不存在或其父路径不存在, 将会自动创建.
     * @param src 将会解压的压缩文件
     * @param dest 解压操作的目录目录
     */
    public static void unzip(File src, File dest) throws IOException {
        Project proj = new Project();
        Expand expand = new Expand();
        expand.setProject(proj);
        expand.setTaskType("unzip");
        expand.setTaskName("unzip");
        expand.setSrc(src);
        expand.setDest(dest);
        expand.setEncoding(encoding);//设置编码不能少，少了文件名会有乱码
        expand.execute();
    }

    public static void main(String[] args) {
        try {
            ZipCompressorByAnt
                    .zip(
                            new File(
                                    "H:\\work\\926\\src"),
                            new File(
                                    "H:\\work\\926\\dest\\问卷调查.zip"));

            ZipCompressorByAnt
                    .unzip(new File(
                            "H:\\work\\926\\dest\\问卷调查.zip"),new File(
                            "H:\\work\\926\\"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}