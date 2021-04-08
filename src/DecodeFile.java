import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Final Project-decompressing the zip301 file.
 * By.Sungjin Kwon, Sungjoon Ha
 *
 * Methods Explaination....
 * buildTree(method): building Huffman Tree.
 * deencoding(method): decoding the text file. It recieve the binarycode and read by character by character. and when it find
 * the value in the HASHMAP, it change it to char(key) value.
 * getKeysByValue(method): getting key value in the HASHMAP using STREAM.
 */

public class DecodeFile {
    private static Map<Character, String> prefixCodeTale = new HashMap<>();

    public static long TOTALTIME  = 0;
    public static long START;
    public static long tmp;

    DecodeFile(String path) throws IOException{

        START = System.currentTimeMillis();

        String OutPutFileName = path.substring(0,path.length()-7);
        FileInputStream FIS = new FileInputStream(new File(path));
        FileOutputStream FOS = new FileOutputStream(OutPutFileName+"2.txt");

        List<String> dataArr = new ArrayList<>();
        String newLine;
        String str;
        String input = "";
        int reading =0;

        while(true){
            String line="";
            while((reading = FIS.read()) != -1){
                input += (char)reading;
                if((char)reading == '\n'){
                    line.trim();
                    break;
                }
                line += (char)reading;
            }
            if(line.contains("*****")){
                break;
            }

            if(line.contains("space")&&line.contains(" ")){
                str = line.replaceAll(" ", "|");
                newLine = str.replaceAll("space", " ");
                dataArr.add(newLine);
            }else if(line.contains("newline")&&line.contains(" ")){
                str = line.replaceAll(" ", "|");
                newLine = str.replaceAll("newline", "\n");
                dataArr.add(newLine);
            }else if(line.contains("return")&&line.contains(" ")){
                str = line.replaceAll(" ", "|");
                newLine = str.replaceAll("return", "\r");
                dataArr.add(newLine);
            }else if(line.contains("tab")&&line.contains(" ")){
                str = line.replaceAll(" ", "|");
                newLine = str.replaceAll("tab", "\t");
                dataArr.add(newLine);
            }else{
                str = line.replaceAll(" ", "|");
                dataArr.add(str);
            }
        }
        int num = dataArr.size();
        for(int i=0; i<num; i++){
            String[] codeSet = dataArr.get(i).split("\\|");
            String value = codeSet[0];
            char key = codeSet[1].charAt(0);
            prefixCodeTale.put(key, value);
        }

        int n;
        String weightLine = "";
        String bitWeight = "";
        while((n=FIS.read()) !=-1){
            bitWeight += (char)n;
            if((char) n== '\n'){
                weightLine.trim();
                break;
            }
            weightLine += (char)n;
        }


        int bitWight = Integer.valueOf(weightLine);
        String buffer="";
        byte[] data = FIS.readAllBytes();
        for(var ele:data){
            String bin = Integer.toBinaryString(Byte.toUnsignedInt(ele));
            int len = bin.length();
            for(int i=len; i<8;i++){
                bin ="0"+bin;
            }
            buffer += bin;
        }

//        buffer = buffer.substring(0, bitWight);

//        int numBytes = bitWight / 8;
//        String buffer = "";
//        for(int i=0; i<numBytes+2; i++){
//            int b= FIS.read();
//            String bin = Integer.toBinaryString(b);
//            int numZeros = 8-bin.length();
//            String padding = "0".repeat(numZeros);
//            bin = padding + bin;
//            buffer += bin;
//        }
        String decodeSentences = decoding(buffer);
        FOS.write(decodeSentences.getBytes());
        FOS.close();

        tmp = System.currentTimeMillis() - START;
        TOTALTIME  += tmp;

        System.out.println("*** UNZIP301 - OUR TEAM IMPLEMENTATION ***");
        System.out.println("Total Time :" +TOTALTIME);
        System.out.println("Output File Name: "+OutPutFileName.substring(7, OutPutFileName.length())+"2.txt");
        System.out.println("*** All processes are DONE ***");

    }

    public static String decoding(String data){

        START = System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();
        String buffer = "";
        for(char c : data.toCharArray()){
            buffer += c;

            if(prefixCodeTale.containsValue(buffer)){
                Stream<Character> keyStream = getKeysByValue(prefixCodeTale, buffer);
                char key = keyStream.findFirst().get();
                sb.append(key);
                buffer="";
            }
        }

        tmp = System.currentTimeMillis() - START;
        TOTALTIME  += tmp;

        return sb.toString();
    }

    public static <K, V> Stream<K> getKeysByValue(Map<K, V> map, V value) {
        return map.
                entrySet().
                stream().
                filter(entry -> value.equals(entry.getValue())).
                map(Map.Entry::getKey);
    }

    public static void main(String[] argv) throws IOException {
        String path = argv[0];
        new DecodeFile("sample/"+path);
    }
}
