import { BehaviorSubject, map, Observable, startWith } from 'rxjs';
import { EntityService } from './entity.service';
import { Page } from './page';

const PAGE_SIZE = 5;

export abstract class EntityListComponent<LT, DT> {

  pageState = new Observable<Page<LT>>();

  pageSubject = new BehaviorSubject<Page<LT>>({
    content: [],
    totalPages: 0,
    totalElements: 0,
  });

  constructor(private entityService: EntityService<LT, DT>) {}


  switchPage(pageNumber: number): void {
    this.pageState = this.entityService.find(pageNumber, PAGE_SIZE).pipe(
      map((response: Page<LT>) => {
        this.pageSubject.next(response);
        return response;
      }),
      startWith(this.pageSubject.value)
    );
  }
}
