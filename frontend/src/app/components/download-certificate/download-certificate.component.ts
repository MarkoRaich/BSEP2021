import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { CertificateDTO } from 'src/app/models/certificateDTO';
import { CertificateService } from 'src/app/services/certificate.service';
import { environment } from 'src/environments/environment';
import { CertificateDetailsComponent } from '../certificate-details/certificate-details.component';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-download-certificate',
  templateUrl: './download-certificate.component.html',
  styleUrls: ['./download-certificate.component.css']
})
export class DownloadCertificateComponent implements OnInit {

  certificateDataSource: MatTableDataSource<CertificateDTO>;
  displayedColumns: string[] = ['id','serialNumberSubject','serialNumberIssuer','startDate','endDate','CA','details','revoke?','download'];
  itemsPerPage = environment.itemsPerPage;


  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(public dialog: MatDialog, private certificateService: CertificateService, public toastr: ToastrService) { }


  ngOnInit(): void {

    this.getCertificates();
  
  }

  getCertificates() {
    this.certificateService.getMyCertificate().subscribe(
      (data: CertificateDTO[]) => {
        this.certificateDataSource = new MatTableDataSource(data);
        this.certificateDataSource.sort = this.sort;
        this.certificateDataSource.paginator = this.paginator;
      },
      () => {
        this.toastr.error("Došlo je do greške...");
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

  downloadCert(cert: CertificateDTO){
    this.certificateService.downloadCertificate(cert.serialNumberSubject).subscribe(
      (data) => saveAs(data , 'certificate.jks')
    )
  }

}
