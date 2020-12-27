package com.example.camauth2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;

public class SetupActivity extends AppCompatActivity {
    private Button lauchCameraButton;
    private Button generateKeysButton;
    private Button printPubKeyButton;
    private TextView pubKeyTextView;
    private KeyStore keyStore;
    private Button clearKeyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initUIElements();
        lauchCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        generateKeysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyPair keyPair = Crypto.generateKeyPair(getApplicationContext(),keyStore);
            }
        });

        printPubKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(Crypto.isKeyPairCreated(keyStore)) {
                        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.
                                getEntry(String.valueOf(R.string.app_name), null);
                        RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();
                        byte[] publicKeyEncoded = publicKey.getEncoded();

                        // Convert Byte Array to hex
                        StringBuilder strBuilder = new StringBuilder();
                        strBuilder.append("RSA Public Key: ");
                        for (byte val : publicKeyEncoded) {
                            strBuilder.append(String.format("%02x", val & 0xff));
                        }
                        pubKeyTextView.setText(strBuilder.toString());
                    }else{
                        Toast.makeText(getApplicationContext(),"No Key found",Toast.LENGTH_SHORT ).show();
                    }
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnrecoverableEntryException e) {
                    e.printStackTrace();
                }
            }
        });

        clearKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    keyStore.deleteEntry(String.valueOf(R.string.app_name));
                    Toast.makeText(getApplicationContext(), "Keys Successfully cleared", Toast.LENGTH_SHORT)
                            .show();
                } catch (KeyStoreException e) {
                    Toast.makeText(getApplicationContext(), "Keys not cleared", Toast.LENGTH_SHORT)
                            .show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void initUIElements(){
        lauchCameraButton = (Button) findViewById(R.id.launchCamButton);
        generateKeysButton = (Button) findViewById(R.id.generateKeyButton);
        printPubKeyButton = (Button) findViewById(R.id.printPublicKeyButton);
        pubKeyTextView = (TextView) findViewById(R.id.pubKeyTextView);
        clearKeyButton = (Button) findViewById(R.id.clearKeyButton);
    }
}