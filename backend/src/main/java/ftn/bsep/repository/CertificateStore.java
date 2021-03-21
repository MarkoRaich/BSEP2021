package ftn.bsep.repository;

import org.springframework.stereotype.Repository;

import ftn.bsep.utilities.KeyStoreReader;
import ftn.bsep.utilities.KeyStoreWriter;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

@Repository
public class CertificateStore {
	
	 private KeyStoreWriter writer = new KeyStoreWriter();
	 private KeyStoreReader reader = new KeyStoreReader();

	 
	 public Enumeration<String> getAllAliases(String keyStoreFile, String password) {
	        return reader.getAllAliases(keyStoreFile, password);
	    }


	public Certificate findCertificateByAlias(String alias, String fileLocation, String password){
        return reader.readCertificate(fileLocation, password, alias);

    }


	 public Certificate findCertificateBySerialNumber(String serialNumber, String fileLocation, String password) {
	        return reader.readCertificate(fileLocation, password, serialNumber);
	    }


	 public Certificate[] findCertificateChainBySerialNumber(String serialNumber, String fileLocation, String password) {
	        return reader.readCertificateChain(fileLocation, password, serialNumber);
	    }
	 
}
