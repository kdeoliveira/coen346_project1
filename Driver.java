import java.io.IOException;


public class Driver {
    public static void main(String[] args) throws IOException {


        try{
            ReadFile rd = new ReadFile(args);
            Bulb b = new Bulb(rd.getList());

            
            b.findDefective();

            b.printDefective();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        

    }
}
