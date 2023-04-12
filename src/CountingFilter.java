import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class CountingFilter extends Filter{
    static int initialElements;
    static int removalElements;
    static int addedElements;
    static int numberOfCounters;
    static int numberOfHashes;

    public static void stdInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of elements to be encoded initially:");
        initialElements = sc.nextInt();
        System.out.println("Enter the number of elements to be removed:");
        removalElements = sc.nextInt();
        System.out.println("Enter the number of elements to be added:");
        addedElements = sc.nextInt();
        System.out.println("Enter the number of counters in the filter:");
        numberOfCounters = sc.nextInt();
        System.out.println("Enter the number of Hashes:");
        numberOfHashes = sc.nextInt();
    }

    public static void countingBloomFunction() throws NoSuchAlgorithmException {

        int[] counterArray = new int[numberOfCounters];
        int[] seedArray = new int[numberOfHashes];
        randomizeSeedArray(seedArray);
        Element[] elementSetA = generateRandomElements(initialElements);
        encodeSet(elementSetA, counterArray, seedArray);
        removeSet(elementSetA, counterArray, seedArray);

        Element[] elementSetB = generateRandomElements(addedElements);
        encodeSet(elementSetB, counterArray, seedArray);
        System.out.println(lookupSet(elementSetA, counterArray, seedArray));
    }

    private static void encodeSet(Element[] elementSet, int[] counterArray, int[] seedArray) throws NoSuchAlgorithmException {
        for (Element element : elementSet) {
            for (int j = 0; j < numberOfHashes; j++) {
                int index = Math.floorMod(hashFunction(element.getElementID(), seedArray[j]), counterArray.length);
                counterArray[index]++;
            }
        }
    }
    private static void removeSet(Element[] elementSet, int[] counterArray, int[] seedArray) throws NoSuchAlgorithmException {
        for (int i = 0; i < removalElements; i++) {
            for (int j = 0; j < numberOfHashes; j++) {
                int index = Math.floorMod(hashFunction(elementSet[i].getElementID(), seedArray[j]), counterArray.length);

                if (counterArray[index] > 0)
                    counterArray[index]--;
            }
        }
    }

    private static int lookupSet(Element[] elementSet, int[] counterArray, int[] seedArray) throws NoSuchAlgorithmException {
        int count = 0;
        for (Element element : elementSet) {
            boolean flag = true;
            for (int j = 0; j < numberOfHashes; j++) {
                int index = Math.floorMod(hashFunction(element.getElementID(), seedArray[j]), counterArray.length);

                if (counterArray[index] <= 0)
                    flag = false;
            }
            if (flag)
                count++;
        }
        return count;
    }
}
