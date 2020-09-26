import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.*;


public class Driver {
    public static void main(String[] args){

        if(args.length == 1){
            try{
                File path = new File(args[0]);
                if(!path.canRead())
                    return;

                List<Integer> temp = new ArrayList<>();

                FileInputStream fileinput = new FileInputStream(path);

                int c;
                while( (c = fileinput.read()) != -1){
                    if((char) c != '\n'){
                        temp.add(Character.getNumericValue(c));
                    }
                }

                Bulb bulb = new Bulb(temp);

                bulb.findDefective();

                bulb.printDefective();

                fileinput.close();
            }
            catch(IOException e){
                //Pass
            }

        }
    }
}
