export class CertDTO {

    subjectCommonName: string;
    subjectFirstName: string;
    subjectLastName: string;
    subjectEmail: string;
    subjectOrganization: string;
    subjectOrganizationUnit: string;
    subjectState: string;
    subjectCountry: string;
    duration: number;
    issuerSerialNumber: string;
    certificateType: string;
    keyUsageList: Array<string>;
    basicConstraints: boolean;

    constructor(subjectCommonName: string, subjectFirstName: string, subjectLastName: string, subjectEmail: string, subjectOrganization: string,
                subjectOrganizationUnit: string, subjectState: string, subjectCountry: string, duration: number, issuerSerialNumber: string,
                certificateType: string, keyUsageList: Array<string>, basicConstraints: boolean ) {
         
         this.subjectCommonName = subjectCommonName;
         this.subjectFirstName = subjectFirstName;
         this.subjectLastName = subjectLastName;
         this.subjectEmail = subjectEmail;
         this.subjectOrganization = subjectOrganization;
         this.subjectOrganizationUnit = subjectOrganizationUnit;
         this.subjectState = subjectState;
         this.subjectCountry = subjectCountry;
         this.duration = duration;
         this.issuerSerialNumber = issuerSerialNumber;
         this.certificateType = certificateType;
         this.keyUsageList = keyUsageList;
         this.basicConstraints = basicConstraints;

    }

}