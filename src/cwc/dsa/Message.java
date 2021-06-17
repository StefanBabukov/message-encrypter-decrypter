
package cwc.dsa;

/**
 * Message class for storing each message
 * @author Stefan Babukov
 */
public class Message {
    int packetID;
    String password = null;
    String text = null;
    /**
     * 
     * @param packetID the packet id  
     * @param password message password
     * @param text message data, either encrypted or in plaintext
     */
    Message(int packetID, String password, String text){
        this.packetID = packetID;
        this.password = password;
        this.text = text;
    }
    /**
     * performs a basic encryption by shifting letters 
     * text field of the message class is changed with the newly encrypted value
     * uses the affine cipher with a key
     */
    public void encrypt(){
        int passwordIndex = 0;
        String cipher ="";
        for(int i = 0; i<text.length(); i++){
            if(passwordIndex == password.length()){
                passwordIndex = 0;
            }
            int cipherNumber = ((int)text.charAt(i) - (int)'A') + (password.charAt(passwordIndex) - (int)'A');
            if(cipherNumber>=26){
                cipherNumber -= 26;
            }
            cipherNumber += (int)'A';
            cipher += (char)cipherNumber;
            passwordIndex++;
        }
        System.out.println("ENCODED " + text + " -> "+cipher);
        text = cipher;
    }
    /**
     * decrypts the message using the message key
     * the text field of the Message class is changed
     * with the newly decrypted value
     */
    public void decrypt(){
        int passwordIndex = 0;
        String plaintext = "";
        for(int i = 0; i<text.length();i++){
            char plainLetter;
            if(passwordIndex == password.length()){
                passwordIndex = 0;
            }
            
            int plainNumber = (int)text.charAt(i) - (int)password.charAt(passwordIndex);
            if(plainNumber<0){
                plainNumber = 26 + plainNumber;
            }
            //adding 'A' so it corresponds to ascii table letter
            plainNumber += (int)'A';
            plaintext = plaintext + (char)plainNumber;
            passwordIndex++;
        }
        System.out.println("DECIPHERED " + text + " -> "+plaintext);
        text = plaintext;
    }
}
