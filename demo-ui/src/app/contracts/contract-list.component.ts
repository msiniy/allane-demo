import { Component, OnInit } from '@angular/core';
import { EntityListComponent } from '../entity-list.component';
import { ContractOverview } from './contract';
import { ContractService } from './contract.service';

@Component({
  selector: 'app-contract-list',
  templateUrl: './contract-list.component.html',
  styleUrls: ['./contract-list.component.css']
})
export class ContractListComponent extends EntityListComponent<ContractOverview> implements OnInit {

  constructor(contractService: ContractService) {
    super(contractService);
  }

  ngOnInit(): void {
    this.switchPage(0);
  }

}
