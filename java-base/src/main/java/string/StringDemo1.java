package string;

public class StringDemo1 {
    public static void main(String[] args) {
//        字符串的创建
//        String s1 = "hello";
//        String s2 = new String("hello");
//        System.out.println(s1);
//        System.out.println(s2);

//        字符串长度
//        String s1 = "hello";
//        System.out.println(s1.length());

//        获取字符
//        String s1 = "hello";
//        System.out.println(s1.charAt(0));

//        比较字符串
//        String s1 = "hello";
//        String s2 = "Hello";
//        System.out.println(s1.equals(s2));
//        System.out.println(s1.equalsIgnoreCase(s2));

//        比较字符串顺序
//        String s1 = "Hello， World";
//        System.out.println(s1.compareTo("hello， world"));

//        String s1 = "Hello， World";
//        boolean contains = s1.contains("World");
//        System.out.println(contains);

//        查找字符串的位置
        String str1 = " Hello， World ";
//        int index = str1.indexOf("World"); // 7
//        int lastIndex = str1.lastIndexOf("o"); // 8
//        System.out.println(index);
//        System.out.println(lastIndex);

//        截取字符串
        System.out.println(str1.substring(7));

//        去除空白字符
        System.out.println(str1.trim());

//        转换大小写
        System.out.println(str1.toUpperCase());

        Character ch= 'a';
        System.out.println(ch.isUpperCase(ch));
        System.out.println(ch.isLowerCase(ch));
        System.out.println(ch.isDigit(ch));

//        字符串连接
        String s1 = "hello";
        String s2 = "world";
        System.out.println(s1.concat(s2));

//        将字符串转换为数字
        String s3 = "123";
        int num = Integer.parseInt(s3);
        System.out.println(num);

//        将数字转换为字符串
        int num2 = 123;
        String s4 = String.valueOf(num2);
        System.out.println(s4);
    }
}
