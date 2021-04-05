import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DecodeFile {
    private static Map<String, String> prefixCodeTale = new HashMap<>();


    DecodeFile() {

        Scanner scan = new Scanner(System.in);

        while(scan.next().equals("****")){
            var cods = scan.next();
            var characters = scan.next();

            System.out.println(cods + " "+ characters);
        }

//        File path = new File("palindrome.zip301");
//        FileInputStream file = new FileInputStream(path);
//
//        String input = "";
//        int i;
//        while((int i = file.read()) != -1){
//
//        }

    }



//    public static String decode(String encodeData){
//        StringBuilder sb = new StringBuilder();
//        String tmp = "";
//        for(char c:encodeData.toCharArray()){
//            tmp += c;
//        }
//    }

    public static void main(String[] argv) {
        new DecodeFile();

    }
}
