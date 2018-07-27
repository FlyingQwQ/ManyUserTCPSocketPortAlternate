package win.simple;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

		//创建端口
        new portCreateThread(222);
        new portCreateThread(444);
        new portCreateThread(555);
    }
}
