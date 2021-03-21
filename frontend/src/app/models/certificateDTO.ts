export class CertificateDTO {

    id: number;
    serialNumberSubject: string;
    serialNumberIssuer: string;
    startDate: Date;
    endDate: Date;
    ca: string;
    revoked: string;

    constructor(id: number, serialNumberSubject: string, serialNumberIssuer: string, startDate: Date, endDate: Date, ca: string, revoked: string){
        this.id = id;
        this.serialNumberSubject = serialNumberSubject;
        this.serialNumberIssuer = serialNumberIssuer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ca = ca;
        this.revoked = revoked;
    }
    
}