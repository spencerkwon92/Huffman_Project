import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
/**
 * MESSAGE:
 * 성준아! 이건 내가 임의로 공부하려고 배껴온거야 ㅋㅋ 그래서 아직 적용인 안된게 많다ㅜㅜ
 * 일단 더 공부해보고 이번주까지 호프만 트리완성할게!
 *
 */

/**
 * Final Project
 * By.Sungjin Kwon, Sungjoon Ha
 *
 */
// 호프만을 구현하기 위한 노드
class Node implements Comparable<Node>{
    char data;
    int freq;
    Node left, right;

    Node(){}
    Node(char data, int frequency){
        this.data = data;
        this.freq = frequency;
    }

    @Override
    public int compareTo(Node node) {
        return freq - node.freq;
    }
}


public class EncodeFile {

    private static Scanner scan = new Scanner(System.in);
    private static Map<Character, String> prefixCodeTable = new HashMap<>();
    public static int bitwight = 0;
    public static FileOutputStream fos;
    public static String outPutFileName;

    EncodeFile(String filename) throws IOException {

        try{

            Path path = Paths.get(filename);
            outPutFileName = filename.substring(0,filename.length()-4);

            fos = new FileOutputStream(outPutFileName+".zip301");

            String data = Files.readString(path, StandardCharsets.ISO_8859_1);
            String code = encoding(data);
            String division = "****"+"\n";
            String bits = String.valueOf(bitwight) +"\n";
            fos.write(division.getBytes());
            fos.write(bits.getBytes());
            for(int i=0; i<code.length(); i+=8){
                String singleByte = code.substring(i,i+8);
                int value = Integer.parseInt(singleByte,2);
                fos.write(value);
            }

            fos.close();



        }catch (IOException e){

            }catch (NumberFormatException ef){

        }

        System.out.println("Huffman Zip - TEAM REFERENCE IMPLEMENTATION ***");
        System.out.println("Generating frequency data...");
        System.out.println("Building the Huffman tree..."+(buildTreeE - buildTreeS)/1000.0 + " sec");
        System.out.println("Generating the Huffman code...");
        System.out.println("Encoding the document..." + (encodingE - encodingS)/1000.0 + " sec");
        System.out.println("Writing output file..." + (prefixE - prefixS)/1000.0 + " sec");
        System.out.println("Wrote output to: " + outPutFileName+".zip301");
        System.out.println("TOTAL TIME: ");
    }

    static long buildTreeS = System.currentTimeMillis();
    public static Node buildTree(PriorityQueue<Node> priQue) {
        if(priQue.size() == 1) {
            return priQue.poll();
        }else {
            Node leftNode = priQue.poll();
            Node rightNode = priQue.poll();
            Node sumNode = new Node();

            if(leftNode !=null && rightNode!=null){
                sumNode.data = '-';
                sumNode.freq = leftNode.freq + rightNode.freq;
                sumNode.left = leftNode;
                sumNode.right = rightNode;
            }
            priQue.offer(sumNode);
            return buildTree(priQue);

        }
    }
    static long buildTreeE = System.currentTimeMillis();

    static long encodingS = System.currentTimeMillis();

    public static String encoding(String data) throws IOException {
        Map<Character, Integer> charFreq = new HashMap<>();
        for(char c:data.toCharArray()) {
            if(!charFreq.containsKey(c)) {
                charFreq.put(c,1);
            }else {
                int no = charFreq.get(c);
                charFreq.put(c, ++no);
            }
        }

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        Set<Character> ketSet = charFreq.keySet();
        for(char c:ketSet) {
            Node node = new Node(c, charFreq.get(c));
            priorityQueue.offer(node);
        }
        Node rootNode = buildTree(priorityQueue);
        setPrefixCode(rootNode, "");

        StringBuilder sb = new StringBuilder();
        for(char c:data.toCharArray()) {
            sb.append(prefixCodeTable.get(c));
        }
        return sb.toString();
    }

    static long encodingE = System.currentTimeMillis();

    static long prefixS = System.currentTimeMillis();
    public static void setPrefixCode(Node n, String code) throws IOException {
        if (n==null) return;

        if(n.data != '-' && n.left== null && n.right == null) {
            prefixCodeTable.put(n.data, code);

            if(n.data == '\n'){
                fos.write((code+" newLine"+"\n").getBytes());
            }else if(n.data == ' '){
                fos.write((code+" Space"+"\n").getBytes());
            }else if(n.data == '\r'){
                fos.write((code+" Return"+"\n").getBytes());
            }else if(n.data =='\t'){
                fos.write((code+" Tab"+"\n").getBytes());
            }else {
                fos.write((code+" "+n.data+"\n").getBytes());
            }

            int bit = code.length()*n.freq;
            bitwight = bitwight + bit;

        }else {
            setPrefixCode(n.left, code+'0');
            setPrefixCode(n.right, code+'1');
        }

    }
    static long prefixE = System.currentTimeMillis();

    public static <K, V> Stream<K> getKeysByValue(Map<K, V> map, V value){
        return map.
                entrySet().
                stream().
                filter(entry -> value.equals(entry.getValue())).
                map(Map.Entry::getKey);
    }


    public static void main(String[] args) throws Exception{
        String filename = args[0];// input 파일 들고 오기 위핸서 넣은거야!!

        new EncodeFile(filename);

//        outPutFileName = filename.substring(0,filename.length()-4);

    }

}
