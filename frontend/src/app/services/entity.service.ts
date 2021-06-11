import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { BehaviorSubject } from "rxjs";
import { environment } from "src/environments/environment";
import { Entity } from "../models/entity";

@Injectable({
    providedIn: 'root'
})
export class EntityService {
   
    
    url = environment.baseUrl + '/api/entities';

    entities: BehaviorSubject<Entity[]> = new BehaviorSubject<Entity[]>([]);


    constructor(private http: HttpClient, private router: Router) { }

    getEntitiesWithoutActiveCertificate() {
        this.http.get(this.url + '/getAllValidEntities').subscribe(
            (data : Entity[]) => {
                this.entities.next(data);
            },
            (error: Entity) => {}
          );
        return this.entities.asObservable();
      }

}