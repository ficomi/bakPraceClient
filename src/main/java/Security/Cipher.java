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
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *  Třída která se stará o šifrování/dešifrování komunikace a AES klíče.
 * @author pix
 */
public class Cipher {

    private static String ASYM_ALGORITHM;
    private static String SYM_ALGORITHM;
    private static PrivateKey privateKey;
    private static PublicKey publicKey;
    private static SecretKey secKey;
    private static Network network;
    public static boolean isEncrypted = false;
    private final static Logger logger = LoggerFactory.getLogger(Cipher.class);


    /**
     *  Konstruktor kde se nastavuje druh použitých šifrovacích algoritmů a velikosti jejich klíčů.
     * @param asymAlgorithm
     * @param symAlgorithm
     * @param asymKeySize
     * @param network
     * @throws NoSuchAlgorithmException
     */

    public static void createKeys(String asymAlgorithm, String symAlgorithm, int asymKeySize,Network network) throws NoSuchAlgorithmException {
        Cipher.network=network;
        Cipher.ASYM_ALGORITHM = asymAlgorithm;
        Cipher.SYM_ALGORITHM = symAlgorithm;

        // Generování asym klíčů
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(asymAlgorithm);
        kpg.initialize(asymKeySize);

        KeyPair keys = kpg.genKeyPair();
        privateKey = keys.getPrivate();
        publicKey = keys.getPublic();

    }


    /**
     *  Zašifrování byte[] -> využívá stringEncrypt()
     * @param message
     * @return
     * @throws Exception
     */
    public static String encrypt(String message) throws Exception {

        javax.crypto.Cipher cipherSym = javax.crypto.Cipher.getInstance(SYM_ALGORITHM +"/ECB/PKCS5PADDING");
        cipherSym.init(javax.crypto.Cipher.ENCRYPT_MODE,secKey);
        byte[] encryptedMessage = cipherSym.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return  Base64.getEncoder().encodeToString(encryptedMessage);

    }


    /**
     *  Dešifrovává byte[] -> používá stringDecrypt()
     * @param message
     * @return
     * @throws Exception
     */
    public static String decrypt(String message) throws Exception {

        javax.crypto.Cipher cipherSym = javax.crypto.Cipher.getInstance(SYM_ALGORITHM +"/ECB/PKCS5PADDING");
        cipherSym.init(javax.crypto.Cipher.DECRYPT_MODE,secKey);
        byte[] decryptedMessage = cipherSym.doFinal(Base64.getDecoder().decode(message));

        return  new String(decryptedMessage, StandardCharsets.UTF_8);

    }





    /**
     * Dešifruje AES klíč pomocí vlastního primarního klíče -> AES klíč je použit na šifrování/dešifrování komunikace.
     * @param encrypted
     * @throws Exception
     */
    public static synchronized void decryptAsymKey(byte[] encrypted) throws Exception{
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ASYM_ALGORITHM);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE,privateKey);
        byte[] byteSecKey = cipher.doFinal(encrypted);

        Cipher.secKey=new SecretKeySpec(byteSecKey, 0, byteSecKey.length, SYM_ALGORITHM);
        if (network!=null) {
            CommandMapSend.setRequiredCommand(StringCommandsSend.STARTENCRYPT);
            network.getNetworkThread(NetworkThreadName.SENDER).interrupt();
        }

        logger.debug("Decryptovaný AES klíč: "+Base64.getEncoder().encodeToString(byteSecKey));
        Arrays.fill(byteSecKey,Byte.MIN_VALUE);
    }


    public static PublicKey getPublicKey() {
        return publicKey;
    }

    public static String getAsymAlgorithm() {
        return ASYM_ALGORITHM;
    }


    public static String getSymAlgorithm(){return SYM_ALGORITHM;}








}
