/**
 * @Description
 * @Author bin
 * @Create 2017/3/3
 **/
public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        String filePackage = "ioc.AnnotationBeanFactory";
        Class<?> clazz = Class.forName(filePackage);
        System.out.println(clazz);
        System.out.println(clazz.getSimpleName());
        String name = clazz.getSimpleName();
        System.out.println(name.replaceFirst(name, name.toLowerCase()));

        char[] chars = new char[1];
        String str = "ABCDE1234";
        chars[0] = str.charAt(0);
        String temp = new String(chars);
//        if (chars[0] >= 'A' && chars[0] <= 'Z') {//当为字母时，则转换为小写
//            System.out.println(str.replaceFirst(temp, temp.toLowerCase()));
//        }
    }
}
