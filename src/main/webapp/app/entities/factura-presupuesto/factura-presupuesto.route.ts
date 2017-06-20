import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FacturaPresupuestoComponent } from './factura-presupuesto.component';
import { FacturaPresupuestoDetailComponent } from './factura-presupuesto-detail.component';
import { FacturaPresupuestoPopupComponent } from './factura-presupuesto-dialog.component';
import { FacturaPresupuestoDeletePopupComponent } from './factura-presupuesto-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class FacturaPresupuestoResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const facturaPresupuestoRoute: Routes = [
    {
        path: 'factura-presupuesto',
        component: FacturaPresupuestoComponent,
        resolve: {
            'pagingParams': FacturaPresupuestoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.facturaPresupuesto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'factura-presupuesto/:id',
        component: FacturaPresupuestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.facturaPresupuesto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const facturaPresupuestoPopupRoute: Routes = [
    {
        path: 'factura-presupuesto-new',
        component: FacturaPresupuestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.facturaPresupuesto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'factura-presupuesto/:id/edit',
        component: FacturaPresupuestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.facturaPresupuesto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'factura-presupuesto/:id/delete',
        component: FacturaPresupuestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.facturaPresupuesto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
