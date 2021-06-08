import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { CertificateService } from 'src/app/services/certificate.service';
import { environment } from 'src/environments/environment';
import { ToastrService } from 'ngx-toastr';
import { CertificateDTO } from 'src/app/models/certificateDTO';
import { MatDialog } from '@angular/material/dialog';
import { CertificateDetailsComponent } from '../certificate-details/certificate-details.component';


@Component({
  selector: 'app-list-certificates',
  templateUrl: './list-certificates.component.html',
  styleUrls: ['./list-certificates.component.css']
})
export class ListCertificatesComponent implements OnInit {

  certificateDataSource: MatTableDataSource<CertificateDTO>;
  displayedColumns: string[] = ['id','serialNumberSubject','serialNumberIssuer','startDate','endDate','CA','details','revoke?','revoke'];
  itemsPerPage = environment.itemsPerPage;
  addCertificateSuccess: Subscription;

  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(public dialog: MatDialog, private certificateService: CertificateService, public toastr: ToastrService) { }

  ngOnInit() {

    this.getCertificates();

  }

  getCertificates() {
    this.certificateService.getAllCertificates().subscribe(
    (data: CertificateDTO[]) => {
      this.certificateDataSource = new MatTableDataSource(data);
      this.certificateDataSource.sort = this.sort;
      this.certificateDataSource.paginator = this.paginator;
    }
    )
  }

  seeDetails(cert: CertificateDTO){
    this.dialog.open(CertificateDetailsComponent, {
      data: {
        certificate: cert
      }
    });
  }

  checkRevoke(cert: CertificateDTO){
    this.certificateService.checkRevokeStatus(cert.serialNumberSubject).subscribe(
      (data: boolean) => {
        if(data == true){
          this.toastr.error("Sertifikat je povučen");
        } else {
          this.toastr.success("Sertifikat nije povučen!");
        }
        
      },
      () => {
        this.toastr.error("Došlo je do greške...");
      }
    )
  }

  revokeCert(cert: CertificateDTO){
    this.certificateService.revokeCertificate(cert.serialNumberSubject).subscribe(
      (data: boolean) => {
        if(data == true) {
          this.toastr.success("Sertifikat je povučen. Povučeni su i svi u lancu čiji je on bio potpisnik.");
        } else {
          this.toastr.error("Došlo je do greške...");
        }
      }
    )
  }

  
}
