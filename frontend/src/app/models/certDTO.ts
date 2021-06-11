export class CertDTO {

    entityId: number;
    duration: number;
    issuerSerialNumber: string;
    certificateType: string;
    keyUsageList: Array<string>;
    basicConstraints: boolean;

    constructor(entityId: number, duration: number, issuerSerialNumber: string,
                certificateType: string, keyUsageList: Array<string>, basicConstraints: boolean ) {
         
         this.entityId = entityId
         this.duration = duration;
         this.issuerSerialNumber = issuerSerialNumber;
         this.certificateType = certificateType;
         this.keyUsageList = keyUsageList;
         this.basicConstraints = basicConstraints;

    }

}