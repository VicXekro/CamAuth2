package com.example.camauth2;
import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;

public class Crypto {
    public static KeyPair generateKeyPair(Context context, KeyStore keyStore){
        try {
            if(!isKeyPairCreated(keyStore)) {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context).
                        setAlias(String.valueOf(R.string.app_name))
                        .setSubject(new X500Principal("CN=Sample Name, O=VeriCam"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                //init keysize
                keyPairGenerator.initialize(spec);

                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                Toast.makeText(context,"Keys generation success", Toast.LENGTH_SHORT).show();
                return keyPair;
            }
            Toast.makeText(context,"Key Already Generated", Toast.LENGTH_SHORT).show();
            return null;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | KeyStoreException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isKeyPairCreated(KeyStore keyStore) throws KeyStoreException {
        return keyStore.containsAlias(String.valueOf(R.string.app_name));
    }
}
