package org.example.calcite.test;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ResUtil {

    /**
     * 读取配置文件
     * 举个例子，假设我们在classpath下放了一个test.xml，读取就变得非常简单：
     * String str = ResourceUtil.readUtf8Str("test.xml");
     * 假设我们的文件存放在src/resources/config目录下，则读取改为：
     * String str = ResourceUtil.readUtf8Str("config/test.xml");
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static String getResourceAsString(String filename) throws IOException {
        String str = ResourceUtil.readUtf8Str(filename);
        String s = str.replaceAll("\r\n", "");

        return s;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getResourceAsString("model.json"));
    }
}
