import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContractListComponent } from './contracts/contract-list.component';
import { CustomerListComponent } from './customers/customer-list.component';
import { VehicleListComponent } from './vehicles/vehicle-list.component';


const routes: Routes = [
  { path: 'contracts', component: ContractListComponent },
  { path: '', redirectTo: '/contracts', pathMatch: 'full' },
  { path: 'vehicles', component: VehicleListComponent },
  { path: 'customers', component: CustomerListComponent }
]


@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
