package fr.lyrania.common.protocol.encryption;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;

public class AESEncryption implements ProtocolEncryption {

    private final Cipher inCipher;
    private final Cipher outCipher;

    public AESEncryption(Key key) throws GeneralSecurityException {
        this.inCipher = Cipher.getInstance("AES/CFB8/NoPadding");
        this.inCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(key.getEncoded()));

        this.outCipher = Cipher.getInstance("AES/CFB8/NoPadding");
        this.outCipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(key.getEncoded()));
    }

    @Override
    public int getDecryptOutputSize(int length) {
        return this.inCipher.getOutputSize(length);
    }

    @Override
    public int getEncryptOutputSize(int length) {
        return this.outCipher.getOutputSize(length);
    }

    @Override
    public int decrypt(byte[] input, int inputOffset, int inputLength, byte[] output, int outputOffset) throws ShortBufferException {
        return this.inCipher.update(input, inputOffset, inputLength, output, outputOffset);
    }

    @Override
    public int encrypt(byte[] input, int inputOffset, int inputLength, byte[] output, int outputOffset) throws ShortBufferException {
        return this.outCipher.update(input, inputOffset, inputLength, output, outputOffset);
    }
}
