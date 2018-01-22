package by.troyan.web.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5Converter class. Help to set passHash for Users.
 */

public class MD5Converter {
    private final static Logger LOG = LogManager.getLogger(MD5Converter.class);

    /**
     * Using special algorithm calculate special code
     *
     * @param  string
     *        all strings, usually used for passwords
     * @return string of code
     */
    public static String getHash(String string){
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(string.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException exc) {
            LOG.error(exc);
        }
        BigInteger bigInt = new BigInteger(1, digest);
        String result = bigInt.toString(16);
        while( result.length() < 32 ){
            result = "0" + result;
        }
        return result;
    }
}
