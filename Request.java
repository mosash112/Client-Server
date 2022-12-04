import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

public class Request implements Task<int[]>, Serializable {

    private static final long serialVersionUID = 227L;
    private int[] out;

    public Request(String f) {
        batch(f);
    }

    public HashMap<String, int[]> batch(String f){
        File file = new File(f);
        Scanner bat = null;
        int ns[] = new int[2];
        String r;
        HashMap<String, int[]> ops = new HashMap<>();

        try {
            bat = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (bat.hasNextLine()) {
            String data = bat.nextLine();
            if (data.equals("F"))
                break;
            r = data.split(" ")[0];
            ns[0] = Integer.parseInt(data.split(" ")[1]);
            ns[1] = Integer.parseInt(data.split(" ")[2]);
            ops.put(r, ns);
        }
//        System.out.println(ops);
        return ops;
    }

    public int[] execute() {
//        return batch(bat);
        return out;
    }
}