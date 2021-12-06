package com.github.xucux.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/11/1 11:37
 * @version: 1.0
 */
@Slf4j
public class PathUtils {

    public static String getRoot(){
        File path = new File(PathUtils.class.getClassLoader().getResource("").getPath()) ;
        return path.getParentFile().getParent();
    }

    public static String getFiles(String filePath){
        String root = getRoot();
        return root.concat(File.separator)
                .concat("files")
                .concat(File.separator )
                .concat(filePath);
    }

    public static void createDir(String path){
        try {
            File file = new File(PathUtils.getFiles(path.concat(File.separator)));
            file.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 过滤文件夹获取文件
     * @param path
     * @return
     */
    public static List<File> getFileList(String path){
        String root = getRoot();
        String dirName = root.concat(File.separator)
                .concat(path)
                .concat(File.separator);
        File file = new File(dirName);
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            List<File> collect = Arrays.stream(files).map(s -> {
                if (!s.isDirectory()) {
                    return s;
                } else {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
            return collect;
        } else {
            log.error("该路径不是文件夹");
            return null;
        }
    }

}
