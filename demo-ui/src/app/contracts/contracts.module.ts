import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContractListComponent } from './contract-list.component';
import { AppRoutingModule } from '../app-routing.module';
import { ContractDetailsComponent } from './contract-details.component';



@NgModule({
  declarations: [
    ContractListComponent,
    ContractDetailsComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
  ]
})
export class ContractsModule { }
