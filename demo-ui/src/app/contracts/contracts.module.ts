import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from '../app-routing.module';

import { ContractListComponent } from './contract-list.component';
import { ContractDetailsComponent } from './contract-details.component';



@NgModule({
  declarations: [
    ContractListComponent,
    ContractDetailsComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    ReactiveFormsModule,
  ]
})
export class ContractsModule { }
