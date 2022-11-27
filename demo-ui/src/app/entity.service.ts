import {
  HttpClient,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Page } from './page';

export abstract class EntityService<LT, DT> {
  constructor(private http: HttpClient, private apiEndpoint: string) {}

  public find(page: number = 0, size: number = 10): Observable<Page<LT>> {
    return this.http.get<Page<LT>>(
      `${this.apiEndpoint}?page=${page}&size=${size}`
    );
  }

  public getDetails(id: number): Observable<DT> {
    return this.http.get<DT>(`${this.apiEndpoint}/${id}`);
  }
}

export type BackendError = {
  status: number;
  statusText: string;
  message: string;
  fieldErrors?: {
    field: string;
    message: string;
  }[];
};

export class ErrorIntercept implements HttpInterceptor {
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        const backendError: BackendError = {
          status: error.status,
          statusText: error.statusText,
          message: error.error?.message,
        };
        if (error.status === 400 && error.error.errors?.length > 0) {
          backendError.fieldErrors = error.error.errors.map(
            (e: { field: string; defaultMessage: string }) => {
              return { field: e.field, message: e.defaultMessage };
            }
          );
        }
        console.warn(backendError);
        return throwError(() => backendError);
      })
    );
  }
}
