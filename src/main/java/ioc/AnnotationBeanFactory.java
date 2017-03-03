package ioc;

import ioc.annotation.BService;
import ioc.service.UserService;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注解方式bean工厂
 *
 * @author bin
 * @version 1.0 2017/3/3
 **/
public class AnnotationBeanFactory {

    private static Map<String, Object> beanMap = new HashMap<String, Object>(); //bean容器

    /**
     * 扫描包
     *
     * @param iPackage 包名
     */
    public static void scanPackage(String iPackage) {
        String path = iPackage.replace(".", "/");
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);//转成文件路径,/Users/app/develop/github/sample/target/classes/ioc
        try {
            if (url != null && url.toString().startsWith("file")) {
                String filePath = URLDecoder.decode(url.getFile(), "utf-8");
                File dir = new File(filePath);
                List<File> fileList = new ArrayList<File>();
                fetchFileList(dir, fileList);//获取路径下所有class文件
                for (File f : fileList) {
                    String fileName = f.getAbsolutePath();
                    if (fileName.endsWith(".class")) {
                        String nosuffixFileName = fileName.substring(8 + fileName.lastIndexOf("classes"), fileName.indexOf(".class"));
                        String filePackage = nosuffixFileName.replaceAll("/", ".");
                        Class<?> clazz = Class.forName(filePackage);

                        BService service = clazz.getAnnotation(BService.class);
                        if (service != null) {
                            Object obj = null;
                            try {
                                obj = clazz.newInstance();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            String name = service.name();
                            if (name == null || "".equals(name)) {//Service-bean默认名称为类名,首字母小写
                                name = clazz.getSimpleName();
                                char[] chars = new char[1];
                                chars[0] = name.charAt(0);
                                String temp = new String(chars);
                                name = name.replaceFirst(temp, temp.toLowerCase());
                            }
                            beanMap.put(name, obj);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fetchFileList(File dir, List<File> fileList) {
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                fetchFileList(f, fileList);
            }
        } else {
            fileList.add(dir);
        }
    }

    public static void main(String[] args) {
        scanPackage("ioc");
        UserService service = (UserService) beanMap.get("userService");
        System.out.println(service.getUser().getName());
    }
}
