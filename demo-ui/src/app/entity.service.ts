import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import { Page } from "./page";

export abstract class EntityService<LT, DT> {


  constructor(private http: HttpClient, private apiEndpoint: string) { }

  public find(page: number = 0, size: number = 10): Observable<Page<LT>> {
    return this.http.get<Page<LT>>(`${this.apiEndpoint}?page=${page}&size=${size}`)

  }

  public getDetails(id: number): Observable<DT>  {
    return this.http.get<DT>(`${this.apiEndpoint}/${id}`);
  }
}
