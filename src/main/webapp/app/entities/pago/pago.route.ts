import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PagoComponent } from './pago.component';
import { PagoDetailComponent } from './pago-detail.component';
import { PagoPopupComponent } from './pago-dialog.component';
import { PagoDeletePopupComponent } from './pago-delete-dialog.component';

@Injectable()
export class PagoResolvePagingParams implements Resolve<any> {

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

export const pagoRoute: Routes = [
    {
        path: 'pago',
        component: PagoComponent,
        resolve: {
            'pagingParams': PagoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.pago.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pago/:id',
        component: PagoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.pago.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pagoPopupRoute: Routes = [
    {
        path: 'pago-new',
        component: PagoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.pago.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pago/:id/edit',
        component: PagoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.pago.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pago/:id/delete',
        component: PagoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.pago.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
