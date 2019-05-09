import modelo.PrinterService;

public class Main3 {

    public static void main(String[] args) throws Exception {

        PrinterService printerService = new PrinterService();

        System.out.println(printerService.getPrinters());

        //print some stuff
        printerService.printString("EC-PM-80360", "\n\n testing testing 1 2 3eeeee \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        //cut that paper!
        byte[] cutP = new byte[] { 0x1d, 'V', 1 };

        printerService.printBytes("EC-PM-80360", cutP);

        //cut that paper!
        byte[] cut = new byte[] {27, 112,48,55,121};

        printerService.printBytes("EC-PM-80360", cut);
    }

}
