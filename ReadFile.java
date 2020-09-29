import java.io.*;
/**
* <h1>Text file reader</h1>

The class {@code ReadFile()} is responsible to generate a boolean array from an input text file which format respects the the following conditions:
<ul>
~/$ text.txt
<ol>1.  Integer specifying number of lightbulbs</ol>
<ol>2.  Bulb-1 state (0 or 1)</ol>
<ol>3.  Bulb-2 state (0 or 1)</ol>
<ol>4.  Bulb-n state (0 or 1)</ol>
</ul>
<p>
The followign methods are available to the client:
<ul>
    <li>{@code getList()}</li>
</ul>
<p>
Once file is generated, a new boolean array will be generated based on the value provided on the input file. The input file can be either specified as an argument on command-line or as a File object.
</p>

* @author  Kevin de Oliveira
* @version 1.0
* @since   2020/07/29
*/
public class ReadFile{
    private File src;
    private static final int EOF = -1;

    public ReadFile(File file){
        this.src = file;
    }
    /**
     * Creates new file object
     * @param args Array of strings
     * @throws IOException Input file invalid
     */
    public ReadFile(String[] args) throws IOException{        
        if(args.length > 1)
            throw new IOException("Invalid number of argument");
        this.src = new File(args[0]);
            if(!this.src.canRead())
                throw new IOException("Unable to read file");
        
    }
    /**
     * Generates and returns a boolean array of n size according to @param args[] arguments from command line or @param file file object .
     * @return boolean array
     * @throws IOException Input file character or line invalid
     */
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
