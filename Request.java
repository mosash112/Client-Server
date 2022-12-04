import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Request implements Task<int[]>, Serializable {

    private static final long serialVersionUID = 227L;
    private int[] out;
    private String file;

    public Request(String f) {
        this.file = f;
    }

    public ArrayList<ArrayList<String>> batch(String f){
        File file = new File(f);
        Scanner bat = null;
        int count = 0;
        ArrayList<ArrayList<String>> ops = new ArrayList<>();

        try {
            bat = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (bat.hasNextLine()) {
            String data = bat.nextLine();
            if (data.equals("F"))
                break;
            ops.add(new ArrayList<>(3));
            ops.get(count).add(data.split(" ")[0]);
            ops.get(count).add(data.split(" ")[1]);
            ops.get(count).add(data.split(" ")[2]);
            count++;
        }
        return ops;
    }

    public ArrayList<ArrayList<String>> execute() {
        return batch(file);
    }
}