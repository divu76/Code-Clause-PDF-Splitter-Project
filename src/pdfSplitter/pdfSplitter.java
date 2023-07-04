package pdfSplitter;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class pdfSplitter
{

    public static void main(String[] args)
    {
        String inputFilePath = "input.pdf";
        String outputFolderPath = "output";

        try 
        {
            // Create output folder if it doesn't exist
            File outputFolder = new File(outputFolderPath);
            if (!outputFolder.exists())
            {
                outputFolder.mkdir();
            }

            // Read the input PDF file as bytes
            byte[] pdfBytes = readBytesFromFile(inputFilePath);

            // Split and save each page as a separate PDF file
            String[] pages = splitPDFPages(pdfBytes);
            for (int i = 0; i < pages.length; i++) 
            {
                String outputFilePath = outputFolderPath + "/page_" + (i + 1) + ".pdf";
                writeBytesToFile(pages[i].getBytes(), outputFilePath);
                System.out.println("Page " + (i + 1) + " saved: " + outputFilePath);
            }

            System.out.println("PDF splitting completed.");
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    private static byte[] readBytesFromFile(String filePath) throws IOException 
    {
        File file = new File(filePath);
        byte[] bytes = new byte[(int) file.length()];
        java.io.FileInputStream fis = new java.io.FileInputStream(file);
        fis.read(bytes);
        fis.close();
        return bytes;
    }

    private static String[] splitPDFPages(byte[] pdfBytes)
    {
        String pdfContent = new String(pdfBytes);
        String[] pages = pdfContent.split("\\s*%%Page\\s+");
        for (int i = 1; i < pages.length; i++) 
        {
            pages[i] = "%%Page " + pages[i];
        }
        return pages;
    }

    private static void writeBytesToFile(byte[] bytes, String filePath) throws IOException 
    {
        try (FileOutputStream fos = new FileOutputStream(filePath)) 
        {
            fos.write(bytes);
        }
    }
}
