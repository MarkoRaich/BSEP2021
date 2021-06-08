import { HttpClient, HttpResponseBase } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

import { BehaviorSubject, Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { CertDTO } from "../models/certDTO";
import { CertificateDTO } from "../models/certificateDTO";

@Injectable({
    providedIn: 'root'
})
export class CertificateService {
    
  
    url = environment.baseUrl + '/api/certificates';

    certificates: BehaviorSubject<CertificateDTO[]> = new BehaviorSubject<CertificateDTO[]>([]);

    constructor(private http: HttpClient, private router: Router) { }
  

    creatRootCertificate(certificate: CertDTO) {
        return this.http.post<CertDTO>(this.url + '/createRootCertificate', certificate);
    }
  
    creatCAorEndCertificate(certificate: CertDTO) {
        return this.http.post<CertDTO>(this.url + '/createCAorEndCertificate', certificate);
    }

    getAllCertificates() {
        this.http.get(this.url + '/getAllCertificates').subscribe(
            (data : CertificateDTO[]) => {
                this.certificates.next(data);
            },
            (error: CertificateDTO) => {}
          );
        return this.certificates.asObservable();
    }
    
    getValidCACertificates() {
        this.http.get(this.url + '/getAllValidCA/').subscribe(
            (data : CertificateDTO[]) => {
                this.certificates.next(data);
            },
            (error: CertificateDTO) => {}
          );
        return this.certificates.asObservable();
    }

    checkRevokeStatus(serialNumberSubject: string) {
       return this.http.get(this.url + '/checkRevocationStatusOCSP/' + serialNumberSubject);
    }

    revokeCertificate(serialNumberSubject: string) {
       return this.http.get(this.url + '/revokeCertificate/' + serialNumberSubject);
    }

    getDetails(serialNumberSubject: string) {
        return this.http.get(this.url + '/getCertificateDetails/' + serialNumberSubject );
    }

}