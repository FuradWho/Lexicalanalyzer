package com.furad;


import java.io.*;

public class CifaChuli {
    private String path1;
    private String path2;
    private String[] keyWords = null;
    private static String[] arithmeticOperation = {"+", "-", "*", "/", "%", "++", "--"};
    private static String[] relationOperation = {"<", ">", "<=", ">=", "==", "!="};
    private static String[] bitOperation = {"&", "|", "^", "~", "<<", ">>", ">>>"};
    private static String[] logicOperation = {"&&", "||", "!"};
    private static String[] assignmentOperation = {"=", "+=", "-=", "/=", "*=", "%=", "<<=", ">>=", "&=", "^=", "|="};
    private static String[] noIdentifier = {"true", "false", "null"};
    private static String[] limiterWords = {".", ",", ";", "[", "]", "{", "}", "(", ")", "\"", "'", ":"};
    private static String[] unaryOperation = {"+", "-", "*", "/", "%", "<", ">", "&", "|", "^", "~", "!", "="};
    private static String[] binaryOperation = {"++", "--", "<=", ">=", "==", "!=", "<<", ">>", "&&", "||", "+=", "-=", "/=", "*=", "%=", "&=", "^=", "|="};
    private static String[] ternaryOperation = {">>>", "<<=", ">>="};


    public CifaChuli(String path1, String path2) {
        this.path1 = path1;
        this.path2 = path2;
        this.keyWords = getKeyWords(path1);
    }

    private static String[] getKeyWords(String path) {
        FileInputStream fileInputStream = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            fileInputStream = new FileInputStream(path);
            byte[] bytes = new byte[64];
            int readCount = 0;
            while ((readCount = fileInputStream.read(bytes)) != -1) {
                stringBuffer.append(new String(bytes, 0, readCount));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(stringBuffer).split(" ");
    }

    private int isNum(String s) {
        int flag = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                flag = 80;
            } else {
                flag = 0;
                break;
            }
        }
        return flag;
    }

    private int isLimiterWords(String s) {
        int flag = 0;
        for (int i = 0; i < limiterWords.length; i++) {
            if (limiterWords[i].equals(s)) {
                flag = 67 + i;
                break;
            }
        }
        return flag;
    }

    private int isOperation(String s) {
        int flag = 0;
        // 判断是否为算数运算符
        for (int i = 0; i < arithmeticOperation.length; i++) {
            if (arithmeticOperation[i].equals(s)) {
                flag = 33 + i;
                break;
            }
        }
        // 判断是否为关系运算符
        for (int j = 0; j < relationOperation.length; j++) {
            if (relationOperation[j].equals(s)) {
                flag = 40 + j;
                break;
            }
        }
        // 判断是否为位运算符
        for (int l = 0; l < bitOperation.length; l++) {
            if (bitOperation[l].equals(s)) {
                flag = 46 + l;
                break;
            }
        }
        // 判断是否为逻辑运算符
        for (int m = 0; m < logicOperation.length; m++) {
            if (logicOperation[m].equals(s)) {
                flag = 53 + m;
                break;
            }
        }
        // 判断是否为赋值运算符
        for (int n = 0; n < assignmentOperation.length; n++) {
            if (assignmentOperation[n].equals(s)) {
                flag = 56 + n;
                break;
            }
        }
        return flag;
    }

    private int isKeyWords(String s) {
        int flag = 0;
        for (int i = 0; i < keyWords.length; i++) {
            if (keyWords[i].equalsIgnoreCase(s)) {
                flag = i;
                break;
            }
        }
        return flag;
    }

    private int isNoIdentifier(String s) {
        int flag = 0;
        for (int i = 0; i < noIdentifier.length; i++) {
            if (noIdentifier[i].equals(s)) {
                flag = 80 + i;
                break;
            }
        }
        return flag;
    }

    private int isIdentifier(String s) {
        int flag = 0;
        if (s.matches("[a-zA-Z]+")) {
            if (isKeyWords(s) > 0) {
                flag = isKeyWords(s);
            } else if (isNoIdentifier(s) > 0) {
                flag = isNoIdentifier(s);
            } else {
                flag = 79;
            }
        } else if (s.matches("^[\\_a-zA-z][0-9a-zA-Z\\_]+")) {
            flag = 79;
        } else {
            flag = 0;
        }
        return flag;
    }

    private void printCode(int flag, String s) {
        if (flag>=1 && flag<=32){
            System.out.println("<  K " + " , " + s + " >");
        }else if(flag>32 && flag<=66){
            System.out.println("<  O " + " , " + s + " >");
        }else if(flag>67 && flag<=78){
            System.out.println("<  P " + " , " + s + " >");
        }else if(flag==79){
            System.out.println("<  I " + " , " + s + " >");
        }else if(flag==80){
            System.out.println("<  C " + " , " + s + " >");
        }else if(flag==82){
            System.out.println("<  L " + " , " + s + " >");
        }
    }

    public void splitString(String s) {
        int num = -1;
        String[] str = s.split("");
        for (int i = 0; i < str.length; i++) {
            if (i <= num) {
                continue;
            }
            num = -1;
            // 判断是否存在运算符
            for (int j = 0; j < unaryOperation.length; j++) {
                // 当匹配到为单元运算符时
                if (unaryOperation[j].equals(str[i])) {
                    // 判断是否到字符串结尾部分
                    if (i + 1 < s.length()) {
                        for (int l = 0; l < binaryOperation.length; l++) {
                            // 当匹配到为双元运算符时
                            if (binaryOperation[l].equals(s.substring(i, i + 2))) {
                                // 判断是否到字符串结尾部分
                                if (i + 2 < s.length()) {
                                    for (int m = 0; m < ternaryOperation.length; m++) {
                                        // 当匹配到三元运算符时
                                        if (ternaryOperation[m].equals(s.substring(i, i + 3))) {
                                            printCode(isOperation(s.substring(i, i + 3)), s.substring(i, i + 3));
                                            num = i + 2;
                                            break;
                                        }
                                    }
                                    // 不是三元运算符输出双元运算符
                                    if (num != i + 2) {
                                        printCode(isOperation(s.substring(i, i + 2)), s.substring(i, i + 2));
                                        num = i + 1;
                                        break;
                                    }
                                    // 字符串长度到底，直接输出双元运算符
                                } else {
                                    printCode(isOperation(s.substring(i, i + 2)), s.substring(i, i + 2));
                                    num = i + 1;
                                    break;
                                }
                            }
                        }
                        // 当没有匹配到双元或者三元运算符时，输出单元运算符
                        if (num == -1) {
                            printCode(isOperation(str[i]), str[i]);
                            num = 0;
                            break;
                        }
                        // 字符串长度到底，直接输出单元运算符
                    } else {
                        printCode(isOperation(str[i]), str[i]);
                        num = 0;
                        break;
                    }
                }
            }
            // 判断是否匹配到运算符，匹配到时值不为-1
            if (num != -1) {
                continue;
            }

            if (s.charAt(i) == '\\'){

            }

            // 判断是否为分界符
            if (isLimiterWords(str[i]) > 0) {
                printCode(isLimiterWords(str[i]), str[i]);
                continue;
            }
            // 判断是否存在数字
            else if (s.charAt(i) <= '9' && s.charAt(i) >= '0') {
                int n = i;
                boolean flag = false;
                for (int j = i + 1; j < str.length; j++) {
                    if (s.charAt(j) <= '9' && s.charAt(j) >= '0' || s.charAt(j)==':') {
                        if (s.charAt(j)==':'){
                           flag = true;
                        }
                        n = j;
                    } else {
                        break;
                    }
                }
                if (n > i) {
                    if (flag){
                        printCode(82, s.substring(i, n));
                        num = n-1;
                    }else{
                        printCode(80, s.substring(i, n+1));
                        num = n;
                    }
                    continue;
                } else {
                    printCode(80, str[i]);
                    continue;
                }
                // 判断是否存在标识符
                // 当字符为"_"开头时
            } else if (str[i] == "_") {
                int n = i;
                for (int j = i + 1; j < s.length(); j++) {
                    if (str[j] == "_"
                            || s.charAt(j) >= 'a' && s.charAt(j) <= 'z'
                            || s.charAt(j) >= 'A' && s.charAt(j) <= 'Z'
                            || s.charAt(j) <= '9' && s.charAt(j) >= '0') {
                        n = j;
                    } else {
                        break;
                    }
                }
                if (n > i) {
                    printCode(79, s.substring(i, n + 1));
                    num = n;
                    continue;
                } else {
                    printCode(79, str[i]);
                    continue;
                }
                // 判断是否存在标识符、特殊字符或者关键字
                // 当字符是字母开头时
            } else if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z'
                    || s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                int n = i;
                for (int j = i + 1; j < s.length(); j++) {
                    if (s.charAt(j) >= 'a' && s.charAt(j) <= 'z' || s.charAt(j) >= 'A' && s.charAt(j) <= 'Z') {
                        n = j;
                    } else {
                        break;
                    }
                }
                if (n > i) {
                    // 关键字
                    if (isKeyWords(s.substring(i, n + 1)) > 0) {
                        printCode(isKeyWords(s.substring(i, n + 1)), s.substring(i, n + 1));
                        num = n;
                        continue;
                    } else {
                        // 特殊字符
                        for (int j = 0; j < noIdentifier.length; j++) {
                            if (noIdentifier[j].equals(s.substring(i, n + 1))) {
                                printCode(99 + j, noIdentifier[j]);
                                num = n;
                                break;
                            }
                        }
                    }
                    if (num != n) {
                        // 标识符
                        printCode(79, s.substring(i, n + 1));
                        num = n;
                        continue;
                    } else {
                        continue;
                    }
                } else {
                    // 标识符
                    printCode(79, str[i]);
                    continue;
                }
            } else {
                System.out.println("<未知字符！," + str[i] + ">");
            }
        }
    }

    public void run() {
        String path = this.path2;
        String[] strings = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String strLine = null;
            int lineIndex = -1;
            int num = 0;
            int lastIndex = 0;
            int firstIndex = 0;
            while ((strLine = bufferedReader.readLine()) != null) {
                firstIndex = strLine.indexOf("/*");
                lastIndex = strLine.indexOf("*/");
                if (num == 0) {
                    if (firstIndex >= 0) {
                        strLine = strLine.substring(0, firstIndex);
                        num = 1;
                    }
                    // 等于1时，代表“/*”已经存在，匹配“*/”。
                } else if (num == 1) {
                    // 匹配不成功，跳出循环
                    if (lastIndex < 0) {
                        continue;
                        // 匹配成功，重新赋值0
                    } else {
                        strLine = strLine.substring(lastIndex + 2);
                        num = 0;
                    }
                }
                // 判断该行字符串是不是空格
                if (strLine.trim().isEmpty()) {
                    continue;
                }
                // 单行注释符的位置，index之后的为注释
                lineIndex = strLine.trim().indexOf("//");
                if (lineIndex < 0) {
                    if (strLine.endsWith("\\")){
                        strLine = strLine.substring(0,strLine.length()- 1) +bufferedReader.readLine();
                        strings = strLine.trim().split("\\s+");

                    }else{
                        strings = strLine.trim().split("\\s+");
                    }
                    // 注释符在开头直接跳出循环
                } else if (lineIndex == 0) {
                    continue;
                } else{
                    if(strLine.endsWith("\\")){
                        strLine = strLine+bufferedReader.readLine();
                        strings = strLine.trim().substring(0, lineIndex).split("\\s+");
                    }
                    strings = strLine.trim().substring(0, lineIndex).split("\\s+");
                }

                for (int i = 0; i < strings.length; i++) {
                    System.out.println("要分析的字符串： " + strings[i]);
                    if (isLimiterWords(strings[i]) > 0) {
                        printCode(isLimiterWords(strings[i]), strings[i]);
                    } else if (isNum(strings[i]) > 0) {
                        printCode(isNum(strings[i]), strings[i]);
                    } else if (isNoIdentifier(strings[i]) > 0) {
                        printCode(isNoIdentifier(strings[i]), strings[i]);
                    } else if (isKeyWords(strings[i]) > 0) {
                        printCode(isKeyWords(strings[i]), strings[i]);
                    } else if (isOperation(strings[i]) > 0) {
                        printCode(isOperation(strings[i]), strings[i]);
                    } else if (isIdentifier(strings[i]) > 0) {
                        printCode(isIdentifier(strings[i]), strings[i]);
                    } else {

                        splitString(strings[i]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
