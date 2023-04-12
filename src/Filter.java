import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Filter {
    public static Element[] generateRandomElements(int numberOfElements) {
        Element[] elementArray = new Element[numberOfElements];
        Random rand = new Random();
        Set<Integer> distinct = new HashSet<>();

        for (int i = 0; i < numberOfElements;) {
            int distinctElementID = rand.nextInt();
            if (!distinct.contains(distinctElementID)){
                distinct.add(distinctElementID);
                elementArray[i] = new Element(distinctElementID);
                i++;
            }
        }

        return elementArray;
    }
    public static void randomizeSeedArray(int[] s){
        Random rand = new Random();
        for (int i = 0; i < s.length; i++)
            s[i] = rand.nextInt();
    }
    public static int hashFunction(int f, int s) throws NoSuchAlgorithmException {
        int hashInput =  f ^ s;
        byte[] bytearray= ByteBuffer.allocate(4).putInt(hashInput).array();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(bytearray);
        return  ByteBuffer.wrap(messageDigest).getInt();
//        return (f ^ s)%10000;
    }

}
