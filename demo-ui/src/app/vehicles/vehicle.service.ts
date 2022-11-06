import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Vehicle } from './vehicle';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private apiEndpoint: string = "/api/vehicles";

  constructor(private http: HttpClient) { }

  public find(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.apiEndpoint);
  }
}
