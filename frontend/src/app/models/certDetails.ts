export class CertDetails {

    serialNumber: string;
    version: number;
    signatureAlgorithm: string;
    signatureHashAlgorithm: string;
    publicKey: string;
    startDate: string;
    endDate: string;
    isRoot: boolean;
    subject: string;
    subjectGivenname: string;
    subjectSurname: string;
    subjectEmail: string;
    subjectCommonName: string;
    issuer: string;
    issuerGivenname: string;
    issuerSurname: string;
    issuerEmail: string;
    issuerCommonName: string;
    issuerSerialNumber: string;
    type: string;
    subjectAlternativeNames: string;
    authorityKeyIdentifier: string;
    subjectKeyIdentifier: string;
    keyUsageList: string[];
    extendedKeyUsageList: number[];


    constructor(){}

}