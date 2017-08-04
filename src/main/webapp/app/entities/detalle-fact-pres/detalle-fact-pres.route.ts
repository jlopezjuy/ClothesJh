import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DetalleFactPresComponent } from './detalle-fact-pres.component';
import { DetalleFactPresDetailComponent } from './detalle-fact-pres-detail.component';
import { DetalleFactPresPopupComponent } from './detalle-fact-pres-dialog.component';
import { DetalleFactPresDeletePopupComponent } from './detalle-fact-pres-delete-dialog.component';

@Injectable()
export class DetalleFactPresResolvePagingParams implements Resolve<any> {

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

export const detalleFactPresRoute: Routes = [
    {
        path: 'detalle-fact-pres',
        component: DetalleFactPresComponent,
        resolve: {
            'pagingParams': DetalleFactPresResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.detalleFactPres.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'detalle-fact-pres/:id',
        component: DetalleFactPresDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.detalleFactPres.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detalleFactPresPopupRoute: Routes = [
    {
        path: 'detalle-fact-pres-new',
        component: DetalleFactPresPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.detalleFactPres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detalle-fact-pres/:id/edit',
        component: DetalleFactPresPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.detalleFactPres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detalle-fact-pres/:id/delete',
        component: DetalleFactPresDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.detalleFactPres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
