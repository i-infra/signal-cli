package org.asamk.signal.manager.helper;

import org.asamk.signal.manager.SignalDependencies;
import org.asamk.signal.manager.config.ServiceConfig;
import org.asamk.signal.manager.storage.SignalAccount;
import org.asamk.signal.manager.util.KeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whispersystems.libsignal.IdentityKeyPair;
import org.whispersystems.libsignal.state.PreKeyRecord;
import org.whispersystems.libsignal.state.SignedPreKeyRecord;
import org.whispersystems.signalservice.api.push.ServiceIdType;

import java.io.IOException;
import java.util.List;

public class PreKeyHelper {

    private final static Logger logger = LoggerFactory.getLogger(PreKeyHelper.class);

    private final SignalAccount account;
    private final SignalDependencies dependencies;

    public PreKeyHelper(
            final SignalAccount account, final SignalDependencies dependencies
    ) {
        this.account = account;
        this.dependencies = dependencies;
    }

    public void refreshPreKeysIfNecessary() throws IOException {
        refreshPreKeysIfNecessary(ServiceIdType.ACI);
        refreshPreKeysIfNecessary(ServiceIdType.PNI);
    }

    public void refreshPreKeysIfNecessary(ServiceIdType serviceIdType) throws IOException {
        if (dependencies.getAccountManager().getPreKeysCount(serviceIdType) < ServiceConfig.PREKEY_MINIMUM_COUNT) {
            refreshPreKeys(serviceIdType);
        }
    }

    public void refreshPreKeys() throws IOException {
        refreshPreKeys(ServiceIdType.ACI);
        refreshPreKeys(ServiceIdType.PNI);
    }

    public void refreshPreKeys(ServiceIdType serviceIdType) throws IOException {
        if (serviceIdType != ServiceIdType.ACI) {
            // TODO implement
            return;
        }
        var oneTimePreKeys = generatePreKeys();
        final var identityKeyPair = account.getAciIdentityKeyPair();
        var signedPreKeyRecord = generateSignedPreKey(identityKeyPair);

        dependencies.getAccountManager()
                .setPreKeys(serviceIdType, identityKeyPair.getPublicKey(), signedPreKeyRecord, oneTimePreKeys);
    }

    private List<PreKeyRecord> generatePreKeys() {
        final var offset = account.getPreKeyIdOffset();

        var records = KeyUtils.generatePreKeyRecords(offset, ServiceConfig.PREKEY_BATCH_SIZE);
        account.addPreKeys(records);

        return records;
    }

    private SignedPreKeyRecord generateSignedPreKey(IdentityKeyPair identityKeyPair) {
        final var signedPreKeyId = account.getNextSignedPreKeyId();

        var record = KeyUtils.generateSignedPreKeyRecord(identityKeyPair, signedPreKeyId);
        account.addSignedPreKey(record);

        return record;
    }
}
