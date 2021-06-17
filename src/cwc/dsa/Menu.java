package cwc.dsa;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.PrintWriter;
/**
 * This class is used to read files and write to them
 * Other features such as the sort() method
 * @author Stefan Babukov
 */
class ReadWriteFile {
    File file;
    LinkListQueue<String[]> queue;
    
    /**
     * @param path path of the file we are reading from
     * @return populated LinkListQueue with the messages
     */
    public LinkListQueue<String[]> populateQueue(String path){
        queue = new LinkListQueue<String[]>();
        try{
        file = new File(path);
        Scanner reader = new Scanner(file);
        while(reader.hasNextLine()){
            String[] data = reader.nextLine().split(",");
            queue.enqueue(data);
        }
        reader.close();
        } catch(FileNotFoundException e){
            System.out.println("File not found!");
        }
        return queue;
    }
    /**
     * 
     * @param list 
     * @param index 
     * @return returns the packet id of the selected line in a list
     */
    public int getPacketId(ArrayList<String[]> list, int index){
        String [] line = list.get(index);
        return Integer.parseInt(line[0]);
    }
    
    /**
     * This method performs the insertion sort on the passed array list 
     * @param list the list to be sorted
     * @return ArrayList the sorted list
     */
    public ArrayList<String[]> sort (ArrayList<String[]> list){

        for(int i = 0; i<list.size(); i++){
            int smallestIndex = i;
            for(int j = i+1; j<list.size(); j++){
                
                if(getPacketId(list, j) < getPacketId(list, smallestIndex)){
                    smallestIndex = j;
                }
            }
            String[] temp = list.get(i);
            list.set(i, list.get(smallestIndex));
            list.set(smallestIndex, temp);
        }
        
        return list;
    }
    /**
     * This method creates a .csv file and writes data into it
     * @param list the list to write line-by-line to the file
     * @param path to the new file
     */
    public void writeToCSV(ArrayList<String[]> list, String path){
        //creating the writer
        FileWriter writer = null;
        File csvFile = new File(path);
        try{
           writer = new FileWriter(csvFile);
     
            for(int i=0; i<list.size(); i++){
                String line = list.get(i)[0] + " " + list.get(i)[1];
                writer.write(line);
                //adding a new line to the file
                writer.write(System.getProperty("line.separator"));
            }
            writer.close();
            System.out.println("Success!");
        }catch(IOException e){
            System.out.println("Failed to write to file!");
            e.printStackTrace();
        }
    }
}

/**
 * This class presents the user with a basic console menu
 * Also triggers the operations themselves and informs the user of success/failure
 * @author Stefan Babukov
 */
public class Menu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int a = 0;
        LinkListQueue<String[]> queue = null;
        System.out.println("Message encryption / decryption. Choose operation");
        System.out.println("1. Decrypt incoming messages from .txt");
        System.out.println("2. Encrypt messages for sending from .txt");
        System.out.println("--------------------------------------------------");
        Scanner input = new Scanner(System.in);
        int choice = 0;
        while(choice != 1 && choice != 2){
            System.out.println("Choose between 1 and 2:");
            choice = input.nextInt();
        }
        ReadWriteFile file = new ReadWriteFile();
        
        switch(choice){
            case 1:
                queue = file.populateQueue("src/static/ciphertext.txt");
                break;
                
            case 2:
                queue = file.populateQueue("src/static/plaintext.txt");
                break;
        }
        
        Node<String[]> current = queue.front;
        ArrayList<String[]> messages = new ArrayList<>();
        String filename ="";
        
        while(current!=null){
            String[] data = current.data;
            Message message = new Message(Integer.parseInt(data[0]), data[1], data[2]);
            if(choice == 1){
                filename = "src/static/decryptedSorted.csv";
                message.decrypt();
            } else {
                filename = "src/static/encrypted.csv";
                message.encrypt();
            }
            String [] line = {Integer.toString(message.packetID), message.text};
            messages.add(line);
            current=current.next;
        }
        
        if(choice==1){
            messages = file.sort(messages);
        }
        file.writeToCSV(messages, filename);
        
    }
    
}
