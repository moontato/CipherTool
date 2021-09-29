import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.KeyGenerator;
 
public class CipherTool{

    private String key;

    public CipherTool(){

    }    
 
    // Encryption
    public String encrypt(String strToEncrypt, String stringKeyInput){
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
    
        
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            if(stringKeyInput.equals("")){
                SecretKey secretKey = secretKeyGenerator();
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);


                // OUTPUT THE KEY WHEN DONE SO THAT IT CAN BE DECRYPTED!!
                // System.out.println("The key is: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
                key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            }
            else{
                // Decode key from string to bytes
                byte[] decodedKey = Base64.getDecoder().decode(stringKeyInput);
                // rebuild key using SecretKeySpec
                SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            }
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    // Decryption
    public static String decrypt(String strToDecrypt, String stringKeyInput) {
        // Decode key from string to bytes
        byte[] decodedKey = Base64.getDecoder().decode(stringKeyInput);
        // rebuild key using SecretKeySpec
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
        
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    // Generating Secret Key
    public SecretKey secretKeyGenerator(){
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // for example
            SecretKey secretKey = keyGen.generateKey();
            return secretKey;
        }
        catch(Exception e){
            System.out.println("Error while generating key: " + e.toString());
        }
        return null;
    }

    public String getKey(){
        return key;
    }
}