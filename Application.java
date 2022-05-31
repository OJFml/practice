import java.util.Arrays;
import java.util.LinkedList;

// Proyecto 3
// Juarez Cruz Oscar Daniel
// Sistemas Distribuidos - 4CM11
public class Application{
    public static void main(String[] args) {
        String [] ips = new String[]{args[0], args[1], args[2]};
        String[] words = Arrays.copyOfRange(args, 3, args.length );
        LinkedList<String> listWords = new LinkedList(Arrays.asList(words) );
        Client client1 = new Client(ips[0], listWords);
        Client client2 = new Client(ips[1], listWords);
        Client client3 = new Client(ips[2], listWords);

        client1.start();
        client2.start();
        client3.start();
    }
}