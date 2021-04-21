package Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

import static org.junit.Assert.*;

/**
 * Testy vztahující se na třídu Communication
 */
public class CommunicationTest {

    /**
     *  Test na vytvoření klíčů a jejich vlastností
     */
    @org.junit.Test
    public void createKeys() {
        try {
            Communication.createKeys("RSA","AES", 2048,128,null);
            assertEquals(2048,Communication.getAsymKeySize());
            assertEquals("RSA",Communication.getAsymAlgorithm());
            assertEquals("AES", Communication.getSymAlgorithm());
            assertEquals(128,Communication.getSymKeySize());


        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    /**
     * Test na zašifrování a dešifrování textu
     */
    @org.junit.Test
    public void stringEncryptDecrypth() {
        try {

            Communication.createKeys("RSA","AES", 2048,128,null);
            String text = "Tomáš má malé péro a všichni to vědí a máma se za něj stydí. Xd /// - xd $ß&@{@[Đ[[Đ€đđ€|€|";

            KeyGenerator kg = KeyGenerator.getInstance(Communication.getSymAlgorithm());
            SecretKey secKey = kg.generateKey();
            Communication.setSecretKey(secKey.getEncoded());


            String encryptetText = Communication.stringEncrypt(text);
            String decryptedMessage = Communication.stringDecrypt(encryptetText);
            assertEquals(text, decryptedMessage);

        }catch (Exception e){
            e.printStackTrace();
            fail("nepovedl se test");
        }
    }


    /**
     * Test na dešifrování příchozího AES klíče
     */
    @org.junit.Test
    public void symKeyDecrypth() {
        try {
            Communication.createKeys("RSA","AES", 2048,128,null);

            //Vygenerováni SecreteKey
            KeyGenerator kg = KeyGenerator.getInstance(Communication.getSymAlgorithm());
            kg.init(Communication.getSymKeySize());
            SecretKey secKey = kg.generateKey();


            //Zašifrování pomocí veřehného klíče a zpátky
            Cipher cipher = Cipher.getInstance(Communication.getAsymAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE,Communication.getPublicKey());
            String encryptedKey = Base64.getEncoder().encodeToString(cipher.doFinal(secKey.getEncoded()));
            Communication.decryptAsymKey(Base64.getDecoder().decode(encryptedKey));


            String text = "Tomáš má malé péro a všichni to vědí a máma se za něj stydí. Xd /// - xd $ß&@{@[Đ[[Đ€đđ€|€|";
            String encryptetText = Communication.stringEncrypt(text);
            String decryptedMessage = Communication.stringDecrypt(encryptetText);
            assertEquals(text, decryptedMessage);


        }catch (Exception e){
            e.printStackTrace();
            fail("Nepovedl se test šifrování sym klíče");
        }


    }
}