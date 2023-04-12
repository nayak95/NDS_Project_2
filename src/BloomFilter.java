import java.security.NoSuchAlgorithmException;
import java.util.*;

public class BloomFilter extends Filter{
    static int numberOfElements;
    static int numberOfBits;
    static int numberOfHashes;

    public static void stdInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of Elements:");
        numberOfElements = sc.nextInt();
        System.out.println("Enter the number of Bits in the Filter:");
        numberOfBits = sc.nextInt();
        System.out.println("Enter the number of Hashes:");
        numberOfHashes = sc.nextInt();
    }
    public static void bloomFunction () throws NoSuchAlgorithmException {
        Element[] elementSetA = generateRandomElements(numberOfElements);
        Element[] elementSetB = generateRandomElements(numberOfElements);
        int[] bitArray = new int[numberOfBits];
        int[] seedArray = new int[numberOfHashes];
        randomizeSeedArray(seedArray);
        encodeSet(elementSetA, bitArray, seedArray);
        System.out.println(lookupSet(elementSetA, bitArray, seedArray));
//        encodeSet(elementSetB, bitArray, seedArray);
        System.out.println(lookupSet(elementSetB, bitArray, seedArray));
    }

    private static void encodeSet(Element[] elementSet, int[] bitArray, int[] seedArray) throws NoSuchAlgorithmException {
        for (Element element : elementSet) {
            for (int j = 0; j < numberOfHashes; j++) {
                int index = Math.floorMod(hashFunction(element.getElementID(), seedArray[j]), bitArray.length);
                bitArray[index] = 1;
            }
        }
    }

    private static int lookupSet(Element[] elementSet, int[] bitArray, int[] seedArray) throws NoSuchAlgorithmException {
        int count = 0;
        for (Element element : elementSet) {
            boolean flag = true;
            for (int j = 0; j < numberOfHashes; j++) {
                int index = Math.floorMod(hashFunction(element.getElementID(), seedArray[j]), bitArray.length);
                if (bitArray[index] == 0)
                    flag = false;
            }
            if (flag)
                count++;
        }
        return count;
    }

}
