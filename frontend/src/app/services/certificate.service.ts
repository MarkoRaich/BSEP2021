import { HttpClient, HttpResponseBase } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

import { BehaviorSubject, Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { CertificateDTO } from "../models/certificateDTO";

@Injectable({
    providedIn: 'root'
})
export class CertificateService {
   
    
  
    url = environment.baseUrl + '/api/certificates';

    certificates: BehaviorSubject<CertificateDTO[]> = new BehaviorSubject<CertificateDTO[]>([]);

    constructor(private http: HttpClient, private router: Router) { }
  
  
    getAllCertificates() {
        this.http.get(this.url + '/getAllCertificates').subscribe(
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

    getDetails(serialNumberSubject: string) {
        return this.http.get(this.url + '/getCertificateDetails/' + serialNumberSubject );
      }

}