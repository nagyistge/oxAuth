/*
 * oxAuth is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2014, Gluu
 */

package org.xdi.oxauth.model.crypto;

import org.xdi.oxauth.model.common.WebKeyStorage;
import org.xdi.oxauth.model.configuration.Configuration;

/**
 * @author Javier Rojas Blum
 * @version June 15, 2016
 */
public class CryptoProviderFactory {

    public static AbstractCryptoProvider getCryptoProvider(Configuration configuration) throws Exception {
        AbstractCryptoProvider cryptoProvider = null;
        WebKeyStorage webKeyStorage = configuration.getWebKeysStorage();

        switch (webKeyStorage) {
            case KEYSTORE:
                String keyStoreFile = configuration.getKeyStoreFile();
                String keyStoreSecret = configuration.getKeyStoreSecret();
                String dnName = configuration.getDnName();
                cryptoProvider = new OxAuthCryptoProvider(keyStoreFile, keyStoreSecret, dnName);
                break;
            case PKCS11:
                cryptoProvider = new OxElevenCryptoProvider(
                        configuration.getOxElevenGenerateKeyEndpoint(),
                        configuration.getOxElevenSignEndpoint(),
                        configuration.getOxElevenVerifySignatureEndpoint(),
                        configuration.getOxElevenDeleteKeyEndpoint());
                break;
        }

        return cryptoProvider;
    }
}
