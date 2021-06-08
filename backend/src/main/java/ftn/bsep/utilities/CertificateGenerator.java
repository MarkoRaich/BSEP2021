package ftn.bsep.utilities;

import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;

import org.springframework.stereotype.Component;

import ftn.bsep.dto.CertificateDTO;


import java.math.BigInteger;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;


@Component
public class CertificateGenerator {


    public CertificateGenerator() {}

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, CertificateDTO certDTO) {
    	
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
        	
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");

            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            //BasicConstraints za svaki dodajem
            certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(certDTO.isBasicConstraints()));

           
            ArrayList<Integer> keyUseValues = getIntegersOfKeyUsages(certDTO.getKeyUsageList());
                       
            //KeyUsage za svaki dodajem
            int allKeyUsages = 0;
            for (Integer i : keyUseValues) {
                allKeyUsages = allKeyUsages | i;
            }
                      
            certGen.addExtension(Extension.keyUsage, true, new KeyUsage(allKeyUsages));



            X509CertificateHolder certHolder = certGen.build(contentSigner);
            
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");
            
            return certConverter.getCertificate(certHolder);

        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (CertIOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private ArrayList<Integer> getIntegersOfKeyUsages(ArrayList<String> usages) {

        HashMap<String, Integer> map = new HashMap<>();
        map.put("DigitalSignature", KeyUsage.digitalSignature);
        map.put("NonRepudiation", KeyUsage.nonRepudiation);
        map.put("KeyEncipherment", KeyUsage.keyEncipherment);
        map.put("DataEncipherment", KeyUsage.dataEncipherment);
        map.put("KeyAgreement", KeyUsage.keyAgreement);
        map.put("KeyCertSign", KeyUsage.keyCertSign);
        map.put("CRLSign", KeyUsage.cRLSign);
        map.put("EncipherOnly", KeyUsage.encipherOnly);
        map.put("DecipherOnly", KeyUsage.decipherOnly);

        ArrayList<Integer> retVal = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if(usages.contains(entry.getKey())) {
                retVal.add(entry.getValue());
            }
        }

        return retVal;
    }
    
}
