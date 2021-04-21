/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;


import Network.*;
import Network.Send.CommandMapSend;
import Network.Send.StringCommandsSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *  Třída která se stará o šifrování/dešifrování komunikace a AES klíče.
 * @author pix
 */
public class Communication {

    private static KeyPairGenerator kpg;
    private static KeyPair keys;
    private static int asymKeySize;
    private static int symKeySize;
    private static String asymAlgorithm;
    private static String symAlgorithm;
    private static PrivateKey privateKey;
    private static PublicKey publicKey;
    private static SecretKey secKey;
    private static Network network;
    public static boolean isEncrypted = false;
    private final static Logger logger = LoggerFactory.getLogger(Communication.class);


    /**
     *  Konstruktor kde se nastavuje druh použitých šifrovacích algoritmů a velikosti jejich klíčů.
     * @param asymAlgorithm
     * @param symAlgorithm
     * @param asymKeySize
     * @param symKeySize
     * @param network
     * @throws NoSuchAlgorithmException
     */

    public static void createKeys(String asymAlgorithm, String symAlgorithm, int asymKeySize, int symKeySize,Network network) throws NoSuchAlgorithmException {
        Communication.network=network;
        Communication.asymKeySize = asymKeySize;
        Communication.symKeySize = symKeySize;
        Communication.asymAlgorithm = asymAlgorithm;
        Communication.symAlgorithm = symAlgorithm;

        // Generování asym klíčů
        kpg = KeyPairGenerator.getInstance(asymAlgorithm);
        kpg.initialize(asymKeySize);
        keys = kpg.genKeyPair();
        privateKey = keys.getPrivate();
        publicKey = keys.getPublic();



    }


    /**
     * Zašifruje vložený string na zašifrovaný Base64 string
     * @param message
     * @return
     * @throws Exception
     */
    public static synchronized String stringEncrypt(String message) throws Exception {
        byte[] encryptMessage = byteEncrypt(message.getBytes("UTF-8"));
        return  Base64.getEncoder().encodeToString(encryptMessage);
    }

    /**
     *  Šifruje bitevé pole -> používá stringEncrypt()
     * @param message
     * @return
     * @throws Exception
     */

    public static byte[] byteEncrypt(byte[] message) throws Exception {

        Cipher cipherSym = Cipher.getInstance(symAlgorithm+"/ECB/PKCS5PADDING");
        cipherSym.init(Cipher.ENCRYPT_MODE,secKey);

        return cipherSym.doFinal(message);
    }

    /**
     * Dešifruje vložený Base64 string na string compatabilní s UTF-8
     * @param encrypted
     * @return
     * @throws Exception
     */
    public static synchronized String stringDecrypt(String encrypted) throws Exception {
        byte[] decryptedMessage = byteDecrypt(Base64.getDecoder().decode(encrypted));
        return new String (decryptedMessage,"UTF-8");
    }


    public static byte[] byteDecrypt(byte [] encrypted) throws Exception {

        if (privateKey != null){

            Cipher cipherSym = Cipher.getInstance(symAlgorithm+"/ECB/PKCS5PADDING");
            cipherSym.init(Cipher.DECRYPT_MODE,secKey);

            return cipherSym.doFinal(encrypted);
        }
        throw new Exception("Nemozno decryprovat zpravu.");

    }


    /**
     * Dešifruje AES klíč pomocí vlastního primarního klíče -> AES klíč je použit na šifrování/dešifrování komunikace.
     * @param encrypted
     * @throws Exception
     */
    public static synchronized void decryptAsymKey(byte[] encrypted) throws Exception{
        Cipher cipher = Cipher.getInstance(asymAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] byteSecKey = cipher.doFinal(encrypted);
        setSecretKey(byteSecKey);
        logger.debug("Decryptovaný AES klíč: "+Base64.getEncoder().encodeToString(byteSecKey));
        Arrays.fill(byteSecKey,Byte.MIN_VALUE);
    }

    /**
     * Nastavuje přichozí AES klíč a odešle serveru že je připraven na šifrovanou komunikaci
     * @param secKey
     */
    public static synchronized void setSecretKey(byte[] secKey){
        Communication.secKey=new SecretKeySpec(secKey, 0, secKey.length, "AES");
        if (network!=null) {
            CommandMapSend.setRequiredCommand(StringCommandsSend.STARTENCRYPT);
            network.getNetworkThread(NetworkThreadName.SENDER).interrupt();
        }
    }

    public static PublicKey getPublicKey() {
        return publicKey;
    }

    public static String getAsymAlgorithm() {
        return asymAlgorithm;
    }

    public static int getAsymKeySize() {
        return asymKeySize;
    }

    public static String getSymAlgorithm(){return symAlgorithm;}



    public static int getSymKeySize() {return symKeySize;}





}
