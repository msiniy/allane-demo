import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs';
import { ContractOverview } from './contract';
import { ContractService } from './contract.service';

@Component({
  selector: 'app-contract-details',
  templateUrl: './contract-details.component.html',
  styleUrls: ['./contract-details.component.css']
})
export class ContractDetailsComponent implements OnInit {

  contract: Observable<ContractOverview> = new Observable<ContractOverview> ();

  constructor(private route: ActivatedRoute, private contractService: ContractService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      this.contract = this.contractService.getDetails(id);
    });
  }




}
