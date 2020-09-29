import java.io.*;
public class ReadFile{
    private File src;
    private final static int EOF = -1;

    public ReadFile(File file){
        this.src = file;
    }

    public ReadFile(String[] args) throws IOException{        
        if(args.length > 1)
            throw new IOException("Invalid number of argument");
        this.src = new File(args[0]);
            if(!this.src.canRead())
                throw new IOException("Unable to read file");
        
    }

    public boolean[] getList() throws IOException{

        boolean[] output;
        
        try(FileInputStream input = new FileInputStream(this.src)){
            char ch = (char) input.read();

            output = new boolean[Character.getNumericValue(ch)];
            int counter = 0;

            while(( ch = (char) input.read()) != EOF && counter < output.length){
                if(ch != '\n'){
                    output[counter++] = Character.getNumericValue(ch) == 1;

                }
            }
        }

        return output;
    }

}
