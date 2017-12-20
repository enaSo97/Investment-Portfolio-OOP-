package Investment;

public class Main {

    private static String fileName = "";

    public static String getFileName(){
        return fileName;
    }//end if
    /**
     *
     * Method that calls the portfolio to run the program
     * @param args Argument
     */
    public static void main(String[] args) {
        fileName = args[0];
        Portfolio portfolio = new Portfolio();
        //portfolio.fileLoad(args[0]);
        if (args.length != 0) {
            portfolio.fileLoad(args[0]);
            portfolio.run();
            portfolio.fileSave(args[0]);
        }else{
            System.out.println("Command line argument is empty, quiting the program");
        }
    }
}
