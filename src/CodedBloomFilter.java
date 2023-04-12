
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CodedBloomFilter extends Filter{
    static int numberOfSets;
    static int numberOfElements;
    static int numberOfFilters;
    static int numberOfBits;
    static int numberOfHashes;
    static int[][] codedBloomFilters;
    static int[] hashKeys;
    static Random rand;

    public static void stdInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of Sets:");
        numberOfSets = sc.nextInt();
        System.out.println("Enter the number of Elements in each Set:");
        numberOfElements = sc.nextInt();
        System.out.println("Enter the number of Filters:");
        numberOfFilters = sc.nextInt();
        System.out.println("Enter the number of bits in each filter:");
        numberOfBits = sc.nextInt();
        System.out.println("Enter the numbers of Hashes:");
        numberOfHashes = sc.nextInt();
    }

    public static void codedBloomFunction() throws NoSuchAlgorithmException {
        hashKeys = new int[numberOfHashes];
        randomizeSeedArray(hashKeys);

        codedBloomFilters = new int[numberOfFilters][numberOfBits];
        HashMap<String, Set<Integer>> visitedMap = new HashMap<>();
        HashSet<Integer> visited = new HashSet<>();

        for(int i=0; i< numberOfSets; i++){
            String bitStr = Integer.toBinaryString(i+1);
            StringBuilder newCode = new StringBuilder(bitStr);

            if(bitStr.length() < 3){
                int len = bitStr.length();
                while(len++ < 3) newCode.insert(0,"0");
            }

            bitStr = new String(newCode);
            int elementCount = 0;
            Set<Integer> currSet = new HashSet<>();

            while(elementCount < numberOfElements){
                rand = new Random();
                int id = rand.nextInt();
                if(visited.add(id)) {
                    encodeSet(id, bitStr);
                    currSet.add(id);
                    elementCount++;
                }
            }
            visitedMap.put(bitStr, currSet);
        }

        int validHash = 0;
        for(Map.Entry<String, Set<Integer>> entry: visitedMap.entrySet()) {
            for(Integer id: entry.getValue())
                if(entry.getKey().equals(lookupSet(id)))
                    validHash++;
        }

        System.out.println(validHash);
    }


    public static void encodeSet(int flowId, String code) throws NoSuchAlgorithmException {
        int[] hashes = new int[numberOfHashes];
        for (int i = 0; i < numberOfHashes; ++i) {
            int hashCode = hashFunction(flowId, hashKeys[i]);
            hashes[i] = Math.floorMod(hashCode, numberOfBits);
        }
        for(int i=0;i<numberOfFilters; i++){
            if(code.charAt(i)!='0')
                for(int j=0;j<numberOfHashes;j++)
                    codedBloomFilters[i][hashes[j]]=1;
        }
    }

    public static String lookupSet(int flowId) throws NoSuchAlgorithmException {
        StringBuilder bitSb = new StringBuilder();
        int[] hashes = new int[numberOfHashes];
        for (int i = 0; i < numberOfHashes; ++i) {
            int hashCode = hashFunction(flowId, hashKeys[i]);
            hashes[i] = Math.floorMod(hashCode, numberOfBits);
        }
        for(int i=0;i<numberOfFilters; i++){
            int j=0;
            while(j<numberOfHashes && codedBloomFilters[i][hashes[j]]!=0) j++;
            bitSb.append(j < numberOfHashes ? "0" : "1");
        }
        return bitSb.toString();
    }
}
