package com.github.xucux;

import com.github.xucux.util.PathUtils;
import com.github.xucux.util.ThumbUtils;

import java.io.File;
import java.util.List;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/11/2 16:08
 * @version: 1.0
 */
public class Test {

    public static void main(String [] args){
//        ThumbUtils.compressLocal(PathUtils.getFiles("test001.jpg"),PathUtils.getFiles("test001_q_0.1.jpg"),0.3f);
        List<File> files = PathUtils.getFileList("files");
        files.stream().forEach(s -> {
            String name = s.getName();
            String suffix = name.substring(name.lastIndexOf("."));
            String prefix = name.substring(0, name.lastIndexOf("."));
            String q = "0.5";
            PathUtils.createDir(q);
            ThumbUtils.compressLocal(s.getAbsolutePath(),PathUtils.getFiles(q.concat(File.separator).concat(prefix+"_"+q+suffix)),Float.valueOf(q));
        });

    }

}
