import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import { Page } from "./page";

export abstract class EntityService<T> {


  constructor(private http: HttpClient, private apiEndpoint: string) { }

  public find(page: number = 0, size: number = 10): Observable<Page<T>> {
    return this.http.get<Page<T>>(`${this.apiEndpoint}?page=${page}&size=${size}`)

  }

  public getDetails(id: number): Observable<T>  {
    return this.http.get<T>(`${this.apiEndpoint}/${id}`);
  }
}
