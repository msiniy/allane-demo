import { BehaviorSubject, map, Observable, startWith } from 'rxjs';
import { EntityService } from './entity.service';
import { Page } from './page';

const PAGE_SIZE = 5;

export abstract class EntityListComponent<T> {

  entityState = new Observable<Page<T>>();

  pageSubject = new BehaviorSubject<Page<T>>({
    content: [],
    totalPages: 0,
    totalElements: 0,
  });

  constructor(private entityService: EntityService<T>) {}


  switchPage(pageNumber: number): void {
    this.entityState = this.entityService.find(pageNumber, PAGE_SIZE).pipe(
      map((response: Page<T>) => {
        this.pageSubject.next(response);
        return response;
      }),
      startWith(this.pageSubject.value)
    );
  }
}
